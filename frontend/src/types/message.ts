export interface Message {
  id: number
  senderId: number
  receiverId: number
  productId: number | null
  content: string
  isRead: boolean
  createTime: string
  // 发送者信息
  senderUsername: string
  senderNickname: string
  senderAvatar: string
  // 接收者信息
  receiverUsername: string
  receiverNickname: string
  receiverAvatar: string
  // 商品信息
  productTitle: string
  productPrice: number
  productImages: string
}

export interface MessageConversation {
  otherUserId: number
  otherUsername: string
  otherNickname: string
  otherAvatar: string
  lastMessageContent: string
  lastMessageTime: string
  unreadCount: number
  productId: number | null
  productTitle: string
  productPrice: number
  productImages: string
}

export interface MessageSendDTO {
  receiverId: number
  productId: number | null
  content: string
}