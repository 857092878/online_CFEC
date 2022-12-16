package com.rui.online.controller.student;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.question.QuestionEditRequestVM;
import com.rui.online.VO.student.exam.ExamPaperSubmitItemVM;
import com.rui.online.VO.student.question.answer.QuestionAnswerVM;
import com.rui.online.VO.student.question.answer.QuestionPageStudentRequestVM;
import com.rui.online.VO.student.question.answer.QuestionPageStudentResponseVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.domain.question.QuestionObject;
import com.rui.online.pojo.ExamPaperQuestionCustomerAnswer;
import com.rui.online.pojo.Subject;
import com.rui.online.pojo.TextContent;
import com.rui.online.service.IExamPaperQuestionCustomerAnswerService;
import com.rui.online.service.IQuestionService;
import com.rui.online.service.ISubjectService;
import com.rui.online.service.ITextContentService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.HtmlUtil;
import com.rui.online.utils.JsonUtil;
import com.rui.online.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/19 11:56
 * Version1.0.0
 */

@RestController("StudentQuestionAnswerController")
@RequestMapping(value = "/api/student/question/answer")
public class QuestionAnswerController extends BaseApiController {

    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final IQuestionService questionService;
    private final ITextContentService textContentService;
    private final ISubjectService subjectService;

    @Autowired
    public QuestionAnswerController(IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, IQuestionService questionService, ITextContentService textContentService, ISubjectService subjectService) {
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.questionService = questionService;
        this.textContentService = textContentService;
        this.subjectService = subjectService;
    }

    /*
    错题本分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<QuestionPageStudentResponseVM>> pageList(@RequestBody QuestionPageStudentRequestVM model) {
        model.setCreateUser(getCurrentUser().getId());
        PageInfo<ExamPaperQuestionCustomerAnswer> pageInfo = examPaperQuestionCustomerAnswerService.studentPage(model);
        PageInfo<QuestionPageStudentResponseVM> page = PageInfoHelper.copyMap(pageInfo, q -> {
            Subject subject = subjectService.selectById(q.getSubjectId());
            QuestionPageStudentResponseVM vm = modelMapper.map(q, QuestionPageStudentResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(q.getCreateTime()));
            TextContent textContent = textContentService.selectById(q.getQuestionTextContentId());
            QuestionObject questionObject = JsonUtil.toJsonObject(textContent.getContent(), QuestionObject.class);
            String clearHtml = HtmlUtil.clear(questionObject.getTitleContent());
            vm.setShortTitle(clearHtml);
            vm.setSubjectName(subject.getName());
            return vm;
        });
        return RestResponse.ok(page);
    }
    /*
    答题详情
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<QuestionAnswerVM> select(@PathVariable Integer id) {
        QuestionAnswerVM vm = new QuestionAnswerVM();
        ExamPaperQuestionCustomerAnswer examPaperQuestionCustomerAnswer = examPaperQuestionCustomerAnswerService.selectById(id);
        ExamPaperSubmitItemVM questionAnswerVM = examPaperQuestionCustomerAnswerService.examPaperQuestionCustomerAnswerToVM(examPaperQuestionCustomerAnswer);
        QuestionEditRequestVM questionVM = questionService.getQuestionEditRequestVM(examPaperQuestionCustomerAnswer.getQuestionId());
        vm.setQuestionVM(questionVM);
        vm.setQuestionAnswerVM(questionAnswerVM);
        return RestResponse.ok(vm);
    }




}
