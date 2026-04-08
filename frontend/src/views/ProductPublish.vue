<template>
  <div class="product-publish">
    <el-container>
      <el-header>
        <div class="header-content">
          <router-link to="/" class="logo">
            <h1>校园二手交易</h1>
          </router-link>
          <div class="header-right">
            <router-link to="/products" class="nav-link">商品列表</router-link>
            <router-link to="/profile" class="nav-link">个人中心</router-link>
          </div>
        </div>
      </el-header>

      <el-main>
        <div class="publish-container">
          <h2>发布商品</h2>
          <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
            <el-form-item label="商品标题" prop="title">
              <el-input v-model="form.title" placeholder="请输入商品标题" />
            </el-form-item>

            <el-form-item label="商品描述" prop="description">
              <el-input
                v-model="form.description"
                type="textarea"
                :rows="5"
                placeholder="请输入商品描述"
              />
            </el-form-item>

            <el-form-item label="商品价格" prop="price">
              <el-input-number v-model="form.price" :min="0" :precision="2" />
              <span style="margin-left: 10px">元</span>
            </el-form-item>

            <el-form-item label="商品分类" prop="categoryId">
              <el-select v-model="form.categoryId" placeholder="请选择分类" clearable>
                <el-option
                  v-for="category in appStore.categories"
                  :key="category.id"
                  :label="category.name"
                  :value="category.id"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="商品图片">
              <el-input
                v-model="form.images"
                type="textarea"
                :rows="3"
                placeholder="请输入图片URL，多张图片用逗号分隔"
              />
              <div class="image-tip">
                提示：请输入图片URL，例如：http://example.com/image1.jpg,http://example.com/image2.jpg
              </div>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" @click="handleSubmit" :loading="loading">
                发布商品
              </el-button>
              <el-button @click="handleCancel">取消</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { productApi } from '@/api/product'
import { useAppStore } from '@/stores/app'
import type { FormInstance, FormRules } from 'element-plus'
import type { ProductPublishDTO } from '@/types/product'

const router = useRouter()
const appStore = useAppStore()
const formRef = ref<FormInstance>()
const loading = ref(false)

const form = reactive<ProductPublishDTO>({
  title: '',
  description: '',
  price: 0,
  categoryId: undefined,
  images: ''
})

const rules: FormRules = {
  title: [
    { required: true, message: '请输入商品标题', trigger: 'blur' },
    { min: 2, max: 200, message: '标题长度在2到200个字符', trigger: 'blur' }
  ],
  description: [{ max: 1000, message: '描述最多1000个字符', trigger: 'blur' }],
  price: [{ required: true, message: '请输入商品价格', trigger: 'blur' }]
}

const handleSubmit = async () => {
  if (!formRef.value) return

  await formRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        const productId = await productApi.publish(form)
        ElMessage.success('发布成功')
        router.push(`/products/${productId}`)
      } catch (error) {
        console.error('发布失败:', error)
      } finally {
        loading.value = false
      }
    }
  })
}

const handleCancel = () => {
  router.back()
}
</script>

<style scoped lang="scss">
.product-publish {
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
  max-width: 800px;
  margin: 0 auto;
  padding: 30px 20px;
}

.publish-container {
  background: white;
  padding: 48px;
  border-radius: 20px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.08);

  h2 {
    font-size: 28px;
    font-weight: 700;
    margin-bottom: 40px;
    color: #2d3748;
    display: flex;
    align-items: center;
    gap: 12px;

    &::before {
      content: '';
      width: 5px;
      height: 32px;
      background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      border-radius: 3px;
    }
  }

  :deep(.el-form) {
    .el-form-item {
      margin-bottom: 28px;

      .el-form-item__label {
        font-weight: 600;
        color: #4a5568;
        font-size: 15px;
      }

      .el-input,
      .el-textarea,
      .el-select,
      .el-input-number {
        width: 100%;

        .el-input__wrapper,
        .el-textarea__inner {
          border-radius: 12px;
          box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
          padding: 12px 16px;
          transition: all 0.3s ease;
          border: 2px solid transparent;

          &:hover {
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.15);
          }

          &.is-focus {
            border-color: #667eea;
            box-shadow: 0 4px 12px rgba(102, 126, 234, 0.25);
          }
        }
      }

      .el-input-number {
        .el-input__wrapper {
          width: 100%;
        }
      }
    }

    .el-button {
      padding: 14px 32px;
      font-size: 16px;
      font-weight: 600;
      border-radius: 12px;
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

  .image-tip {
    font-size: 13px;
    color: #a0aec0;
    margin-top: 8px;
    padding: 12px;
    background: linear-gradient(135deg, #f7fafc 0%, #edf2f7 100%);
    border-radius: 8px;
    border-left: 3px solid #667eea;
  }
}
</style>