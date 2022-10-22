package com.rui.online.service.impl;

import com.rui.online.config.property.SystemConfig;
import com.rui.online.pojo.User;
import com.rui.online.service.AuthenticationService;
import com.rui.online.service.IUserService;
import com.rui.online.utils.RsaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationServiceImpl implements AuthenticationService {


    private final IUserService userService;
    private final SystemConfig systemConfig;

    @Autowired
    public AuthenticationServiceImpl(IUserService userService, SystemConfig systemConfig) {
        this.userService = userService;
        this.systemConfig = systemConfig;
    }


    /**
     * @param username username
     * @param password password
     * @return boolean
     */
    @Override
    public boolean authUser(String username, String password) {
        User user = userService.getUserByUserName(username);
        return authUser(user, username, password);
    }


    @Override
    public boolean authUser(User user, String username, String password) {
        if (user == null) {
            return false;
        }
        String encodePwd = user.getPassword();
        if (null == encodePwd || encodePwd.length() == 0) {
            return false;
        }
        String pwd = pwdDecode(encodePwd);
        return pwd.equals(password);
    }

    @Override
    public String pwdEncode(String password) {
        return RsaUtil.rsaEncode(systemConfig.getPwdKey().getPublicKey(), password);
    }

    @Override
    public String pwdDecode(String encodePwd) {
        return RsaUtil.rsaDecode(systemConfig.getPwdKey().getPrivateKey(), encodePwd);
    }


}
