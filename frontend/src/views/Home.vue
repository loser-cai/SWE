<template>
  <div class="home">
    <el-container>
      <NavBar />

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
import { productApi } from '@/api/product'
import { useAppStore } from '@/stores/app'
import NavBar from '@/components/NavBar.vue'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const appStore = useAppStore()

const latestProducts = ref<ProductVO[]>([])

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getMonth() + 1}-${date.getDate()}`
}

const handleCategoryClick = (categoryId: number) => {
  router.push({ path: '/products', query: { categoryId } })
}

const goToDetail = (id: number) => {
  router.push(`/products/${id}`)
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
  background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf1 100%);
}

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

  .header-content {
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 100%;
  }

  .logo h1 {
    font-size: 26px;
    font-weight: 700;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    margin: 0;
    letter-spacing: -0.5px;
  }

  .header-right {
    display: flex;
    align-items: center;
  }

  .nav-link {
    margin-left: 24px;
    color: #4a5568;
    text-decoration: none;
    font-weight: 500;
    padding: 8px 16px;
    border-radius: 8px;
    transition: all 0.3s ease;

    &:hover {
      color: #667eea;
      background: rgba(102, 126, 234, 0.1);
    }
  }
}

.el-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

.banner {
  background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #f093fb 100%);
  color: white;
  padding: 60px 40px;
  border-radius: 20px;
  text-align: center;
  margin-bottom: 40px;
  box-shadow: 0 20px 40px rgba(102, 126, 234, 0.3);
  position: relative;
  overflow: hidden;

  &::before {
    content: '';
    position: absolute;
    top: -50%;
    left: -50%;
    width: 200%;
    height: 200%;
    background: radial-gradient(circle, rgba(255, 255, 255, 0.1) 0%, transparent 70%);
    animation: pulse 8s ease-in-out infinite;
  }

  @keyframes pulse {
    0%, 100% {
      transform: scale(1);
      opacity: 0.5;
    }
    50% {
      transform: scale(1.1);
      opacity: 0.8;
    }
  }

  h2 {
    font-size: 42px;
    font-weight: 700;
    margin-bottom: 16px;
    position: relative;
    text-shadow: 0 2px 10px rgba(0, 0, 0, 0.2);
  }

  p {
    font-size: 20px;
    opacity: 0.95;
    position: relative;
  }
}

.categories {
  background: white;
  padding: 30px;
  border-radius: 16px;
  margin-bottom: 40px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);

  h3 {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 20px;
    color: #2d3748;
    display: flex;
    align-items: center;
    gap: 10px;

    &::before {
      content: '';
      width: 4px;
      height: 24px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 2px;
    }
  }

  .category-list {
    display: flex;
    flex-wrap: wrap;
    gap: 12px;
  }

  .category-item {
    padding: 12px 24px;
    background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
    border-radius: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
    font-weight: 500;
    color: #4a5568;
    border: 2px solid transparent;

    &:hover {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(102, 126, 234, 0.3);
    }

    &:active {
      transform: translateY(0);
    }
  }
}

.latest-products {
  h3 {
    font-size: 22px;
    font-weight: 600;
    margin-bottom: 24px;
    color: #2d3748;
    display: flex;
    align-items: center;
    gap: 10px;

    &::before {
      content: '';
      width: 4px;
      height: 24px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 2px;
    }
  }

  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 24px;
    margin-bottom: 30px;
  }

  .product-card {
    background: white;
    border-radius: 16px;
    overflow: hidden;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
    cursor: pointer;
    transition: all 0.4s cubic-bezier(0.4, 0, 0.2, 1);
    border: 1px solid rgba(0, 0, 0, 0.05);

    &:hover {
      transform: translateY(-8px);
      box-shadow: 0 20px 40px rgba(102, 126, 234, 0.2);
      border-color: rgba(102, 126, 234, 0.2);
    }

    .product-image {
      width: 100%;
      height: 200px;
      background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      overflow: hidden;

      &::after {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
        opacity: 0;
        transition: opacity 0.3s ease;
      }

      &:hover::after {
        opacity: 1;
      }

      img {
        width: 100%;
        height: 100%;
        object-fit: cover;
        transition: transform 0.4s ease;
      }

      &:hover img {
        transform: scale(1.05);
      }

      .no-image {
        color: #a0aec0;
        font-size: 14px;
      }
    }

    .product-info {
      padding: 20px;

      .product-title {
        font-size: 17px;
        font-weight: 600;
        margin-bottom: 12px;
        color: #2d3748;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
        line-height: 1.4;
      }

      .product-price {
        font-size: 24px;
        font-weight: 700;
        color: #f56565;
        margin-bottom: 12px;
        background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
      }

      .product-meta {
        display: flex;
        justify-content: space-between;
        align-items: center;
        font-size: 13px;
        color: #718096;
        padding-top: 12px;
        border-top: 1px solid #edf2f7;
      }
    }
  }

  .load-more {
    text-align: center;
    padding: 20px 0;

    :deep(.el-button) {
      padding: 12px 32px;
      font-size: 16px;
      border-radius: 12px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      font-weight: 600;
      transition: all 0.3s ease;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
      }
    }
  }
}
</style>