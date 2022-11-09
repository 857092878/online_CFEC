package com.rui.online.config.security;

import com.rui.online.base.SystemCode;
import com.rui.online.event.UserEvent;
import com.rui.online.pojo.UserEventLog;
import com.rui.online.service.IUserService;
import com.rui.online.utils.RedisJsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;


/**
 * @version 3.5.0
 * @description: 登录成功返回
 * Copyright (C), 2020-2021, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
@Component
public class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final ApplicationEventPublisher eventPublisher;
    private final IUserService userService;
    public static  String USERLOGINKEY;

    @Autowired
    private RedisJsonUtil redisJsonUtil;

    /**
     * Instantiates a new Rest authentication success handler.
     *
     * @param eventPublisher the event publisher
     * @param userService    the user service
     */
    @Autowired
    public RestAuthenticationSuccessHandler(ApplicationEventPublisher eventPublisher, IUserService userService) {
        this.eventPublisher = eventPublisher;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object object = authentication.getPrincipal();

        if (null != object) {
            User springUser = (User) object;
            /*
            redis获取user
             */
            USERLOGINKEY = "ONLINE_" + springUser.getUsername().toUpperCase() + "_KEY";
            System.out.println(USERLOGINKEY);
            List<com.rui.online.pojo.User> users = redisJsonUtil.getBean(USERLOGINKEY, com.rui.online.pojo.User.class);
            com.rui.online.pojo.User user = new com.rui.online.pojo.User();
            if (users == null){
                user = userService.getUserByUserName(springUser.getUsername());
                redisJsonUtil.setBean(USERLOGINKEY,user);
            }else{
                for (com.rui.online.pojo.User user1 : users) {
                    user = user1;
                }
            }


            if (null != user) {
                UserEventLog userEventLog = new UserEventLog(user.getId(), user.getUserName(), user.getRealName(), new Date());
                userEventLog.setContent(user.getUserName() + " 登录了考试系统");//设置日志信息
                eventPublisher.publishEvent(new UserEvent(userEventLog));
                com.rui.online.pojo.User newUser = new com.rui.online.pojo.User();
                newUser.setUserName(user.getUserName());
                newUser.setImagePath(user.getImagePath());
                RestUtil.response(response, SystemCode.OK.getCode(), SystemCode.OK.getMessage(), newUser);
            }
        } else {
            RestUtil.response(response, SystemCode.UNAUTHORIZED.getCode(), SystemCode.UNAUTHORIZED.getMessage());
        }
    }
}
