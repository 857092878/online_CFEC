package com.rui.online.mapper;

import com.rui.online.VO.student.user.MessageRequestVM;
import com.rui.online.pojo.MessageUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Mapper
public interface MessageUserMapper extends BaseMapper<MessageUser> {

    int inserts(List<MessageUser> list);

    List<MessageUser> studentPage(MessageRequestVM requestVM);
}
