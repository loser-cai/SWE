import request from '@/utils/request'
import type { PageResult } from '@/types/api'

export interface CommentVO {
  id: number
  productId: number
  userId: number
  username: string
  userAvatar: string | null
  content: string
  parentId: number
  parentUsername: string | null
  createTime: string
}

export interface CommentAddDTO {
  productId: number
  content: string
  parentId?: number
}

export const commentApi = {
  // 添加评论
  add: (data: CommentAddDTO) => request.post<number>('/comment/add', data),

  // 获取评论列表
  getList: (productId: number, page = 1, size = 10) =>
    request.get<PageResult<CommentVO>>('/comment/list', { params: { productId, page, size } }),

  // 删除评论
  delete: (id: number) => request.delete<boolean>(`/comment/${id}`)
}