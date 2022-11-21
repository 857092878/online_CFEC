package com.rui.online;

import com.rui.online.mapper.UserMapper;
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
public class UserTest {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    @Test
    public void getUserByUserName(){
        String username = "admin";
        System.out.println("Name:陆丹\nSex:女\nClass:21智科1班\nNumber:2022900408");

       // System.out.println(userService.getUserByUserName(username));
    }

}


