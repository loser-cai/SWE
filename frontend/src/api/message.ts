import request from '@/utils/request'
import type { Message, MessageConversation, MessageSendDTO, PageResult } from '@/types'

export interface MessageListParams {
  page?: number
  size?: number
}

export interface ConversationListParams {
  page?: number
  size?: number
}

export interface ChatMessagesParams {
  otherUserId: number
  page?: number
  size?: number
}

// 获取会话列表
export const getConversations = (params: ConversationListParams = {}) => {
  return request.get<PageResult<MessageConversation>>('/message/conversations', { params })
}

// 获取与某个用户的聊天记录
export const getChatMessages = (params: ChatMessagesParams) => {
  return request.get<PageResult<Message>>(`/message/conversations/${params.otherUserId}`, {
    params: {
      page: params.page,
      size: params.size
    }
  })
}

// 发送消息
export const sendMessage = (data: MessageSendDTO) => {
  return request.post<Message>('/message', data)
}

// 标记消息为已读
export const markAsRead = (otherUserId: number) => {
  return request.post('/message/read', null, { params: { otherUserId } })
}

// 删除会话
export const deleteConversation = (otherUserId: number) => {
  return request.delete('/message/conversation', { params: { otherUserId } })
}

// 获取未读消息数量
export const getUnreadCount = () => {
  return request.get<number>('/message/unread/count')
}