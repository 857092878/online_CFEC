package com.rui.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.exam.ExamResponseVM;
import com.rui.online.VO.admin.task.TaskPageRequestVM;
import com.rui.online.VO.admin.task.TaskRequestVM;
import com.rui.online.domain.enums.ActionEnum;
import com.rui.online.domain.task.TaskItemObject;
import com.rui.online.mapper.ExamPaperMapper;
import com.rui.online.pojo.ExamPaper;
import com.rui.online.pojo.TaskExam;
import com.rui.online.mapper.TaskExamMapper;
import com.rui.online.pojo.TextContent;
import com.rui.online.pojo.User;
import com.rui.online.service.ITaskExamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.online.service.ITextContentService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.JsonUtil;
import com.rui.online.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Service
public class TaskExamServiceImpl extends ServiceImpl<TaskExamMapper, TaskExam> implements ITaskExamService {

    @Autowired
    private TaskExamMapper taskExamMapper;
    @Autowired
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();
    @Autowired
    private ITextContentService textContentService;
    @Autowired
    private ExamPaperMapper examPaperMapper;

    @Override
    public PageInfo<TaskExam> page(TaskPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                taskExamMapper.page(requestVM)
        );
    }

    @Override
    public TaskRequestVM taskExamToVM(Integer id) {
        TaskExam taskExam = taskExamMapper.selectById(id);
        TaskRequestVM vm = modelMapper.map(taskExam, TaskRequestVM.class);
        TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());
        List<ExamResponseVM> examResponseVMS = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class).stream().map(tk -> {
            ExamPaper examPaper = examPaperMapper.selectById(tk.getExamPaperId());
            ExamResponseVM examResponseVM = modelMapper.map(examPaper, ExamResponseVM.class);
            examResponseVM.setCreateTime(DateTimeUtil.dateFormat(examPaper.getCreateTime()));
            return examResponseVM;
        }).collect(Collectors.toList());
        vm.setPaperItems(examResponseVMS);
        return vm;
    }

    @Override
    @Transactional
    public void edit(TaskRequestVM model, User user) {
        ActionEnum actionEnum = (model.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        TaskExam taskExam = null;
        if (actionEnum == ActionEnum.ADD) {
            Date now = new Date();
            taskExam = modelMapper.map(model, TaskExam.class);
            taskExam.setCreateUser(user.getId());
            taskExam.setCreateUserName(user.getUserName());
            taskExam.setCreateTime(now);
            taskExam.setDeleted(false);

            //保存任务结构
            TextContent textContent = textContentService.jsonConvertInsert(model.getPaperItems(), now, p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });
            textContentService.insertByFilter(textContent);
            taskExam.setFrameTextContentId(textContent.getId());
            taskExamMapper.insert(taskExam);

        } else {
            taskExam = taskExamMapper.selectById(model.getId());
            modelMapper.map(model, taskExam);

            TextContent textContent = textContentService.selectById(taskExam.getFrameTextContentId());
            //清空试卷任务的试卷Id，后面会统一设置
            List<Integer> paperIds = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemObject.class)
                    .stream()
                    .map(d -> d.getExamPaperId())
                    .collect(Collectors.toList());
            examPaperMapper.clearTaskPaper(paperIds);

            //更新任务结构
            textContentService.jsonConvertUpdate(textContent, model.getPaperItems(), p -> {
                TaskItemObject taskItemObject = new TaskItemObject();
                taskItemObject.setExamPaperId(p.getId());
                taskItemObject.setExamPaperName(p.getName());
                return taskItemObject;
            });
            textContentService.updateByIdFilter(textContent);
            taskExamMapper.updateById(taskExam);
        }

        //更新试卷的taskId
        List<Integer> paperIds = model.getPaperItems().stream().map(d -> d.getId()).collect(Collectors.toList());
        examPaperMapper.updateTaskPaper(taskExam.getId(), paperIds);
        model.setId(taskExam.getId());
    }

    @Override
    public TaskExam selectById(Integer id) {
        return taskExamMapper.selectById(id);
    }

    @Override
    public void updateByIdFilter(TaskExam taskExam) {
        taskExamMapper.updateById(taskExam);
    }

    @Override
    public List<TaskExam> getByGradeLevel(Integer userLevel) {
        return taskExamMapper.selectList(new LambdaQueryWrapper<TaskExam>().eq(TaskExam::getDeleted,0).eq(TaskExam::getGradeLevel,userLevel));
    }


}
