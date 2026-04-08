import request from '@/utils/request'
import type { ProductPublishDTO, ProductQueryDTO, ProductVO } from '@/types/product'
import type { PageResult } from '@/types/api'

export const productApi = {
  // 发布商品
  publish: (data: ProductPublishDTO) => request.post<number>('/product/publish', data),

  // 获取商品详情
  getDetail: (id: number) => request.get<ProductVO>(`/product/${id}`),

  // 获取商品列表
  getList: (params: ProductQueryDTO) => request.get<PageResult<ProductVO>>('/product/list', { params }),

  // 搜索商品
  search: (keyword: string, page = 1, size = 10) =>
    request.get<PageResult<ProductVO>>('/product/search', { params: { keyword, page, size } }),

  // 更新商品
  update: (data: Partial<ProductVO>) => request.put<boolean>('/product/update', data),

  // 删除商品
  delete: (id: number) => request.delete<boolean>(`/product/${id}`)
}