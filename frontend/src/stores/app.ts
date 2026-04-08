import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useAppStore = defineStore('app', () => {
  const loading = ref(false)
  const categories = ref<any[]>([])

  const setLoading = (value: boolean) => {
    loading.value = value
  }

  const setCategories = (value: any[]) => {
    categories.value = value
  }

  return {
    loading,
    categories,
    setLoading,
    setCategories
  }
})