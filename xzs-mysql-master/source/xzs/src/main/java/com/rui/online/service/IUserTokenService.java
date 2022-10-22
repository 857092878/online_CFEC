package com.rui.online.service;

import com.rui.online.pojo.User;
import com.rui.online.pojo.UserToken;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IUserTokenService extends IService<UserToken> {

    UserToken bind(User user);

    UserToken insertUserToken(User user);

    void unBind(UserToken userToken);

    UserToken getToken(String token);
}
