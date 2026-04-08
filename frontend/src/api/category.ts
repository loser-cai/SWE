import request from '@/utils/request'

export interface CategoryVO {
  id: number
  name: string
  parentId: number
  sort: number
  createTime: string
}

export const categoryApi = {
  // 获取所有分类
  getAll: () => request.get<CategoryVO[]>('/category/list')
}