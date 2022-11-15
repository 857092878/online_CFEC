package com.rui.online.mapper;

import com.rui.online.VO.admin.message.MessagePageRequestVM;
import com.rui.online.pojo.Message;
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
public interface MessageMapper extends BaseMapper<Message> {

    int readAdd(Integer id);

    List<Message> page(MessagePageRequestVM requestVM);
}
