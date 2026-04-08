<template>
  <div class="message-list">
    <el-container>
      <NavBar />
      <el-main>
        <div class="message-container">
          <div class="message-header">
            <h2>消息中心</h2>
            <el-button @click="refreshMessages" :loading="loading" icon="Refresh">刷新</el-button>
          </div>

          <div v-loading="loading" class="conversations">
            <div
              v-for="conversation in conversations"
              :key="conversation.otherUserId"
              class="conversation-item"
              :class="{ active: selectedUserId === conversation.otherUserId }"
              @click="selectConversation(conversation)"
            >
              <el-avatar :size="50" :src="conversation.otherAvatar">
                {{ conversation.otherNickname?.charAt(0) || 'U' }}
              </el-avatar>

              <div class="conversation-info">
                <div class="conversation-header">
                  <span class="username">{{ conversation.otherNickname || conversation.otherUsername }}</span>
                  <span class="time">{{ formatTime(conversation.lastMessageTime) }}</span>
                </div>

                <div class="conversation-preview">
                  <span class="message-preview">{{ conversation.lastMessageContent }}</span>
                  <el-badge v-if="conversation.unreadCount > 0" :value="conversation.unreadCount" class="unread-badge" />
                </div>

                <div v-if="conversation.productId" class="product-preview">
                  <span class="product-tag">商品：{{ conversation.productTitle }}</span>
                </div>
              </div>
            </div>

            <el-empty v-if="conversations.length === 0 && !loading" description="暂无消息" />
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getConversations, getUnreadCount, markAsRead } from '@/api/message'
import type { MessageConversation } from '@/types/message'
import NavBar from '@/components/NavBar.vue'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()
const loading = ref(false)
const conversations = ref<MessageConversation[]>([])
const selectedUserId = ref<number | null>(null)
const unreadCount = ref(0)
let refreshTimer: number | null = null

const loadConversations = async () => {
  loading.value = true
  try {
    const res = await getConversations({ page: 1, size: 50 })
    conversations.value = res.records || []
  } catch (error) {
    ElMessage.error('加载消息失败')
  } finally {
    loading.value = false
  }
}

const loadUnreadCount = async () => {
  try {
    const res = await getUnreadCount()
    unreadCount.value = res || 0
  } catch (error) {
    // 忽略错误
  }
}

const selectConversation = async (conversation: MessageConversation) => {
  selectedUserId.value = conversation.otherUserId

  // 标记为已读
  if (conversation.unreadCount > 0) {
    try {
      await markAsRead(conversation.otherUserId)
      await loadUnreadCount()
      await loadConversations()
    } catch (error) {
      // 忽略错误
    }
  }

  // 跳转到聊天页面
  router.push({
    name: 'MessageChat',
    params: { userId: conversation.otherUserId },
    query: { productId: conversation.productId?.toString() || undefined }
  })
}

const refreshMessages = async () => {
  await Promise.all([loadConversations(), loadUnreadCount()])
  ElMessage.success('刷新成功')
}

const formatTime = (time: string) => {
  const date = new Date(time)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))

  if (days === 0) {
    return date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
  } else if (days === 1) {
    return '昨天'
  } else if (days < 7) {
    return `${days}天前`
  } else {
    return date.toLocaleDateString('zh-CN', { month: '2-digit', day: '2-digit' })
  }
}

onMounted(() => {
  // 检查用户是否登录
  if (!userStore.isLoggedIn()) {
    router.replace('/login')
    return
  }

  loadConversations()
  loadUnreadCount()

  // 每30秒刷新一次未读消息数
  refreshTimer = setInterval(loadUnreadCount, 30000)
})

onUnmounted(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer)
  }
})
</script>

<style scoped lang="scss">
.message-list {
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

    &.active {
      color: #667eea;
      background: rgba(102, 126, 234, 0.1);
    }

    &:hover {
      color: #667eea;
      background: rgba(102, 126, 234, 0.1);
    }
  }

  .message-badge {
    margin-left: 24px;

    :deep(.el-badge__content) {
      background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
      border: none;
      box-shadow: 0 2px 8px rgba(245, 101, 101, 0.4);
    }
  }
}

.el-main {
  max-width: 800px;
  margin: 0 auto;
  padding: 30px 20px;
}

.message-container {
  background: white;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow: hidden;
}

.message-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 24px 32px;
  border-bottom: 1px solid #edf2f7;

  h2 {
    font-size: 24px;
    font-weight: 700;
    margin: 0;
    color: #2d3748;
    display: flex;
    align-items: center;
    gap: 12px;

    &::before {
      content: '';
      width: 4px;
      height: 28px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 2px;
    }
  }

  :deep(.el-button) {
    padding: 10px 20px;
    border-radius: 10px;
    font-weight: 600;
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    border: none;

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
    }
  }
}

.conversations {
  max-height: calc(100vh - 200px);
  overflow-y: auto;
  padding: 8px;
}

.conversation-item {
  display: flex;
  align-items: flex-start;
  gap: 16px;
  padding: 20px;
  border-radius: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;

  &:hover {
    background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
    transform: translateX(4px);
  }

  &.active {
    background: linear-gradient(135deg, rgba(102, 126, 234, 0.1) 0%, rgba(118, 75, 162, 0.1) 100%);
    border-color: #667eea;
  }

  .conversation-info {
    flex: 1;
    min-width: 0;
  }

  .conversation-header {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-bottom: 8px;

    .username {
      font-size: 16px;
      font-weight: 600;
      color: #2d3748;
    }

    .time {
      font-size: 12px;
      color: #a0aec0;
      white-space: nowrap;
    }
  }

  .conversation-preview {
    display: flex;
    align-items: center;
    justify-content: space-between;
    gap: 12px;
    margin-bottom: 8px;

    .message-preview {
      flex: 1;
      font-size: 14px;
      color: #718096;
      overflow: hidden;
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    :deep(.el-badge) {
      .el-badge__content {
        background: linear-gradient(135deg, #f56565 0%, #ed64a6 100%);
        border: none;
        box-shadow: 0 2px 8px rgba(245, 101, 101, 0.4);
      }
    }
  }

  .product-preview {
    .product-tag {
      font-size: 12px;
      color: #667eea;
      background: rgba(102, 126, 234, 0.1);
      padding: 4px 12px;
      border-radius: 6px;
      font-weight: 500;
    }
  }
}

:deep(.el-empty) {
  padding: 60px 0;
}
</style>