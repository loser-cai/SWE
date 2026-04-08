// API 响应结果类型
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
}

// 分页结果类型
export interface PageResult<T = any> {
  total: number
  records: T[]
}