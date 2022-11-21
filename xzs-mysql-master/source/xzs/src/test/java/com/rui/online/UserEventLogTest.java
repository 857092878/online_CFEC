package com.rui.online;

import com.rui.online.mapper.UserEventLogMapper;
import com.rui.online.mapper.UserMapper;
import com.rui.online.service.IUserEventLogService;
import com.rui.online.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author 蒲锐
 * @CreateTme 2022/10/15 14:30
 * Version1.0.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserEventLogTest {
    @Autowired
    private UserEventLogMapper userEventLogMapper;
    @Autowired
    private IUserEventLogService userEventLogService;
    @Test
    public void getUserByUserName(){
        System.out.println(userEventLogService.getUserEventLogByUserId(5));
    }

}
