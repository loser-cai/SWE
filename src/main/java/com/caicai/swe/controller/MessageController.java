package com.caicai.swe.controller;

import com.caicai.swe.common.PageResult;
import com.caicai.swe.common.Result;
import com.caicai.swe.dto.MessageSendDTO;
import com.caicai.swe.service.MessageService;
import com.caicai.swe.util.UserContext;
import com.caicai.swe.vo.MessageConversationVO;
import com.caicai.swe.vo.MessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping
    public Result<MessageVO> sendMessage(@RequestBody MessageSendDTO dto) {
        Long currentUserId = UserContext.getUserId();
        MessageVO message = messageService.sendMessage(dto,currentUserId);
        return Result.success(message);
    }

    @GetMapping("/conversations")
    public Result<PageResult<MessageConversationVO>> getConversations(
            @RequestParam(defaultValue="1") Integer page,
            @RequestParam(defaultValue="20") Integer size) {
        Long currentUserId = UserContext.getUserId();
        PageResult<MessageConversationVO> result = messageService.getConversations(currentUserId,page,size);
        return Result.success(result);
    }

    @GetMapping("/conversations/{userId}")
    public Result<PageResult<MessageVO>> getChatMessages(
            @PathVariable Long userId,
            @RequestParam(defaultValue="1") Integer page,
            @RequestParam(defaultValue="20") Integer size) {
        Long currentUserId = UserContext.getUserId();
        PageResult<MessageVO> result = messageService.getChatMessages(userId,currentUserId,page,size);
        return Result.success(result);
    }

    @PostMapping("/read")
    public Result<Void> markAsRead(@RequestParam Long otherUserId) {
        Long currentUserId = UserContext.getUserId();
        messageService.markAsRead(otherUserId,currentUserId);
        return Result.success();
    }

    @GetMapping("/unread/count")
    public Result<Integer> getUnreadCount() {
        Long currentUserId = UserContext.getUserId();
        Integer count = messageService.getUnreadCount(currentUserId);
        return Result.success(count);
    }

    @DeleteMapping("/conversation")
    public Result<Void> deleteConversation(@RequestParam Long otherUserId) {
        // TODO: 实现删除对话功能
        return Result.success();
    }
}