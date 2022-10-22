package com.rui.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.user.UserEventPageRequestVM;
import com.rui.online.domain.other.KeyValue;
import com.rui.online.pojo.UserEventLog;
import com.rui.online.mapper.UserEventLogMapper;
import com.rui.online.service.IUserEventLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rui.online.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Service
public class UserEventLogServiceImpl extends ServiceImpl<UserEventLogMapper, UserEventLog> implements IUserEventLogService {

    @Autowired
    private UserEventLogMapper userEventLogMapper;

    @Override
    public List<Integer> selectMothCount() {
        Date startTime = DateTimeUtil.getMonthStartDay();//这个月第一天
        Date endTime = DateTimeUtil.getMonthEndDay();//这个月最后一天
        List<KeyValue> mouthCount = userEventLogMapper.selectCountByDate(startTime, endTime);
        List<String> mothStartToNowFormat = DateTimeUtil.MothStartToNowFormat();
        return mothStartToNowFormat.stream().map(md -> {
            KeyValue keyValue = mouthCount.stream().filter(kv -> kv.getName().equals(md)).findAny().orElse(null);
            return null == keyValue ? 0 : keyValue.getValue();
        }).collect(Collectors.toList());
    }

    @Override
    public PageInfo<UserEventLog> page(UserEventPageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                userEventLogMapper.page(requestVM)
        );
    }

    @Override
    public List<UserEventLog> getUserEventLogByUserId(Integer id) {
        LambdaQueryWrapper<UserEventLog> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(UserEventLog::getUserId,id);
        lambdaQueryWrapper.orderByDesc(UserEventLog::getUserId);
        lambdaQueryWrapper.last("limit 10");
        List<UserEventLog> userEventLogs = userEventLogMapper.selectList(lambdaQueryWrapper);
        return userEventLogs;
    }
}
