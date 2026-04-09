<template>
  <div class="search">
    <el-container direction="vertical">
      <NavBar />
      <el-main>
        <div class="search-bar">
          <el-input
            v-model="searchKeyword"
            placeholder="请输入搜索关键字"
            size="large"
            @keyup.enter="handleSearch"
          >
            <template #append>
              <el-button icon="Search" @click="handleSearch">搜索</el-button>
            </template>
          </el-input>
        </div>

        <div v-loading="loading" class="search-results">
          <h3 v-if="searchKeyword">
            搜索结果："{{ searchKeyword }}" (共 {{ total }} 条)
          </h3>
          <h3 v-else>热门搜索</h3>

          <div class="product-grid">
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
            <el-empty description="暂无搜索结果" />
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
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { productApi } from '@/api/product'
import NavBar from '@/components/NavBar.vue'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const route = useRoute()

const loading = ref(false)
const products = ref<ProductVO[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)
const searchKeyword = ref('')

const formatDate = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getMonth() + 1}-${date.getDate()}`
}

const handleSearch = () => {
  if (!searchKeyword.value.trim()) {
    ElMessage.warning('请输入搜索关键字')
    return
  }
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
  if (!searchKeyword.value.trim()) return

  loading.value = true
  try {
    const result = await productApi.search(searchKeyword.value, currentPage.value, pageSize.value)
    products.value = result.records
    total.value = result.total
  } catch (error) {
    console.error('搜索失败:', error)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  if (route.query.keyword) {
    searchKeyword.value = route.query.keyword as string
    loadProducts()
  }
})
</script>

<style scoped lang="scss">
.search {
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

.search-bar {
  background: white;
  padding: 32px;
  border-radius: 20px;
  margin-bottom: 30px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);

  :deep(.el-input) {
    .el-input__wrapper {
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
      padding: 16px 20px;
      transition: all 0.3s ease;

      &:hover {
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
      }

      &.is-focus {
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.25);
      }

      .el-input__inner {
        font-size: 16px;
        font-weight: 500;
      }
    }

    .el-input-group__append {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;
      border-radius: 12px;
      padding: 0 24px;
      box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);

      .el-button {
        background: transparent;
        border: none;
        color: white;
        font-size: 16px;
        font-weight: 600;
      }
    }
  }
}

.search-results {
  h3 {
    font-size: 20px;
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

  .empty-state {
    padding: 80px 0;
    background: white;
    border-radius: 20px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
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
}
</style>