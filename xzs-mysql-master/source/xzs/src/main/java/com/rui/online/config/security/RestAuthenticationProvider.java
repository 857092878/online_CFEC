package com.rui.online.config.security;


import com.rui.online.context.WebContext;
import com.rui.online.domain.enums.RoleEnum;
import com.rui.online.domain.enums.UserStatusEnum;
import com.rui.online.service.AuthenticationService;
import com.rui.online.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


/**
 * @version 3.5.0
 * @description: 登录用户名密码验证
 * 验证登录
 */
@Component
public class RestAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationService authenticationService;
    private final IUserService userService;
    private final WebContext webContext;

    /**
     * Instantiates a new Rest authentication provider.
     *
     * @param authenticationService the authentication service
     * @param userService           the user service
     * @param webContext            the web context
     */
    @Autowired
    public RestAuthenticationProvider(AuthenticationService authenticationService, IUserService userService, WebContext webContext) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.webContext = webContext;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();//输入的用户名
        String password = (String) authentication.getCredentials();//输入的密码并加密


        com.rui.online.pojo.User user = userService.getUserByUserName(username);//验证用户
        if (user == null) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        boolean result = authenticationService.authUser(user, username, password);//密码加密
        if (!result) {
            throw new BadCredentialsException("用户名或密码错误");
        }

        UserStatusEnum userStatusEnum = UserStatusEnum.fromCode(user.getStatus());//查看是否禁用
        if (UserStatusEnum.Disable == userStatusEnum) {
            throw new LockedException("用户被禁用");
        }

        ArrayList<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(RoleEnum.fromCode(user.getRole()).getRoleName()));//角色权限

        User authUser = new User(user.getUserName(), user.getPassword(), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(authUser, authUser.getPassword(), authUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
