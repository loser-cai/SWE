import { defineStore } from 'pinia'
import { ref } from 'vue'
import type { UserVO } from '@/types/user'
import { storage } from '@/utils/storage'

export const useUserStore = defineStore('user', () => {
  const userInfo = ref<UserVO | null>(null)
  const token = ref<string>(storage.get('token') || '')

  const setUserInfo = (info: UserVO) => {
    userInfo.value = info
  }

  const setToken = (newToken: string) => {
    token.value = newToken
    storage.set('token', newToken)
  }

  const logout = () => {
    userInfo.value = null
    token.value = ''
    storage.remove('token')
  }

  const isLoggedIn = () => {
    return !!token.value
  }

  return {
    userInfo,
    token,
    setUserInfo,
    setToken,
    logout,
    isLoggedIn
  }
})