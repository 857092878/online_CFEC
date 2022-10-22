package com.rui.online.service;

import com.rui.online.pojo.TaskExamCustomerAnswer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
public interface ITaskExamCustomerAnswerService extends IService<TaskExamCustomerAnswer> {

    List<TaskExamCustomerAnswer> selectByTUid(List<Integer> tIds, Integer id);
}
