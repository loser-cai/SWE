<template>
  <div class="product-list">
    <el-container>
      <NavBar />

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
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { productApi } from '@/api/product'
import { useAppStore } from '@/stores/app'
import NavBar from '@/components/NavBar.vue'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const route = useRoute()
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

.filter-bar {
  background: white;
  padding: 24px 30px;
  border-radius: 16px;
  margin-bottom: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);

  :deep(.el-select) {
    width: 200px;

    .el-input__wrapper {
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
      }

      &.is-focus {
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.25);
      }
    }
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

.empty-state {
  padding: 80px 0;
  background: white;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.06);
}

.pagination {
  display: flex;
  justify-content: center;
  padding: 30px 0;

  :deep(.el-pagination) {
    .el-pager li {
      border-radius: 8px;
      margin: 0 4px;
      transition: all 0.3s ease;

      &.is-active {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
        color: white;
      }

      &:hover {
        color: #667eea;
      }
    }

    .btn-prev,
    .btn-next {
      border-radius: 8px;
      transition: all 0.3s ease;

      &:hover {
        color: #667eea;
      }
    }
  }
}
</style>