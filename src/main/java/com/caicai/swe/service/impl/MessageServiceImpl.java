package com.caicai.swe.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.caicai.swe.common.PageResult;
import com.caicai.swe.dto.MessageSendDTO;
import com.caicai.swe.entity.Message;
import com.caicai.swe.mapper.MessageMapper;
import com.caicai.swe.service.MessageService;
import com.caicai.swe.vo.MessageConversationVO;
import com.caicai.swe.vo.MessageVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public MessageVO sendMessage(MessageSendDTO dto, Long currentUserId) {
        Message message = new Message();
        message.setSenderId(currentUserId);
        message.setReceiverId(dto.getReceiverId());
        message.setProductId(dto.getProductId());
        message.setContent(dto.getContent());
        message.setIsRead(false);
        message.setCreateTime(LocalDateTime.now());
        message.setUpdateTime(LocalDateTime.now());
        messageMapper.insert(message);

        return getChatMessages(dto.getReceiverId(), currentUserId, 1, 1).getRecords().get(0);
    }

    @Override
    public PageResult<MessageVO> getChatMessages(Long otherUserId, Long currentUserId, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        var messages = messageMapper.getChatMessages(currentUserId, otherUserId, offset, size);

        // 获取总数
        LambdaQueryWrapper<Message> wrapper = new LambdaQueryWrapper<>();
        wrapper.and(w -> w.eq(Message::getSenderId, currentUserId).eq(Message::getReceiverId, otherUserId))
                .or(w -> w.eq(Message::getSenderId, otherUserId).eq(Message::getReceiverId, currentUserId));
        Long total = messageMapper.selectCount(wrapper);

        return new PageResult<>(messages, total, page, size);
    }

    @Override
    public PageResult<MessageConversationVO> getConversations(Long currentUserId, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        var conversations = messageMapper.getConversations(currentUserId, offset, size);

        // 获取总数（这里简化处理，实际应该查询不同的对话数量）
        Long total = (long) conversations.size();

        return new PageResult<>(conversations, total, page, size);
    }

    @Override
    public Integer markAsRead(Long otherUserId, Long currentUserId) {
        LambdaUpdateWrapper<Message> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Message::getSenderId, otherUserId)
                .eq(Message::getReceiverId, currentUserId)
                .eq(Message::getIsRead, false)
                .set(Message::getIsRead, true);
        return messageMapper.update(null, wrapper);
    }

    @Override
    public Integer getUnreadCount(Long userId) {
        return messageMapper.getUnreadCount(userId);
    }
}