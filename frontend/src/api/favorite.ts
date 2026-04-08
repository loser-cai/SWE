import request from '@/utils/request'

export const favoriteApi = {
  // 添加收藏
  add: (productId: number) => request.post<boolean>('/favorite/add', null, { params: { productId } }),

  // 取消收藏
  remove: (productId: number) => request.delete<boolean>('/favorite/remove', { params: { productId } }),

  // 获取收藏列表
  getList: () => request.get<number[]>('/favorite/list')
}