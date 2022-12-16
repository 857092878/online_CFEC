package com.rui.online.mapper;

import com.rui.online.pojo.TaskExamCustomerAnswer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Mapper
public interface TaskExamCustomerAnswerMapper extends BaseMapper<TaskExamCustomerAnswer> {

    List<TaskExamCustomerAnswer> selectByTUid(List<Integer> taskIds, Integer uid);

    TaskExamCustomerAnswer getByTUid(Integer taskId, Integer userId);
}
