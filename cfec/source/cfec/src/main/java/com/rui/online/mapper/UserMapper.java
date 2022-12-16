package com.rui.online.mapper;

import com.rui.online.VO.admin.user.UserPageRequestVM;
import com.rui.online.domain.other.KeyValue;
import com.rui.online.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-13
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    List<User> userPage(UserPageRequestVM requestVM);

    List<KeyValue> selectByUserName(String userName);
}
