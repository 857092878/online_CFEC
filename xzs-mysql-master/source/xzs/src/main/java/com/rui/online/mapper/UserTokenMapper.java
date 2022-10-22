package com.rui.online.mapper;

import com.rui.online.pojo.UserToken;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-15
 */
@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {

    UserToken getToken(String token);
}
