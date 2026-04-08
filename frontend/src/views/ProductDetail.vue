<template>
  <div class="product-detail">
    <el-container>
      <NavBar />

      <el-main v-loading="loading">
        <div v-if="product" class="detail-container">
          <div class="product-content">
            <div class="product-images">
              <div v-if="product.images" class="image-list">
                <img
                  v-for="(image, index) in product.images.split(',')"
                  :key="index"
                  :src="image"
                  :alt="product.title"
                />
              </div>
              <div v-else class="no-image">暂无图片</div>
            </div>

            <div class="product-info">
              <h1 class="product-title">{{ product.title }}</h1>
              <div class="product-price">¥{{ product.price }}</div>

              <div class="product-meta">
                <div class="meta-item">
                  <span class="label">卖家：</span>
                  <span>{{ product.username }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">分类：</span>
                  <span>{{ product.categoryName || '未分类' }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">发布时间：</span>
                  <span>{{ formatDateTime(product.createTime) }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">浏览次数：</span>
                  <span>{{ product.viewCount }}</span>
                </div>
                <div class="meta-item">
                  <span class="label">收藏次数：</span>
                  <span>{{ product.favoriteCount }}</span>
                </div>
              </div>

              <div class="product-description">
                <h3>商品描述</h3>
                <p>{{ product.description || '暂无描述' }}</p>
              </div>

              <div class="product-actions">
                <!-- 发布者显示的按钮 -->
                <template v-if="isOwner">
                  <el-button type="primary" size="large" @click="handleEdit" icon="Edit">
                    编辑商品
                  </el-button>
                  <el-button type="danger" size="large" @click="handleDelete" icon="Delete">
                    删除商品
                  </el-button>
                </template>
                <!-- 买家显示的按钮 -->
                <template v-else>
                  <el-button type="primary" size="large" @click="handleContact">
                    联系卖家
                  </el-button>
                  <el-button size="large" @click="handleFavorite">
                    {{ isFavorited ? '已收藏' : '收藏' }}
                  </el-button>
                </template>
              </div>
            </div>
          </div>

          <div class="comments-section">
            <h3>商品评论</h3>
            <div class="comment-input">
              <el-input
                v-model="commentContent"
                type="textarea"
                :rows="3"
                placeholder="发表评论..."
              />
              <el-button type="primary" @click="handleAddComment" :loading="commentLoading">
                发表评论
              </el-button>
            </div>

            <div class="comment-list">
              <div v-for="comment in comments" :key="comment.id" class="comment-item">
                <div class="comment-header">
                  <span class="comment-user">{{ comment.username }}</span>
                  <span class="comment-time">{{ formatDateTime(comment.createTime) }}</span>
                </div>
                <div class="comment-content">{{ comment.content }}</div>
                <div v-if="comment.parentUsername" class="comment-reply">
                  回复 {{ comment.parentUsername }}
                </div>
              </div>
            </div>

            <div v-if="comments.length === 0" class="empty-comments">
              <el-empty description="暂无评论" />
            </div>
          </div>
        </div>

        <div v-else-if="!loading" class="empty-state">
          <el-empty description="商品不存在" />
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { productApi } from '@/api/product'
import { commentApi, type CommentAddDTO, type CommentVO } from '@/api/comment'
import { favoriteApi } from '@/api/favorite'
import { useUserStore } from '@/stores/user'
import NavBar from '@/components/NavBar.vue'
import type { ProductVO } from '@/types/product'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const loading = ref(false)
const product = ref<ProductVO | null>(null)
const comments = ref<CommentVO[]>([])
const commentContent = ref('')
const commentLoading = ref(false)
const isFavorited = ref(false)

// 判断当前用户是否是商品发布者
const isOwner = computed(() => {
  return userStore.isLoggedIn() && product.value && userStore.userInfo?.id === product.value.userId
})

const formatDateTime = (dateStr: string) => {
  const date = new Date(dateStr)
  return `${date.getFullYear()}-${date.getMonth() + 1}-${date.getDate()} ${date.getHours()}:${date.getMinutes()}`
}

const handleContact = () => {
  if (!userStore.isLoggedIn()) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  if (!product.value?.userId) {
    ElMessage.error('获取卖家信息失败')
    return
  }

  router.push({
    name: 'MessageChat',
    params: { userId: product.value.userId },
    query: { productId: product.value.id.toString() }
  })
}

const handleFavorite = async () => {
  if (!userStore.isLoggedIn()) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  try {
    if (isFavorited.value) {
      await favoriteApi.remove(product.value!.id)
      ElMessage.success('已取消收藏')
      isFavorited.value = false
      if (product.value) {
        product.value.favoriteCount--
      }
    } else {
      await favoriteApi.add(product.value!.id)
      ElMessage.success('收藏成功')
      isFavorited.value = true
      if (product.value) {
        product.value.favoriteCount++
      }
    }
  } catch (error) {
    console.error('操作失败:', error)
  }
}

const handleEdit = () => {
  if (!product.value) return
  router.push(`/products/edit/${product.value.id}`)
}

const handleDelete = async () => {
  if (!product.value) return

  try {
    await ElMessageBox.confirm(
      '确定要删除这个商品吗？此操作不可恢复。',
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    await productApi.delete(product.value.id)
    ElMessage.success('删除成功')
    router.push('/')
  } catch (error: any) {
    if (error !== 'cancel') {
      console.error('删除失败:', error)
      ElMessage.error('删除失败')
    }
  }
}

const handleAddComment = async () => {
  if (!userStore.isLoggedIn()) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  if (!commentContent.value.trim()) {
    ElMessage.warning('请输入评论内容')
    return
  }

  commentLoading.value = true
  try {
    const data: CommentAddDTO = {
      productId: product.value!.id,
      content: commentContent.value,
      parentId: 0
    }
    await commentApi.add(data)
    ElMessage.success('评论成功')
    commentContent.value = ''
    loadComments()
  } catch (error) {
    console.error('评论失败:', error)
  } finally {
    commentLoading.value = false
  }
}

const loadProduct = async () => {
  loading.value = true
  try {
    const productId = Number(route.params.id)
    product.value = await productApi.getDetail(productId)
  } catch (error) {
    console.error('加载商品失败:', error)
    ElMessage.error('加载商品失败')
  } finally {
    loading.value = false
  }
}

const loadComments = async () => {
  try {
    const result = await commentApi.getList(product.value!.id, 1, 20)
    comments.value = result.records
  } catch (error) {
    console.error('加载评论失败:', error)
  }
}

const checkFavorite = async () => {
  if (!userStore.isLoggedIn()) return

  try {
    const favorites = await favoriteApi.getList()
    isFavorited.value = favorites.includes(product.value!.id)
  } catch (error) {
    console.error('检查收藏状态失败:', error)
  }
}

onMounted(async () => {
  await loadProduct()
  if (product.value) {
    await loadComments()
    await checkFavorite()
  }
})
</script>

<style scoped lang="scss">
.product-detail {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e8ecf1 100%);
}

.el-container {
  width: 100%;
  max-width: 1400px;
  margin: 0 auto;
}

.el-main {
  max-width: 1200px;
  margin: 0 auto;
  padding: 30px 20px;
}

.detail-container {
  .product-content {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 40px;
    background: white;
    padding: 40px;
    border-radius: 20px;
    margin-bottom: 40px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  }

  .product-images {
    .image-list {
      display: flex;
      flex-direction: column;
      gap: 16px;

      img {
        width: 100%;
        height: 400px;
        object-fit: cover;
        border-radius: 16px;
        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
        transition: transform 0.3s ease;

        &:hover {
          transform: scale(1.02);
        }
      }
    }

    .no-image {
      width: 100%;
      height: 400px;
      background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
      display: flex;
      align-items: center;
      justify-content: center;
      color: #a0aec0;
      font-size: 16px;
      border-radius: 16px;
    }
  }

  .product-info {
    .product-title {
      font-size: 32px;
      font-weight: 700;
      margin-bottom: 20px;
      color: #2d3748;
      line-height: 1.3;
    }

    .product-price {
      font-size: 42px;
      font-weight: 700;
      color: #f56565;
      margin-bottom: 30px;
      background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
    }

    .product-meta {
      margin-bottom: 30px;
      padding: 24px;
      background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
      border-radius: 16px;
      border: 1px solid #edf2f7;

      .meta-item {
        display: flex;
        margin-bottom: 14px;
        align-items: center;

        &:last-child {
          margin-bottom: 0;
        }

        .label {
          width: 100px;
          color: #718096;
          font-weight: 500;
        }

        span {
          color: #2d3748;
          font-weight: 600;
        }
      }
    }

    .product-description {
      margin-bottom: 30px;

      h3 {
        font-size: 20px;
        font-weight: 600;
        margin-bottom: 16px;
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

      p {
        line-height: 1.8;
        color: #4a5568;
        font-size: 15px;
      }
    }

    .product-actions {
      display: flex;
      gap: 16px;

      :deep(.el-button) {
        padding: 14px 32px;
        font-size: 16px;
        border-radius: 12px;
        font-weight: 600;
        transition: all 0.3s ease;

        &.el-button--primary {
          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          border: none;

          &:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
          }
        }

        &:not(.el-button--primary) {
          background: white;
          border: 2px solid #e2e8f0;

          &:hover {
            border-color: #667eea;
            color: #667eea;
          }
        }
      }
    }
  }

  .comments-section {
    background: white;
    padding: 40px;
    border-radius: 20px;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);

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

    .comment-input {
      margin-bottom: 30px;
      padding: 24px;
      background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
      border-radius: 16px;

      .el-textarea {
        margin-bottom: 16px;

        :deep(.el-textarea__inner) {
          border-radius: 12px;
          border: 2px solid #e2e8f0;
          font-size: 15px;
          padding: 12px;

          &:focus {
            border-color: #667eea;
            box-shadow: 0 0 0 3px rgba(102, 126, 234, 0.1);
          }
        }
      }

      :deep(.el-button) {
        padding: 12px 28px;
        font-size: 15px;
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

    .comment-list {
      .comment-item {
        padding: 24px;
        border-bottom: 1px solid #edf2f7;
        transition: background-color 0.3s ease;

        &:last-child {
          border-bottom: none;
        }

        &:hover {
          background-color: #f7fafc;
          border-radius: 12px;
        }

        .comment-header {
          display: flex;
          justify-content: space-between;
          margin-bottom: 12px;
          align-items: center;

          .comment-user {
            font-weight: 600;
            color: #2d3748;
            font-size: 15px;
          }

          .comment-time {
            color: #a0aec0;
            font-size: 13px;
          }
        }

        .comment-content {
          color: #4a5568;
          line-height: 1.7;
          font-size: 15px;
        }

        .comment-reply {
          margin-top: 12px;
          padding: 12px 16px;
          background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
          border-radius: 12px;
          color: #718096;
          font-size: 14px;
          border-left: 3px solid #667eea;
        }
      }
    }

    .empty-comments {
      padding: 60px 0;
    }
  }
}

.empty-state {
  padding: 80px 0;
  background: white;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
}
</style>