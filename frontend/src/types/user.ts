// 用户相关类型
export interface UserVO {
  id: number
  username: string
  nickname: string
  avatar: string | null
  phone: string | null
  email: string | null
  createTime: string
}

export interface UserRegisterDTO {
  username: string
  password: string
  phone?: string
  email?: string
  nickname?: string
}

export interface UserLoginDTO {
  username: string
  password: string
}