// 商品相关类型
export interface ProductVO {
  id: number
  userId: number
  username: string
  userAvatar: string | null
  title: string
  description: string | null
  price: number
  categoryId: number | null
  categoryName: string | null
  images: string | null
  status: number
  viewCount: number
  favoriteCount: number
  createTime: string
}

export interface ProductPublishDTO {
  title: string
  description?: string
  price: number
  categoryId?: number
  images?: string
}

export interface ProductQueryDTO {
  page: number
  size: number
  keyword?: string
  categoryId?: number
  status?: number
  userId?: number
}