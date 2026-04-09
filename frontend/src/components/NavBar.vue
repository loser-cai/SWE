<template>
  <el-header>
    <div class="header-content">
      <router-link to="/" class="logo">
        <h1>校园二手交易</h1>
      </router-link>
      <div class="header-right">
        <el-input
          class="search-input"
          v-model="searchKeyword"
          placeholder="搜索商品"
          prefix-icon="Search"
          @keyup.enter="handleSearch"
        />
        <router-link to="/products" class="nav-link">商品列表</router-link>
        <router-link to="/messages" class="nav-link">消息中心</router-link>
        <router-link to="/products/publish" class="nav-link">发布商品</router-link>
        <router-link to="/profile" class="nav-link">个人中心</router-link>
        <router-link to="/login" class="nav-link">登录</router-link>
      </div>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const searchKeyword = ref('')

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push(`/search?keyword=${encodeURIComponent(searchKeyword.value)}`)
  }
}
</script>

<style scoped lang="scss">
.el-header {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.08);
  display: flex;
  align-items: center;
  padding: 0 40px;
  position: sticky;
  top: 0;
  z-index: 100;
  height: 70px;

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
    flex-wrap: wrap;
    gap: 12px;
    min-width: 0;
  }

  .logo {
    text-decoration: none;

    h1 {
      font-size: 26px;
      font-weight: 700;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      margin: 0;
      letter-spacing: -0.5px;
    }
  }

  .header-right {
    display: flex;
    align-items: center;
    justify-content: flex-end;
    flex-wrap: wrap;
    gap: 12px;
    min-width: 0;
    flex: 1 1 0;

    :deep(.search-input) {
      width: 100%;
      max-width: 320px;
      flex: 1 1 240px;
      min-width: 180px;
    }

    .nav-link {
      text-decoration: none;
      color: #4a5568;
      font-weight: 500;
      font-size: 15px;
      padding: 8px 16px;
      border-radius: 8px;
      transition: all 0.3s ease;
      position: relative;

      &:hover {
        color: #667eea;
        background: rgba(102, 126, 234, 0.08);
      }

      &.router-link-active {
        color: #667eea;
        background: rgba(102, 126, 234, 0.1);
        font-weight: 600;
      }
    }
  }
}
</style>