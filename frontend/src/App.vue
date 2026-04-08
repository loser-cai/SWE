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

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

html {
  scroll-behavior: smooth;
}

body {
  font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue',
    Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background: #f5f7fa;
  color: #2d3748;
  line-height: 1.6;
}

#app {
  min-height: 100vh;
}

/* 自定义滚动条 */
::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}

::-webkit-scrollbar-track {
  background: #f1f1f1;
  border-radius: 10px;
}

::-webkit-scrollbar-thumb {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 10px;
}

::-webkit-scrollbar-thumb:hover {
  background: linear-gradient(135deg, #764ba2 0%, #f093fb 100%);
}

/* Element Plus 全局样式覆盖 */
.el-message {
  border-radius: 12px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.el-notification {
  border-radius: 16px;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.el-dialog {
  border-radius: 20px;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.el-card {
  border-radius: 16px;
  border: 1px solid rgba(0, 0, 0, 0.05);
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 8px 30px rgba(0, 0, 0, 0.12);
  }
}

.el-button {
  border-radius: 10px;
  font-weight: 500;
  transition: all 0.3s ease;

  &.el-button--primary {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
    }
  }

  &.el-button--success {
    background: linear-gradient(135deg, #48bb78 0%, #38a169 100%);
    border: none;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(72, 187, 120, 0.4);
    }
  }

  &.el-button--danger {
    background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
    border: none;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(245, 101, 101, 0.4);
    }
  }

  &.el-button--warning {
    background: linear-gradient(135deg, #ed8936 0%, #dd6b20 100%);
    border: none;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(237, 137, 54, 0.4);
    }
  }
}
</style>