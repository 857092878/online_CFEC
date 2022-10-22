package com.rui.online.mapper;

import com.rui.online.VO.admin.user.UserEventPageRequestVM;
import com.rui.online.domain.other.KeyValue;
import com.rui.online.pojo.UserEventLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Mapper
public interface UserEventLogMapper extends BaseMapper<UserEventLog> {

    List<KeyValue> selectCountByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    List<UserEventLog> page(UserEventPageRequestVM requestVM);
}
