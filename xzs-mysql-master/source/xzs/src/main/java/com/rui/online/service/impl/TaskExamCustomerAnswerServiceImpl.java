package com.rui.online.service.impl;

import com.rui.online.pojo.TaskExamCustomerAnswer;
import com.rui.online.mapper.TaskExamCustomerAnswerMapper;
import com.rui.online.service.ITaskExamCustomerAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Service
public class TaskExamCustomerAnswerServiceImpl extends ServiceImpl<TaskExamCustomerAnswerMapper, TaskExamCustomerAnswer> implements ITaskExamCustomerAnswerService {

    @Autowired
    private TaskExamCustomerAnswerMapper taskExamCustomerAnswerMapper;

    @Override
    public List<TaskExamCustomerAnswer> selectByTUid(List<Integer> taskIds, Integer uid) {
        return taskExamCustomerAnswerMapper.selectByTUid(taskIds, uid);
    }
}
