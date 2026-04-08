package com.caicai.swe.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageConversationVO {
    private Long otherUserId;
    private String otherUsername;
    private String otherNickname;
    private String otherAvatar;
    private String lastMessageContent;
    private LocalDateTime lastMessageTime;
    private Integer unreadCount;
    private Long productId;
    private String productTitle;
    private Double productPrice;
    private String productImages;
}