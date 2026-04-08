package com.caicai.swe.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageVO {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private Long productId;
    private String content;
    private Boolean isRead;
    private LocalDateTime createTime;

    // 发送者信息
    private String senderUsername;
    private String senderNickname;
    private String senderAvatar;

    // 接收者信息
    private String receiverUsername;
    private String receiverNickname;
    private String receiverAvatar;

    // 商品信息
    private String productTitle;
    private Double productPrice;
    private String productImages;
}