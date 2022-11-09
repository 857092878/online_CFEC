package com.rui.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.user.UserPageRequestVM;
import com.rui.online.domain.other.KeyValue;
import com.rui.online.pojo.User;
import com.rui.online.mapper.UserMapper;
import com.rui.online.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    @Value("${file}")
    private String file;

    @Override
    public User getUserByUserName(String username) {
        return userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUserName,username).eq(User::getDeleted,0));
    }

    @Override
    public PageInfo<User> userPage(UserPageRequestVM model) {
        return PageHelper.startPage(model.getPageIndex(), model.getPageSize(), "id desc").doSelectPageInfo(() ->
                userMapper.userPage(model)
        );
    }

    @Override
    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void insertByFilter(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateByIdFilter(User user) {
        userMapper.updateById(user);
    }

    @Override
    public List<User> selectByIds(List<Integer> receiveUserIds) {
        return userMapper.selectBatchIds(receiveUserIds);
    }

    @Override
    public User selectById(Integer createUser) {
        return userMapper.selectById(createUser);
    }

    @Override
    public List<KeyValue> selectByUserName(String userName) {
        return userMapper.selectByUserName(userName);
    }

    @Override
    public void changePicture(User currentUser, String fileName) {
        User user = userMapper.selectById(currentUser);
        File del = new File(file + user.getImagePath());
        if (del.exists()){
            del.delete();
        }
        user.setImagePath(fileName);
        userMapper.updateById(user);
    }

    @Override
    public Integer selectAdmin() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getDeleted,1);
        queryWrapper.eq(User::getRole,3);
        return userMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer selectUser() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getRole,1);
        queryWrapper.eq(User::getRole,1);
        return userMapper.selectCount(queryWrapper);
    }
}
