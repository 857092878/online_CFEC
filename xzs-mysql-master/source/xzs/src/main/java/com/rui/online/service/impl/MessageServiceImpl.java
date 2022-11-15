package com.rui.online.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rui.online.VO.admin.message.MessagePageRequestVM;
import com.rui.online.VO.student.user.MessageRequestVM;
import com.rui.online.mapper.MessageUserMapper;
import com.rui.online.pojo.Message;
import com.rui.online.mapper.MessageMapper;
import com.rui.online.pojo.MessageUser;
import com.rui.online.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author purui
 * @since 2022-10-16
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    private final MessageMapper messageMapper;
    private final MessageUserMapper messageUserMapper;

    @Autowired
    public MessageServiceImpl(MessageMapper messageMapper, MessageUserMapper messageUserMapper) {
        this.messageMapper = messageMapper;
        this.messageUserMapper = messageUserMapper;
    }

    @Override
    public PageInfo<Message> page(MessagePageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                messageMapper.page(requestVM)
        );
    }

    @Override
    @Transactional
    public void sendMessage(Message message, List<MessageUser> messageUsers) {
        messageMapper.insert(message);
        messageUsers.forEach(d -> d.setMessageId(message.getId()));
        messageUserMapper.inserts(messageUsers);
    }

    @Override
    public List<Message> selectMessageByIds(List<Integer> ids) {
        return  messageMapper.selectBatchIds(ids);
    }

    @Override
    public PageInfo<MessageUser> studentPage(MessageRequestVM requestVM) {
        return PageHelper.startPage(requestVM.getPageIndex(), requestVM.getPageSize(), "id desc").doSelectPageInfo(() ->
                messageUserMapper.studentPage(requestVM)
        );
    }

    @Override
    @Transactional
    public void read(Integer id) {
        MessageUser messageUser = messageUserMapper.selectById(id);
        if (messageUser.getReaded())
            return;
        messageUser.setReaded(true);
        messageUser.setReadTime(new Date());
        messageUserMapper.updateById(messageUser);
        messageMapper.readAdd(messageUser.getMessageId());
    }

    @Override
    public Integer unReadCount(Integer id) {
        return messageUserMapper.selectCount(new LambdaQueryWrapper<MessageUser>().eq(MessageUser::getReaded,"f").eq(MessageUser::getReceiveUserId,id));
    }

    @Override
    public Message messageDetail(Integer id) {
        MessageUser messageUser = messageUserMapper.selectById(id);
        return messageMapper.selectById(messageUser.getMessageId());
    }

    @Override
    public List<MessageUser> selectByMessageIds(List<Integer> ids) {
        return messageUserMapper.selectBatchIds(ids);
    }
}
