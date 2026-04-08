<template>
  <div class="profile">
    <el-container>
      <el-header>
        <div class="header-content">
          <router-link to="/" class="logo">
            <h1>校园二手交易</h1>
          </router-link>
          <div class="header-right">
            <router-link to="/products" class="nav-link">商品列表</router-link>
            <router-link to="/products/publish" class="nav-link">发布商品</router-link>
          </div>
        </div>
      </el-header>

      <el-main>
        <div class="profile-container">
          <div class="user-info">
            <div class="avatar">
              <el-avatar :size="80" :src="userStore.userInfo?.avatar || ''">
                {{ userStore.userInfo?.nickname?.charAt(0) || 'U' }}
              </el-avatar>
            </div>
            <div class="info">
              <h2>{{ userStore.userInfo?.nickname || userStore.userInfo?.username }}</h2>
              <p>{{ userStore.userInfo?.email || userStore.userInfo?.phone || '暂无联系方式' }}</p>
            </div>
            <el-button type="danger" @click="handleLogout">退出登录</el-button>
          </div>

          <el-tabs v-model="activeTab" class="profile-tabs">
            <el-tab-pane label="我的收藏" name="favorites">
              <div v-loading="loading" class="product-grid">
                <div
                  v-for="product in favoriteProducts"
                  :key="product.id"
                  class="product-card"
                  @click="goToDetail(product.id)"
                >
                  <div class="product-image">
                    <img
                      v-if="product.images"
                      :src="product.images.split(',')[0]"
                      :alt="product.title"
                    />
                    <div v-else class="no-image">暂无图片</div>
                  </div>
                  <div class="product-info">
                    <h4 class="product-title">{{ product.title }}</h4>
                    <div class="product-price">¥{{ product.price }}</div>
                  </div>
                </div>
              </div>
              <div v-if="!loading && favoriteProducts.length === 0" class="empty-state">
                <el-empty description="暂无收藏" />
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { favoriteApi } from '@/api/favorite'
import { productApi } from '@/api/product'
import { useUserStore } from '@/stores/user'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const userStore = useUserStore()

const loading = ref(false)
const activeTab = ref('favorites')
const favoriteProducts = ref<ProductVO[]>([])

const handleLogout = () => {
  userStore.logout()
  ElMessage.success('已退出登录')
  router.push('/login')
}

const goToDetail = (id: number) => {
  router.push(`/products/${id}`)
}

const loadFavorites = async () => {
  loading.value = true
  try {
    const favoriteIds = await favoriteApi.getList()
    if (favoriteIds.length > 0) {
      // 批量获取商品详情
      const promises = favoriteIds.map((id) => productApi.getDetail(id))
      const products = await Promise.all(promises)
      favoriteProducts.value = products
    } else {
      favoriteProducts.value = []
    }
  } catch (error) {
    console.error('加载收藏失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  loadFavorites()
})
</script>

<style scoped lang="scss">
.profile {
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

.profile-container {
  .user-info {
    display: flex;
    align-items: center;
    gap: 24px;
    background: white;
    padding: 40px;
    border-radius: 20px;
    margin-bottom: 30px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);

    .avatar {
      flex-shrink: 0;

      :deep(.el-avatar) {
        border: 4px solid white;
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
      }
    }

    .info {
      flex: 1;

      h2 {
        font-size: 28px;
        font-weight: 700;
        margin-bottom: 12px;
        color: #2d3748;
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
      }

      p {
        color: #718096;
        margin: 0;
        font-size: 15px;
      }
    }

    :deep(.el-button) {
      padding: 12px 24px;
      font-size: 15px;
      border-radius: 12px;
      font-weight: 600;
      transition: all 0.3s ease;

      &.el-button--danger {
        background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
        border: none;

        &:hover {
          transform: translateY(-2px);
          box-shadow: 0 8px 20px rgba(245, 101, 101, 0.4);
        }
      }
    }
  }

  .profile-tabs {
    background: white;
    padding: 32px;
    border-radius: 20px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);

    :deep(.el-tabs) {
      .el-tabs__item {
        font-size: 16px;
        font-weight: 600;
        color: #718096;
        padding: 0 24px;
        height: 48px;
        line-height: 48px;

        &.is-active {
          color: #667eea;
        }

        &:hover {
          color: #667eea;
        }
      }

      .el-tabs__active-bar {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        height: 3px;
        border-radius: 2px;
      }

      .el-tabs__content {
        padding-top: 24px;
      }
    }
  }

  .product-grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
    gap: 24px;
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
        background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
        background-clip: text;
      }
    }
  }

  .empty-state {
    padding: 80px 0;
    background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
    border-radius: 16px;
  }
}
</style>