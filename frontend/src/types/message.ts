export interface Message {
  id: number
  senderId: number
  receiverId: number
  productId: number | null
  content: string
  isRead: boolean
  createTime: string
  sender?: {
    id: number
    username: string
    nickname: string
    avatar: string
  }
  receiver?: {
    id: number
    username: string
    nickname: string
    avatar: string
  }
  product?: {
    id: number
    title: string
    price: number
    images: string
  }
}

export interface MessageConversation {
  otherUserId: number
  otherUser: {
    id: number
    username: string
    nickname: string
    avatar: string
  }
  lastMessage: {
    content: string
    createTime: string
  }
  unreadCount: number
  productId: number | null
  product?: {
    id: number
    title: string
    price: number
    images: string
  }
}

export interface MessageSendDTO {
  receiverId: number
  productId: number | null
  content: string
}