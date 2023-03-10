package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.user.UserEventPageRequestVM;
import com.rui.online.VO.chart.UserLogVo;
import com.rui.online.pojo.UserEventLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IUserEventLogService extends IService<UserEventLog> {

    List<Integer> selectMothCount();

    PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM);

    List<UserEventLog> getUserEventLogByUserId(Integer id);

    List<UserLogVo> selectUserLog();

    void insertByFilter(UserEventLog userEventLog);

    Integer loginByMonth(Date startTime, Date endTime);


    Integer loginByToday(Date startDay, Date endDay);
}
