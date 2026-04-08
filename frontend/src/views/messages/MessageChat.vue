<template>
  <div class="message-chat">
    <el-container>
      <el-header>
        <div class="header-content">
          <el-button @click="goBack" icon="ArrowLeft" circle />
          <div class="user-info">
            <el-avatar :size="40" :src="otherUser?.avatar">
              {{ otherUser?.nickname?.charAt(0) || 'U' }}
            </el-avatar>
            <div class="user-details">
              <span class="username">{{ otherUser?.nickname || otherUser?.username }}</span>
              <span v-if="product" class="product-tag">{{ product.title }}</span>
            </div>
          </div>
          <div class="header-actions">
            <el-button @click="markAsReadHandler" :disabled="unreadCount === 0" type="primary" size="small">
              标记已读
            </el-button>
          </div>
        </div>
      </el-header>

      <el-main>
        <div class="chat-container">
          <div v-loading="loading" class="messages" ref="messagesContainer">
            <div
              v-for="message in messages"
              :key="message.id"
              class="message-item"
              :class="{ 'is-self': message.senderId === currentUserId }"
            >
              <el-avatar :size="40" :src="message.sender?.avatar">
                {{ message.sender?.nickname?.charAt(0) || 'U' }}
              </el-avatar>
              <div class="message-content">
                <div class="message-header">
                  <span class="sender-name">{{ message.sender?.nickname || message.sender?.username }}</span>
                  <span class="message-time">{{ formatTime(message.createTime) }}</span>
                </div>
                <div class="message-text">{{ message.content }}</div>
              </div>
            </div>

            <el-empty v-if="messages.length === 0 && !loading" description="暂无消息" />
          </div>

          <div class="message-input">
            <el-input
              v-model="newMessage"
              type="textarea"
              :rows="3"
              placeholder="输入消息..."
              @keyup.ctrl.enter="sendMessage"
              :disabled="sending"
            />
            <div class="input-actions">
              <span class="hint">Ctrl + Enter 发送</span>
              <el-button @click="sendMessage" type="primary" :loading="sending" icon="Promotion">
                发送
              </el-button>
            </div>
          </div>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, nextTick, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getChatMessages, sendMessage as sendMessageAPI, markAsRead } from '@/api/message'
import { useUserStore } from '@/stores/user'
import type { Message } from '@/types/message'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const otherUserId = ref<number>(parseInt(route.params.userId as string))
const productId = ref<number | null>(route.query.productId ? parseInt(route.query.productId as string) : null)
const loading = ref(false)
const sending = ref(false)
const messages = ref<Message[]>([])
const newMessage = ref('')
const messagesContainer = ref<HTMLElement>()
const otherUser = ref<any>(null)
const product = ref<any>(null)
const unreadCount = ref(0)
let refreshTimer: NodeJS.Timeout | null = null

const currentUserId = computed(() => userStore.userInfo?.id || 0)

const loadMessages = async () => {
  loading.value = true
  try {
    const res = await getChatMessages({
      otherUserId: otherUserId.value,
      page: 1,
      size: 100
    })
    messages.value = res.list || []
    // 获取对方用户信息和商品信息
    if (messages.value.length > 0) {
      const lastMessage = messages.value[messages.value.length - 1]
      otherUser.value = lastMessage.senderId === currentUserId.value ? lastMessage.receiver : lastMessage.sender
      product.value = lastMessage.product
    }
    scrollToBottom()
  } catch (error) {
    ElMessage.error('加载消息失败')
  } finally {
    loading.value = false
  }
}

const sendMessage = async () => {
  if (!newMessage.value.trim()) {
    ElMessage.warning('请输入消息内容')
    return
  }

  sending.value = true
  try {
    const res = await sendMessageAPI({
      receiverId: otherUserId.value,
      productId: productId.value,
      content: newMessage.value.trim()
    })

    newMessage.value = ''
    await loadMessages()
    ElMessage.success('发送成功')
  } catch (error) {
    ElMessage.error('发送失败')
  } finally {
    sending.value = false
  }
}

const markAsReadHandler = async () => {
  try {
    await markAsRead(otherUserId.value)
    unreadCount.value = 0
    ElMessage.success('已标记为已读')
  } catch (error) {
    ElMessage.error('操作失败')
  }
}

