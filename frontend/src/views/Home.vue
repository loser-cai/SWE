<template>
  <div class="home">
    <el-container>
      <el-header>
        <div class="header-content">
          <div class="logo">
            <h1>校园二手交易</h1>
          </div>
          <div class="header-right">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索商品"
              prefix-icon="Search"
              @keyup.enter="handleSearch"
              style="width: 300px; margin-right: 20px"
            />
            <router-link to="/products" class="nav-link">商品列表</router-link>
            <router-link v-if="userStore.isLoggedIn()" to="/products/publish" class="nav-link">
              发布商品
            </router-link>
            <router-link v-if="userStore.isLoggedIn()" to="/profile" class="nav-link">
              个人中心
            </router-link>
            <router-link v-else to="/login" class="nav-link">登录</router-link>
            <router-link v-if="!userStore.isLoggedIn()" to="/register" class="nav-link">
              注册
            </router-link>
            <el-button v-else @click="handleLogout" type="danger" size="small">退出</el-button>
          </div>
        </div>
      </el-header>

      <el-main>
        <div class="banner">
          <h2>欢迎来到校园二手交易平台</h2>
          <p>让闲置物品流转更高效、更安全</p>
        </div>

        <div class="categories">
          <h3>商品分类</h3>
          <div class="category-list">
            <div
              v-for="category in appStore.categories"
              :key="category.id"
              class="category-item"
              @click="handleCategoryClick(category.id)"
            >
              {{ category.name }}
            </div>
          </div>
        </div>

        <div class="latest-products">
          <h3>最新发布</h3>
          <div class="product-grid">
            <div
              v-for="product in latestProducts"
              :key="product.id"
              class="product-card"
              @click="goToDetail(product.id)"
            >
              <div class="product-image">
                <img v-if="product.images" :src="product.images.split(',')[0]" :alt="product.title" />
                <div v-else class="no-image">暂无图片</div>
              </div>
              <div class="product-info">
                <h4 class="product-title">{{ product.title }}</h4>
                <div class="product-price">¥{{ product.price }}</div>
                <div class="product-meta">
                  <span>{{ product.username }}</span>
                  <span>{{ formatDate(product.createTime) }}</span>
                </div>
              </div>
            </div>
          </div>
          <div class="load-more">
            <router-link to="/products">
              <el-button type="primary">查看更多</el-button>
            </router-link>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { productApi } from '@/api/product'
import { useUserStore } from '@/stores/user'
import { useAppStore } from '@/stores/app'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const userStore = useUserStore()
const appStore = useAppStore()

const searchKeyword = ref('')
const latestProducts = ref<ProductVO[]>([])

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getMonth() + 1}-${date.getDate()}`
}

const handleSearch = () => {
  if (searchKeyword.value.trim()) {
    router.push({ path: '/search', query: { keyword: searchKeyword.value } })
  }
}

const handleCategoryClick = (categoryId: number) => {
  router.push({ path: '/products', query: { categoryId } })
}

const goToDetail = (id: number) => {
  router.push(`/products/${id}`)
}

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
}

const loadLatestProducts = async () => {
  try {
    const result = await productApi.getList({ page: 1, size: 8, status: 1 })
    latestProducts.value = result.records
  } catch (error) {
    console.error('加载商品失败:', error)
  }
}

onMounted(() => {
  loadLatestProducts()
})
</script>

<style scoped lang="scss">
.home {
  min-height: 100vh;
  background: #f5f7fa;
}

.el-header {
  background: white;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  padding: 0 40px;

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
  }

  .logo h1 {
    font-size: 24px;
    color: #409eff;
    margin: 0;
  }

  .header-right {
    display: flex;
    align-items: center;
  }

  .nav-link {
    margin-left: 20px;
    color: #606266;
    text-decoration: none;

    &:hover {
      color: #409eff;
    }
  }
}

.el-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  color: white;
  padding: 40px;
  border-radius: 8px;
  text-align: center;
  margin-bottom: 30px;

  h2 {
    font-size: 32px;
    margin-bottom: 10px;
  }

  p {
    font-size: 18px;
    opacity: 0.9;
  }
}

.categories {
  background: white;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 30px;

  h3 {
    margin-bottom: 15px;
    color: #333;
  }

  .category-list {
    display: flex;
    flex-wrap: wrap;
    gap: 10px;
  }

  .category-item {
    padding: 8px 16px;
    background: #f0f2f5;
    border-radius: 4px;
    cursor: pointer;
    transition: all 0.3s;

    &:hover {
      background: #409eff;
      color: white;
    }
  }
}

.latest-products {
  h3 {
    margin-bottom: 20px;
    color: #333;
  }

  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
    gap: 20px;
    margin-bottom: 20px;
  }

  .product-card {
    background: white;
    border-radius: 8px;
    overflow: hidden;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    cursor: pointer;
    transition: transform 0.3s, box-shadow 0.3s;

    &:hover {
      transform: translateY(-4px);
      box-shadow: 0 4px 16px rgba(0, 0, 0, 0.15);
    }

    .product-image {
      width: 100%;
      height: 180px;
      background: #f0f2f5;
      display: flex;
      align-items: center;
      justify-content: center;

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
      }

      .no-image {
        color: #999;
      }
    }

    .product-info {
      padding: 15px;

      .product-title {
        font-size: 16px;
        margin-bottom: 10px;
        color: #333;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }

      .product-price {
        font-size: 20px;
        color: #f56c6c;
        font-weight: bold;
        margin-bottom: 10px;
      }

      .product-meta {
        display: flex;
        justify-content: space-between;
        font-size: 12px;
        color: #999;
      }
    }
  }

  .load-more {
    text-align: center;
  }
}
</style>