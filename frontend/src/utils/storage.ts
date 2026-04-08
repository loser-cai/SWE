// 本地存储工具
export const storage = {
  // 获取
  get<T = any>(key: string): T | null {
    const value = localStorage.getItem(key)
    if (value === null) return null
    try {
      return JSON.parse(value) as T
    } catch {
      return value as T
    }
  },

  // 设置
  set(key: string, value: any): void {
    const valueStr = typeof value === 'string' ? value : JSON.stringify(value)
    localStorage.setItem(key, valueStr)
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