const scrollToBottom = () => {
  nextTick(() => {
    if (messagesContainer.value) {
      messagesContainer.value.scrollTop = messagesContainer.value.scrollHeight
    }
  })
}

const formatTime = (time: string) => {
  const date = new Date(time)
  const now = new Date()
  const diff = now.getTime() - date.getTime()
  const days = Math.floor(diff / (1000 * 60 * 60 * 24))

  if (days === 0) {
    return date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
  } else if (days === 1) {
    return '昨天 ' + date.toLocaleTimeString('zh-CN', { hour: '2-digit', minute: '2-digit' })
  } else if (days < 7) {
    return `${days}天前`
  } else {
    return date.toLocaleDateString('zh-CN', { month: '2-digit', day: '2-digit' })
  }
}

const goBack = () => {
  router.push('/messages')
}

onMounted(() => {
  // 检查用户是否登录
  if (!userStore.isLoggedIn()) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }

  if (!otherUserId.value) {
    ElMessage.error('参数错误')
    goBack()
    return
  }

  loadMessages()

  // 每30秒刷新一次消息
  refreshTimer = setInterval(loadMessages, 30000)
})

onUnmounted(() => {
  if (refreshTimer) {
    clearInterval(refreshTimer)
  }
})
</script>

<style scoped lang="scss">
.message-chat {
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
    gap: 16px;
    width: 100%;
  }

  .user-info {
    display: flex;
    align-items: center;
    gap: 12px;
    flex: 1;

    .user-details {
      display: flex;
      flex-direction: column;
      gap: 4px;

      .username {
        font-size: 16px;
        font-weight: 600;
        color: #2d3748;
      }

      .product-tag {
        font-size: 12px;
        color: #667eea;
        background: rgba(102, 126, 234, 0.1);
        padding: 2px 8px;
        border-radius: 4px;
        font-weight: 500;
      }
    }
  }

  .header-actions {
    :deep(.el-button) {
      border-radius: 8px;
      font-weight: 600;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border: none;

      &:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(102, 126, 234, 0.4);
      }
    }
  }
}

.el-main {
  max-width: 900px;
  margin: 0 auto;
  padding: 30px 20px;
  display: flex;
  flex-direction: column;
  height: calc(100vh - 80px);
}

.chat-container {
  background: white;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);
  overflow: hidden;
  display: flex;
  flex-direction: column;
  height: 100%;
}

.messages {
  flex: 1;
  overflow-y: auto;
  padding: 24px;
  display: flex;
  flex-direction: column;
  gap: 20px;
}

.message-item {
  display: flex;
  gap: 12px;
  max-width: 80%;
  animation: fadeIn 0.3s ease;

  @keyframes fadeIn {
    from {
      opacity: 0;
      transform: translateY(10px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  &.is-self {
    align-self: flex-end;
    flex-direction: row-reverse;

    .message-content {
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      color: white;

      .message-header {
        .sender-name {
          color: rgba(255, 255, 255, 0.9);
        }

        .message-time {
          color: rgba(255, 255, 255, 0.7);
        }
      }
    }
  }

  .message-content {
    background: #f7fafc;
    border-radius: 16px;
    padding: 12px 16px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);

    .message-header {
      display: flex;
      align-items: center;
      gap: 12px;
      margin-bottom: 8px;

      .sender-name {
        font-size: 13px;
        font-weight: 600;
        color: #718096;
      }

      .message-time {
        font-size: 11px;
        color: #a0aec0;
      }
    }

    .message-text {
      font-size: 14px;
      line-height: 1.6;
      color: #2d3748;
      word-break: break-word;
    }
  }
}

.message-input {
  padding: 20px;
  border-top: 1px solid #edf2f7;
  background: white;

  :deep(.el-textarea) {
    .el-textarea__inner {
      border-radius: 12px;
      box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
      padding: 12px 16px;
      font-size: 14px;
      resize: none;

      &:focus {
        border-color: #667eea;
        box-shadow: 0 4px 12px rgba(102, 126, 234, 0.25);
      }
    }
  }

  .input-actions {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 12px;

    .hint {
      font-size: 12px;
      color: #a0aec0;
    }

    :deep(.el-button) {
      padding: 10px 24px;
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
}

:deep(.el-empty) {
  padding: 60px 0;
}

:deep(.el-avatar) {
  flex-shrink: 0;
}
</style>