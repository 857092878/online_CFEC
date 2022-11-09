package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.user.UserPageRequestVM;
import com.rui.online.domain.other.KeyValue;
import com.rui.online.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
public interface IUserService extends IService<User> {

    /*
    根据username查询用户
     */
    User getUserByUserName(String username);

    PageInfo<User> userPage(UserPageRequestVM model);

    User getUserById(Integer id);

    void insertByFilter(User user);

    void updateByIdFilter(User user);

    List<User> selectByIds(List<Integer> receiveUserIds);

    User selectById(Integer createUser);

    List<KeyValue> selectByUserName(String userName);

    void changePicture(User currentUser, String fileName);

    Integer selectAdmin();

    Integer selectUser();
}
