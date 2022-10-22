package com.rui.online.mapper;

import com.rui.online.VO.admin.task.TaskPageRequestVM;
import com.rui.online.pojo.TaskExam;
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
public interface TaskExamMapper extends BaseMapper<TaskExam> {

    List<TaskExam> page(TaskPageRequestVM requestVM);
}
