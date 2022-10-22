package com.rui.online.controller.admin;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.task.TaskPageRequestVM;
import com.rui.online.VO.admin.task.TaskPageResponseVM;
import com.rui.online.VO.admin.task.TaskRequestVM;
import com.rui.online.base.RestResponse;
import com.rui.online.controller.BaseApiController;
import com.rui.online.pojo.TaskExam;
import com.rui.online.service.ITaskExamService;
import com.rui.online.utils.DateTimeUtil;
import com.rui.online.utils.PageInfoHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/16 15:33
 * Version1.0.0
 */

@RestController("AdminTaskController")
@RequestMapping(value = "/api/admin/task")
public class TaskController extends BaseApiController {

    @Autowired
    private ITaskExamService taskExamService;

    /*
    任务分页
     */
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public RestResponse<PageInfo<TaskPageResponseVM>> pageList(@RequestBody TaskPageRequestVM model) {
        PageInfo<TaskExam> pageInfo = taskExamService.page(model);
        PageInfo<TaskPageResponseVM> page = PageInfoHelper.copyMap(pageInfo, m -> {
            TaskPageResponseVM vm = modelMapper.map(m, TaskPageResponseVM.class);
            vm.setCreateTime(DateTimeUtil.dateFormat(m.getCreateTime()));
            return vm;
        });
        return RestResponse.ok(page);
    }
    /*
    任务查询
     */
    @RequestMapping(value = "/select/{id}", method = RequestMethod.POST)
    public RestResponse<TaskRequestVM> select(@PathVariable Integer id) {
        TaskRequestVM vm = taskExamService.taskExamToVM(id);
        return RestResponse.ok(vm);
    }
    /*
    任务编辑
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public RestResponse edit(@RequestBody @Valid TaskRequestVM model) {
        taskExamService.edit(model, getCurrentUser());
        TaskRequestVM vm = taskExamService.taskExamToVM(model.getId());
        return RestResponse.ok(vm);
    }
    /*
    任务删除
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RestResponse delete(@PathVariable Integer id) {
        TaskExam taskExam = taskExamService.selectById(id);
        taskExam.setDeleted(true);
        taskExamService.updateByIdFilter(taskExam);
        return RestResponse.ok();
    }


}
