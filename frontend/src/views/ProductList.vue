<template>
  <div class="product-list">
    <el-container>
      <el-header>
        <div class="header-content">
          <router-link to="/" class="logo">
            <h1>校园二手交易</h1>
          </router-link>
          <div class="header-right">
            <el-input
              v-model="searchKeyword"
              placeholder="搜索商品"
              prefix-icon="Search"
              @keyup.enter="handleSearch"
              style="width: 300px; margin-right: 20px"
            />
            <router-link v-if="userStore.isLoggedIn()" to="/products/publish" class="nav-link">
              发布商品
            </router-link>
            <router-link v-if="userStore.isLoggedIn()" to="/profile" class="nav-link">
              个人中心
            </router-link>
            <router-link v-else to="/login" class="nav-link">登录</router-link>
          </div>
        </div>
      </el-header>

      <el-main>
        <div class="filter-bar">
          <el-select v-model="selectedCategory" placeholder="选择分类" clearable @change="handleFilterChange">
            <el-option label="全部分类" :value="null" />
            <el-option
              v-for="category in appStore.categories"
              :key="category.id"
              :label="category.name"
              :value="category.id"
            />
          </el-select>
        </div>

        <div v-loading="loading" class="product-grid">
          <div
            v-for="product in products"
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

        <div v-if="!loading && products.length === 0" class="empty-state">
          <el-empty description="暂无商品" />
        </div>

        <div v-if="total > 0" class="pagination">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :total="total"
            :page-sizes="[10, 20, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handlePageChange"
          />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, watch } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { productApi } from '@/api/product'
import { useUserStore } from '@/stores/user'
import { useAppStore } from '@/stores/app'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()
const appStore = useAppStore()

const loading = ref(false)
const products = ref<ProductVO[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const searchKeyword = ref('')
const selectedCategory = ref<number | null>(null)

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getMonth() + 1}-${date.getDate()}`
}

const handleSearch = () => {
  currentPage.value = 1
  loadProducts()
}

const handleFilterChange = () => {
  currentPage.value = 1
  loadProducts()
}

const handleSizeChange = (size: number) => {
  pageSize.value = size
  currentPage.value = 1
  loadProducts()
}

const handlePageChange = (page: number) => {
  currentPage.value = page
  loadProducts()
}

const goToDetail = (id: number) => {
  router.push(`/products/${id}`)
}

const loadProducts = async () => {
  loading.value = true
  try {
    const params: any = {
      page: currentPage.value,
      size: pageSize.value,
      status: 1
    }

    if (searchKeyword.value) {
      params.keyword = searchKeyword.value
    }

    if (selectedCategory.value) {
      params.categoryId = selectedCategory.value
    }

    const result = await productApi.getList(params)
    products.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('加载商品失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  // 从路由参数获取初始值
  if (route.query.keyword) {
    searchKeyword.value = route.query.keyword as string
  }
  if (route.query.categoryId) {
    selectedCategory.value = Number(route.query.categoryId)
  }

  loadProducts()
})
</script>

<style scoped lang="scss">
.product-list {
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

  .logo {
    text-decoration: none;

    h1 {
      font-size: 24px;
      color: #409eff;
      margin: 0;
    }
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

.filter-bar {
  background: white;
  padding: 20px;
  border-radius: 8px;
  margin-bottom: 20px;
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

.empty-state {
  padding: 60px 0;
}

.pagination {
  display: flex;
  justify-content: center;
  padding: 20px 0;
}
</style>