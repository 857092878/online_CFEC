package com.rui.online.service;

import com.github.pagehelper.PageInfo;
import com.rui.online.VO.student.user.MessageRequestVM;
import com.rui.online.pojo.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rui.online.pojo.MessageUser;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
public interface IMessageService extends IService<Message> {

    void sendMessage(Message message, List<MessageUser> messageUsers);

    List<Message> selectMessageByIds(List<Integer> ids);

    PageInfo<MessageUser> studentPage(MessageRequestVM messageRequestVM);

    void read(Integer id);

    Integer unReadCount(Integer id);

    Message messageDetail(Integer id);
}
