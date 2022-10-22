package com.rui.online.controller.admin;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.paper.ExamPaperAnswerPageRequestVM;
import com.rui.online.VO.student.exampaper.ExamPaperAnswerPageResponseVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.pojo.ExamPaperAnswer;
import com.rui.online.pojo.Subject;
import com.rui.online.pojo.User;
import com.rui.online.service.IExamPaperAnswerService;
import com.rui.online.service.ISubjectService;
import com.rui.online.service.IUserService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.ExamUtil;
import com.rui.online.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/16 16:56
 * Version1.0.0
 */

@RestController("AdminExamPaperAnswerController")
@RequestMapping(value = "/api/admin/examPaperAnswer")
public class ExamPaperAnswerController extends BaseApiController {

    private final IExamPaperAnswerService examPaperAnswerService;
    private final ISubjectService subjectService;
    private final IUserService userService;

    @Autowired
    public ExamPaperAnswerController(IExamPaperAnswerService examPaperAnswerService, ISubjectService subjectService, IUserService userService) {
        this.examPaperAnswerService = examPaperAnswerService;
        this.subjectService = subjectService;
        this.userService = userService;
    }
    /*
    答卷分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamPaperAnswerPageResponseVM>> pageJudgeList(@RequestBody ExamPaperAnswerPageRequestVM model) {
        PageInfo<ExamPaperAnswer> pageInfo = examPaperAnswerService.adminPage(model);
        PageInfo<ExamPaperAnswerPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamPaperAnswerPageResponseVM vm = modelMapper.map(e, ExamPaperAnswerPageResponseVM.class);
            Subject subject = subjectService.selectById(vm.getSubjectId());
            vm.setDoTime(ExamUtil.secondToVM(e.getDoTime()));
            vm.setSystemScore(ExamUtil.scoreToVM(e.getSystemScore()));
            vm.setUserScore(ExamUtil.scoreToVM(e.getUserScore()));
            vm.setPaperScore(ExamUtil.scoreToVM(e.getPaperScore()));
            vm.setSubjectName(subject.getName());
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            User user = userService.selectById(e.getCreateUser());
            vm.setUserName(user.getUserName());
            return vm;
        });
        return RestResponse.ok(page);
    }
}
