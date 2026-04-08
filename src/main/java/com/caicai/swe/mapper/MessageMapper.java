package com.caicai.swe.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.caicai.swe.entity.Message;
import com.caicai.swe.vo.MessageConversationVO;
import com.caicai.swe.vo.MessageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MessageMapper extends BaseMapper<Message> {

    @Select("SELECT m.*, " +
            "u1.username as senderUsername, u1.nickname as senderNickname, u1.avatar as senderAvatar, " +
            "u2.username as receiverUsername, u2.nickname as receiverNickname, u2.avatar as receiverAvatar, " +
            "p.title as productTitle, p.price as productPrice, p.images as productImages " +
            "FROM message m " +
            "LEFT JOIN user u1 ON m.sender_id = u1.id " +
            "LEFT JOIN user u2 ON m.receiver_id = u2.id " +
            "LEFT JOIN product p ON m.product_id = p.id " +
            "WHERE (m.sender_id = #{currentUserId} AND m.receiver_id = #{otherUserId}) " +
            "OR (m.sender_id = #{otherUserId} AND m.receiver_id = #{currentUserId}) " +
            "ORDER BY m.create_time DESC " +
            "LIMIT #{offset}, #{size}")
    List<MessageVO> getChatMessages(@Param("currentUserId") Long currentUserId,
                                    @Param("otherUserId") Long otherUserId,
                                    @Param("offset") Integer offset,
                                    @Param("size") Integer size);

    @Select("SELECT " +
            "otherUserId, " +
            "u.username as otherUsername, " +
            "u.nickname as otherNickname, " +
            "u.avatar as otherAvatar, " +
            "m.content as lastMessageContent, " +
            "m.create_time as lastMessageTime, " +
            "conversations.unreadCount, " +
            "m.product_id as productId, " +
            "p.title as productTitle, " +
            "p.price as productPrice, " +
            "p.images as productImages " +
            "FROM (" +
            "  SELECT " +
            "  IF(m.sender_id = #{currentUserId}, m.receiver_id, m.sender_id) as otherUserId, " +
            "  MAX(m.create_time) as lastMessageTime, " +
            "  SUM(CASE WHEN m.receiver_id = #{currentUserId} AND m.is_read = false THEN 1 ELSE 0 END) as unreadCount " +
            "  FROM message m " +
            "  WHERE m.sender_id = #{currentUserId} OR m.receiver_id = #{currentUserId} " +
            "  GROUP BY IF(m.sender_id = #{currentUserId}, m.receiver_id, m.sender_id)" +
            ") conversations " +
            "INNER JOIN message m ON conversations.otherUserId = IF(m.sender_id = #{currentUserId}, m.receiver_id, m.sender_id) " +
            "  AND m.create_time = conversations.lastMessageTime " +
            "LEFT JOIN user u ON conversations.otherUserId = u.id " +
            "LEFT JOIN product p ON m.product_id = p.id " +
            "ORDER BY conversations.lastMessageTime DESC " +
            "LIMIT #{offset}, #{size}")
    List<MessageConversationVO> getConversations(@Param("currentUserId") Long currentUserId,
                                                 @Param("offset") Integer offset,
                                                 @Param("size") Integer size);

    @Select("SELECT COUNT(*) FROM message WHERE receiver_id = #{userId} AND is_read = false")
    Integer getUnreadCount(@Param("userId") Long userId);
}