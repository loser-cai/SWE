import request from '@/utils/request'
import type { UserRegisterDTO, UserLoginDTO, UserVO } from '@/types/user'

interface LoginResponse {
  user: UserVO
  token: string
}

export const userApi = {
  // 用户注册
  register: (data: UserRegisterDTO) => request.post<number>('/user/register', data),

  // 用户登录
  login: (data: UserLoginDTO) => request.post<LoginResponse>('/user/login', data),

  // 获取用户信息
  getUserInfo: (id: number) => request.get<UserVO>(`/user/info/${id}`),

  // 获取当前用户信息
  getCurrentUserInfo: () => request.get<UserVO>('/user/info'),

  // 更新用户信息
  updateUser: (data: Partial<UserVO>) => request.put<boolean>('/user/update', data)
}