package com.rui.online.controller.student;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.exam.ExamPaperEditRequestVM;
import com.rui.online.VO.student.exam.ExamPaperPageResponseVM;
import com.rui.online.VO.student.exam.ExamPaperPageVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.pojo.ExamPaper;
import com.rui.online.service.IExamPaperAnswerService;
import com.rui.online.service.IExamPaperService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/18 14:51
 * Version1.0.0
 */

@RestController("StudentExamPaperController")
@RequestMapping(value = "/api/student/exam/paper")
public class ExamPaperController extends BaseApiController {
    private final IExamPaperService examPaperService;
    private final IExamPaperAnswerService examPaperAnswerService;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public ExamPaperController(IExamPaperService examPaperService, IExamPaperAnswerService examPaperAnswerService, ApplicationEventPublisher eventPublisher) {
        this.examPaperService = examPaperService;
        this.examPaperAnswerService = examPaperAnswerService;
        this.eventPublisher = eventPublisher;
    }
    /*
    试卷分页
     */
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperPageResponseVM>> pageList(@RequestBody @Valid ExamPaperPageVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.studentPage(model);
        PageInfo<ExamPaperPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperPageResponseVM vm = modelMapper.map(e, ExamPaperPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
    /*
    试卷查询
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> select(@PathVariable Integer id) {
        ExamPaperEditRequestVM vm = examPaperService.examPaperToVM(id);
        return RestResponse.ok(vm);
    }

}
