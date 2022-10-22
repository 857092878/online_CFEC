package com.rui.online.controller.wx;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.exam.ExamPaperEditRequestVM;
import com.rui.online.VO.student.exam.ExamPaperReadVM;
import com.rui.online.VO.student.exam.ExamPaperSubmitItemVM;
import com.rui.online.VO.student.exam.ExamPaperSubmitVM;
import com.rui.online.VO.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.rui.online.VO.student.exampaper.ExamPaperAnswerPageVM;
import com.rui.online.base.RestResponse;
import com.rui.online.domain.ExamPaperAnswerInfo;
import com.rui.online.domain.enums.QuestionTypeEnum;
import com.rui.online.event.CalculateExamPaperAnswerCompleteEvent;
import com.rui.online.event.UserEvent;
import com.rui.online.pojo.ExamPaperAnswer;
import com.rui.online.pojo.Subject;
import com.rui.online.pojo.User;
import com.rui.online.pojo.UserEventLog;
import com.rui.online.service.IExamPaperAnswerService;
import com.rui.online.service.IExamPaperService;
import com.rui.online.service.ISubjectService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.ExamUtil;
import com.rui.online.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;


@Controller("WXStudentExamPaperAnswerController")
@RequestMapping(value = "/api/wx/student/exampaper/answer")
@ResponseBody
public class ExamPaperAnswerController extends BaseWXApiController {

    private final IExamPaperAnswerService examPaperAnswerService;
    private final ISubjectService subjectService;
    private final ApplicationEventPublisher eventPublisher;
    private final IExamPaperService examPaperService;

    @Autowired
    public ExamPaperAnswerController(IExamPaperAnswerService examPaperAnswerService, ISubjectService subjectService, ApplicationEventPublisher eventPublisher, IExamPaperService examPaperService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.eventPublisher = eventPublisher;
        this.examPaperService = examPaperService;
    }

    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageList(@Valid ExamPaperAnswerPageVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperAnswer> pageInfo = examPaperAnswerService.studentPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }


    /*
    试卷提交
     */
    @RequestMapping(value = "/answerSubmit", method = RequestMethod.POST)
    public RestResponse answerSubmit(HttpServletRequest request) {
        ExamPaperSubmitVM examPaperSubmitVM = requestToExamPaperSubmitVM(request);
        User user = getCurrentUser();
        ExamPaperAnswerInfo examPaperAnswerInfo = examPaperAnswerService.calculateExamPaperAnswer(examPaperSubmitVM, user);
        if (null == examPaperAnswerInfo) {
            return RestResponse.fail(2, "试卷不能重复做");
        }
        ExamPaperAnswer examPaperAnswer = examPaperAnswerInfo.getExamPaperAnswer();
        Integer userScore = examPaperAnswer.getUserScore();
        String scoreVm = ExamUtil.scoreToVM(userScore);
        UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
        String content = user.getUserName() + " 提交试卷：" + examPaperAnswerInfo.getExamPaper().getName()
                + " 得分：" + scoreVm
                + " 耗时：" + ExamUtil.secondToVM(examPaperAnswer.getDoTime());
        userEventLog.setContent(content);
        eventPublisher.publishEvent(new CalculateExamPaperAnswerCompleteEvent(examPaperAnswerInfo));
        eventPublisher.publishEvent(new UserEvent(userEventLog));
        return RestResponse.ok(scoreVm);
    }

    private ExamPaperSubmitVM requestToExamPaperSubmitVM(HttpServletRequest request) {
        ExamPaperSubmitVM examPaperSubmitVM = new ExamPaperSubmitVM();
        examPaperSubmitVM.setId(Integer.parseInt(request.getParameter("id")));
        examPaperSubmitVM.setDoTime(Integer.parseInt(request.getParameter("doTime")));
        List<String> parameterNames = Collections.list(request.getParameterNames()).stream()
                .filter(n -> n.contains("_"))
                .collect(Collectors.toList());
        //题目答案按序号分组
        Map<String, List<String>> questionGroup = parameterNames.stream().collect(Collectors.groupingBy(p -> p.substring(0, p.indexOf("_"))));
        List<ExamPaperSubmitItemVM> answerItems = new ArrayList<>();
        questionGroup.forEach((k, v) -> {
            ExamPaperSubmitItemVM examPaperSubmitItemVM = new ExamPaperSubmitItemVM();
            String p = v.get(0);
            String[] keys = p.split("_");
            examPaperSubmitItemVM.setQuestionId(Integer.parseInt(keys[1]));
            examPaperSubmitItemVM.setItemOrder(Integer.parseInt(keys[0]));
            QuestionTypeEnum typeEnum = QuestionTypeEnum.fromCode(Integer.parseInt(keys[2]));
            if (v.size() == 1) {
                String content = request.getParameter(p);
                examPaperSubmitItemVM.setContent(content);
                if (typeEnum == QuestionTypeEnum.MultipleChoice) {
                    examPaperSubmitItemVM.setContentArray(Arrays.asList(content.split(",")));
                }
            } else {  //多个空 填空题
                List<String> answers = v.stream().sorted(Comparator.comparingInt(ExamUtil::lastNum)).map(inputKey -> request.getParameter(inputKey)).collect(Collectors.toList());
                examPaperSubmitItemVM.setContentArray(answers);
            }
            answerItems.add(examPaperSubmitItemVM);
        });
        examPaperSubmitVM.setAnswerItems(answerItems);
        return examPaperSubmitVM;
    }


    @PostMapping(value = "/read/{id}")
    public RestResponse<ExamPaperReadVM> read(@PathVariable Integer id) {
        ExamPaperReadVM vm = new ExamPaperReadVM();
        ExamPaperAnswer examPaperAnswer = examPaperAnswerService.selectById(id);
        ExamPaperEditRequestVM paper = examPaperService.examPaperToVM(examPaperAnswer.getExamPaperId());
        ExamPaperSubmitVM answer = examPaperAnswerService.examPaperAnswerToVM(examPaperAnswer.getId());
        vm.setPaper(paper);
        vm.setAnswer(answer);
        return RestResponse.ok(vm);
    }
}
