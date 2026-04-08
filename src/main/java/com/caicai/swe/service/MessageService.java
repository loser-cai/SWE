package com.caicai.swe.service;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.dto.MessageSendDTO;
import com.caicai.swe.vo.MessageConversationVO;
import com.caicai.swe.vo.MessageVO;

public interface MessageService {
    MessageVO sendMessage(MessageSendDTO dto, Long currentUserId);

    PageResult<MessageVO> getChatMessages(Long otherUserId, Long currentUserId, Integer page, Integer size);

    PageResult<MessageConversationVO> getConversations(Long currentUserId, Integer page, Integer size);

    Integer markAsRead(Long otherUserId, Long currentUserId);

    Integer getUnreadCount(Long userId);
}