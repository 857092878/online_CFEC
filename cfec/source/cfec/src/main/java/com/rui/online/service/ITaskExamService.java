package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.task.TaskPageRequestVM;
import com.rui.online.VO.admin.task.TaskRequestVM;
import com.rui.online.pojo.TaskExam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.online.pojo.User;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
public interface ITaskExamService extends IService<TaskExam> {

    PageInfo<TaskExam> page(TaskPageRequestVM requestVM);

    TaskRequestVM taskExamToVM(Integer id);

    void edit(TaskRequestVM model, User user);

    TaskExam selectById(Integer id);

    void updateByIdFilter(TaskExam taskExam);

    List<TaskExam> getByGradeLevel(Integer userLevel);

}
