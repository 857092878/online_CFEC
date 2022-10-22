package com.rui.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.exam.ExamPaperEditRequestVM;
import com.rui.online.VO.admin.exam.ExamPaperPageRequestVM;
import com.rui.online.VO.admin.exam.ExamPaperTitleItemVM;
import com.rui.online.VO.admin.question.QuestionEditRequestVM;
import com.rui.online.VO.student.dashboard.PaperFilter;
import com.rui.online.VO.student.dashboard.PaperInfo;
import com.rui.online.VO.student.exam.ExamPaperPageVM;
import com.rui.online.domain.enums.ActionEnum;
import com.rui.online.domain.enums.ExamPaperTypeEnum;
import com.rui.online.domain.exam.ExamPaperQuestionItemObject;
import com.rui.online.domain.exam.ExamPaperTitleItemObject;
import com.rui.online.mapper.QuestionMapper;
import com.rui.online.pojo.ExamPaper;
import com.rui.online.mapper.ExamPaperMapper;
import com.rui.online.pojo.Question;
import com.rui.online.pojo.TextContent;
import com.rui.online.pojo.User;
import com.rui.online.service.IExamPaperService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.online.service.IQuestionService;
import com.rui.online.service.ISubjectService;
import com.rui.online.service.ITextContentService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.ExamUtil;
import com.rui.online.utils.JsonUtil;
import com.rui.online.utils.ModelMapperSingle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Service
public class ExamPaperServiceImpl extends ServiceImpl<ExamPaperMapper, ExamPaper> implements IExamPaperService {

    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Autowired
    private ITextContentService textContentService;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private ISubjectService subjectService;

    @Autowired
    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    @Override
    public Integer selectAllCount() {
        Integer examPaperCount = Math.toIntExact(examPaperMapper.selectCount(new LambdaQueryWrapper<ExamPaper>().eq(ExamPaper::getDeleted, 0)));
        return examPaperCount;
    }

