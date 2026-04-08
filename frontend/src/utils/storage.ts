// 本地存储工具
export const storage = {
  // 获取
  get(key: string): string | null {
    return localStorage.getItem(key)
  },

  // 设置
  set(key: string, value: string): void {
    localStorage.setItem(key, value)
  },

  // 删除
  remove(key: string): void {
    localStorage.removeItem(key)
  },

  // 清空
  clear(): void {
    localStorage.clear()
  }
}