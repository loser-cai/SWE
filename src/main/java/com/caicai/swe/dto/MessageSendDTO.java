package com.caicai.swe.dto;

import lombok.Data;

@Data
public class MessageSendDTO {
    private Long receiverId;
    private Long productId;
    private String content;
}