    @Override
    public PageInfo<ExamPaper> page(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.page(requestVM));
    }

    @Override
    public ExamPaperEditRequestVM examPaperToVM(Integer id) {
        ExamPaper examPaper = examPaperMapper.selectById(id);//查询试卷
        ExamPaperEditRequestVM vm = modelMapper.map(examPaper, ExamPaperEditRequestVM.class);
        vm.setLevel(examPaper.getGradeLevel());
        TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());//根据试卷内容id查询试卷内容
        List<ExamPaperTitleItemObject> examPaperTitleItemObjects = JsonUtil.toJsonListObject(frameTextContent.getContent(), ExamPaperTitleItemObject.class);
        List<Integer> questionIds = examPaperTitleItemObjects.stream()
                .flatMap(t -> t.getQuestionItems().stream()
                        .map(q -> q.getId()))
                .collect(Collectors.toList());
        List<Question> questions = questionMapper.selectBatchIds(questionIds);
        List<ExamPaperTitleItemVM> examPaperTitleItemVMS = examPaperTitleItemObjects.stream().map(t -> {
            ExamPaperTitleItemVM tTitleVM = modelMapper.map(t, ExamPaperTitleItemVM.class);
            List<QuestionEditRequestVM> questionItemsVM = t.getQuestionItems().stream().map(i -> {
                Question question = questions.stream().filter(q -> q.getId().equals(i.getId())).findFirst().get();
                QuestionEditRequestVM questionEditRequestVM = questionService.getQuestionEditRequestVM(question);
                questionEditRequestVM.setItemOrder(i.getItemOrder());
                return questionEditRequestVM;
            }).collect(Collectors.toList());
            tTitleVM.setQuestionItems(questionItemsVM);
            return tTitleVM;
        }).collect(Collectors.toList());
        vm.setTitleItems(examPaperTitleItemVMS);
        vm.setScore(ExamUtil.scoreToVM(examPaper.getScore()));
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            List<String> limitDateTime = Arrays.asList(DateTimeUtil.dateFormat(examPaper.getLimitStartTime()), DateTimeUtil.dateFormat(examPaper.getLimitEndTime()));
            vm.setLimitDateTime(limitDateTime);
        }
        return vm;
    }

    private List<ExamPaperTitleItemObject> frameTextContentFromVM(List<ExamPaperTitleItemVM> titleItems) {
        AtomicInteger index = new AtomicInteger(1);
        return titleItems.stream().map(t -> {
            ExamPaperTitleItemObject titleItem = modelMapper.map(t, ExamPaperTitleItemObject.class);
            List<ExamPaperQuestionItemObject> questionItems = t.getQuestionItems().stream()
                    .map(q -> {
                        ExamPaperQuestionItemObject examPaperQuestionItemObject = modelMapper.map(q, ExamPaperQuestionItemObject.class);
                        examPaperQuestionItemObject.setItemOrder(index.getAndIncrement());
                        return examPaperQuestionItemObject;
                    })
                    .collect(Collectors.toList());
            titleItem.setQuestionItems(questionItems);
            return titleItem;
        }).collect(Collectors.toList());
    }
    private void examPaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, ExamPaper examPaper, List<ExamPaperTitleItemVM> titleItemsVM) {
        Integer gradeLevel = subjectService.levelBySubjectId(examPaperEditRequestVM.getSubjectId());
        Integer questionCount = titleItemsVM.stream()
                .mapToInt(t -> t.getQuestionItems().size()).sum();
        Integer score = titleItemsVM.stream().
                flatMapToInt(t -> t.getQuestionItems().stream()
                        .mapToInt(q -> ExamUtil.scoreFromVM(q.getScore()))
                ).sum();
        examPaper.setQuestionCount(questionCount);
        examPaper.setScore(score);
        examPaper.setGradeLevel(gradeLevel);
        List<String> dateTimes = examPaperEditRequestVM.getLimitDateTime();
        if (ExamPaperTypeEnum.TimeLimit == ExamPaperTypeEnum.fromCode(examPaper.getPaperType())) {
            examPaper.setLimitStartTime(DateTimeUtil.parse(dateTimes.get(0), DateTimeUtil.STANDER_FORMAT));
            examPaper.setLimitEndTime(DateTimeUtil.parse(dateTimes.get(1), DateTimeUtil.STANDER_FORMAT));
        }
    }

    @Override
    @Transactional
    public ExamPaper savePaperFromVM(ExamPaperEditRequestVM examPaperEditRequestVM, User user) {
        ActionEnum actionEnum = (examPaperEditRequestVM.getId() == null) ? ActionEnum.ADD : ActionEnum.UPDATE;
        Date now = new Date();
        List<ExamPaperTitleItemVM> titleItemsVM = examPaperEditRequestVM.getTitleItems();
        List<ExamPaperTitleItemObject> frameTextContentList = frameTextContentFromVM(titleItemsVM);
        String frameTextContentStr = JsonUtil.toJsonStr(frameTextContentList);

        ExamPaper examPaper;
        if (actionEnum == ActionEnum.ADD) {
            examPaper = modelMapper.map(examPaperEditRequestVM, ExamPaper.class);
            TextContent frameTextContent = new TextContent(frameTextContentStr, now);
            textContentService.insertByFilter(frameTextContent);
            examPaper.setFrameTextContentId(frameTextContent.getId());
            examPaper.setCreateTime(now);
            examPaper.setCreateUser(user.getId());
            examPaper.setDeleted(false);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaperMapper.insert(examPaper);
        } else {
            examPaper = examPaperMapper.selectById(examPaperEditRequestVM.getId());
            TextContent frameTextContent = textContentService.selectById(examPaper.getFrameTextContentId());
            frameTextContent.setContent(frameTextContentStr);
            textContentService.updateById(frameTextContent);
            modelMapper.map(examPaperEditRequestVM, examPaper);
            examPaperFromVM(examPaperEditRequestVM, examPaper, titleItemsVM);
            examPaperMapper.updateById(examPaper);
        }
        return examPaper;
    }

    @Override
    public ExamPaper selectById(Integer id) {
        return examPaperMapper.selectById(id);
    }

    @Override
    public void updateByIdFilter(ExamPaper examPaper) {
        examPaperMapper.updateById(examPaper);
    }

    @Override
    public PageInfo<ExamPaper> taskExamPage(ExamPaperPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.taskExamPage(requestVM));
    }

    @Override
    public List<PaperInfo> indexPaper(PaperFilter paperFilter) {
        return examPaperMapper.indexPaper(paperFilter);
    }

    @Override
    public PageInfo<ExamPaper> studentPage(ExamPaperPageVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                examPaperMapper.studentPage(requestVM));
    }
}
