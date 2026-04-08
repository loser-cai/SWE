<template>
  <router-view />
</template>

<script setup lang="ts">
import { onMounted } from 'vue'
import { categoryApi } from '@/api/category'
import { useAppStore } from '@/stores/app'

const appStore = useAppStore()

onMounted(async () => {
  // 加载分类数据
  try {
    const categories = await categoryApi.getAll()
    appStore.setCategories(categories)
  } catch (error) {
    console.error('加载分类失败:', error)
  }
})
</script>

<style lang="scss">
@import '@/assets/styles/global.scss';
</style>