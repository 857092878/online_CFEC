package com.rui.online.service.impl;

import com.rui.online.domain.task.TaskItemAnswerObject;
import com.rui.online.mapper.TextContentMapper;
import com.rui.online.pojo.ExamPaper;
import com.rui.online.pojo.ExamPaperAnswer;
import com.rui.online.pojo.TaskExamCustomerAnswer;
import com.rui.online.mapper.TaskExamCustomerAnswerMapper;
import com.rui.online.pojo.TextContent;
import com.rui.online.service.ITaskExamCustomerAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.online.service.ITextContentService;
import com.rui.online.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Service
public class TaskExamCustomerAnswerServiceImpl extends ServiceImpl<TaskExamCustomerAnswerMapper, TaskExamCustomerAnswer> implements ITaskExamCustomerAnswerService {

    @Autowired
    private TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;
    @Autowired
    private ITextContentService textContentService;

    @Override
    public List<TaskExamCustomerAnswer> selectByTUid(List<Integer> taskIds, Integer uid) {
        return taskExamCustomerAnswerMapper.selectByTUid(taskIds, uid);
    }

    @Override
    public void insertOrUpdate(ExamPaper examPaper, ExamPaperAnswer examPaperAnswer, Date now) {
        Integer taskId = examPaper.getTaskExamId();
        Integer userId = examPaperAnswer.getCreateUser();
        TaskExamCustomerAnswer taskExamCustomerAnswer = taskExamCustomerAnswerMapper.getByTUid(taskId, userId);
        if (null == taskExamCustomerAnswer) {
            taskExamCustomerAnswer = new TaskExamCustomerAnswer();
            taskExamCustomerAnswer.setCreateTime(now);
            taskExamCustomerAnswer.setCreateUser(userId);
            taskExamCustomerAnswer.setTaskExamId(taskId);
            List<TaskItemAnswerObject> taskItemAnswerObjects = Arrays.asList(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            TextContent textContent = textContentService.jsonConvertInsert(taskItemAnswerObjects, now, null);
            textContentService.insertByFilter(textContent);
            taskExamCustomerAnswer.setTextContentId(textContent.getId());
            taskExamCustomerAnswerMapper.insert(taskExamCustomerAnswer);
        } else {
            TextContent textContent = textContentService.selectById(taskExamCustomerAnswer.getTextContentId());
            List<TaskItemAnswerObject> taskItemAnswerObjects = JsonUtil.toJsonListObject(textContent.getContent(), TaskItemAnswerObject.class);
            taskItemAnswerObjects.add(new TaskItemAnswerObject(examPaperAnswer.getExamPaperId(), examPaperAnswer.getId(), examPaperAnswer.getStatus()));
            textContentService.jsonConvertUpdate(textContent, taskItemAnswerObjects, null);
            textContentService.updateByIdFilter(textContent);
        }
    }
}
