package com.rui.online.controller.admin;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.exam.ExamPaperEditRequestVM;
import com.rui.online.VO.admin.exam.ExamPaperPageRequestVM;
import com.rui.online.VO.admin.exam.ExamResponseVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.pojo.ExamPaper;
import com.rui.online.service.IExamPaperService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/15 20:45
 * Version1.0.0
 */

@RestController("AdminExamPaperController")
@RequestMapping(value = "/api/admin/exam/paper")
public class ExamPaperController extends BaseApiController {

    private final IExamPaperService examPaperService;

    @Autowired
    public ExamPaperController(IExamPaperService examPaperService) {
        this.examPaperService = examPaperService;
    }

    /*
    试卷分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> pageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.page(model);
        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);
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
    /*
    试卷编辑
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse<ExamPaperEditRequestVM> edit(@RequestBody @Valid ExamPaperEditRequestVM model) {
        ExamPaper examPaper = examPaperService.savePaperFromVM(model, getCurrentUser());
        ExamPaperEditRequestVM newVM = examPaperService.examPaperToVM(examPaper.getId());
        return RestResponse.ok(newVM);
    }
    /*
    试卷删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        ExamPaper examPaper = examPaperService.selectById(id);
        examPaper.setDeleted(true);
        examPaperService.updateByIdFilter(examPaper);
        return RestResponse.ok();
    }

    /*
    试卷任务分页
     */
    @RequestMapping(value = "/taskExamPage", method = RequestMethod.POST)
    public RestResponse<PageInfo<ExamResponseVM>> taskExamPageList(@RequestBody ExamPaperPageRequestVM model) {
        PageInfo<ExamPaper> pageInfo = examPaperService.taskExamPage(model);
        PageInfo<ExamResponseVM> page = PageInfoHelper.copyMap(pageInfo, e -> {
            ExamResponseVM vm = modelMapper.map(e, ExamResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(e.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }

}
