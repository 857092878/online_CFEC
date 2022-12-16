package com.rui.online.controller.admin;

import com.rui.online.VO.admin.dashboard.IndexVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.service.*;
import com.rui.online.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/15 15:51
 * Version1.0.0
 */

@RestController("AdminDashboardController")
@RequestMapping(value = "/api/admin/dashboard")
public class DashboardController extends BaseApiController {

    private final IExamPaperService examPaperService;
    private final IQuestionService questionService;
    private final IExamPaperAnswerService examPaperAnswerService;
    private final IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService;
    private final IUserEventLogService userEventLogService;

    @Autowired
    public DashboardController(IExamPaperService examPaperService, IQuestionService questionService, IExamPaperAnswerService examPaperAnswerService, IExamPaperQuestionCustomerAnswerService examPaperQuestionCustomerAnswerService, IUserEventLogService userEventLogService) {
        this.examPaperService = examPaperService;
        this.questionService = questionService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.examPaperQuestionCustomerAnswerService = examPaperQuestionCustomerAnswerService;
        this.userEventLogService = userEventLogService;
    }
    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public RestResponse<IndexVM> Index() {
        IndexVM vm = new IndexVM();

        //试卷总数
        Integer examPaperCount = examPaperService.selectAllCount();
        //题目总数
        Integer questionCount = questionService.selectAllCount();
        //答卷总数
        Integer doExamPaperCount = examPaperAnswerService.selectAllCount();
        //答题总数
        Integer doQuestionCount = examPaperQuestionCustomerAnswerService.selectAllCount();

        vm.setExamPaperCount(examPaperCount);
        vm.setQuestionCount(questionCount);
        vm.setDoExamPaperCount(doExamPaperCount);
        vm.setDoQuestionCount(doQuestionCount);

        //用户活跃度
        List<Integer> mothDayUserActionValue = userEventLogService.selectMothCount();
        //题目月数量
        List<Integer> mothDayDoExamQuestionValue = examPaperQuestionCustomerAnswerService.selectMothCount();
        vm.setMothDayUserActionValue(mothDayUserActionValue);
        vm.setMothDayDoExamQuestionValue(mothDayDoExamQuestionValue);

        vm.setMothDayText(DateTimeUtil.MothDay());
        return RestResponse.ok(vm);
    }


}
