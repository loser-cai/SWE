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
  return request<PageResult<MessageConversation>>({
    url: '/message/conversations',
    method: 'get',
    params
  })
}

// 获取与某个用户的聊天记录
export const getChatMessages = (params: ChatMessagesParams) => {
  return request<PageResult<Message>>({
    url: `/message/conversations/${params.otherUserId}`,
    method: 'get',
    params: {
      page: params.page,
      size: params.size
    }
  })
}

// 发送消息
export const sendMessage = (data: MessageSendDTO) => {
  return request<Message>({
    url: '/message',
    method: 'post',
    data
  })
}

// 标记消息为已读
export const markAsRead = (otherUserId: number) => {
  return request({
    url: '/message/read',
    method: 'post',
    data: { otherUserId }
  })
}

// 删除会话
export const deleteConversation = (otherUserId: number) => {
  return request({
    url: '/message/conversation',
    method: 'delete',
    params: { otherUserId }
  })
}

// 获取未读消息数量
export const getUnreadCount = () => {
  return request<number>({
    url: '/message/unread/count',
    method: 'get'
  })
}