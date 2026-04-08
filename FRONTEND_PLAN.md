# 前端项目规划

## 1. 项目概述

校园/社区二手交易 Web 应用，提供用户友好的二手商品交易平台界面。

## 2. 技术栈

**技术栈：**
- Vue 3 + TypeScript
- Vite 构建工具
- Element Plus UI 组件库
- Pinia 状态管理
- Vue Router 路由管理
- Axios HTTP 客户端

## 3. 项目结构

```
frontend/
├── public/
│   └── favicon.ico
├── src/
│   ├── api/                 # API 接口定义
│   │   ├── user.ts
│   │   ├── product.ts
│   │   ├── comment.ts
│   │   ├── category.ts
│   │   └── favorite.ts
│   ├── assets/              # 静态资源
│   │   └── styles/
│   │       ├── variables.scss
│   │       └── global.scss
│   ├── components/          # 通用组件
│   │   ├── ProductCard.vue
│   │   ├── CommentItem.vue
│   │   └── CategoryTag.vue
│   ├── layouts/             # 布局组件
│   │   ├── MainLayout.vue
│   │   └── AuthLayout.vue
│   ├── router/              # 路由配置
│   │   └── index.ts
│   ├── stores/              # 状态管理（Pinia）
│   │   ├── user.ts
│   │   └── app.ts
│   ├── types/               # TypeScript 类型定义
│   │   ├── user.ts
│   │   ├── product.ts
│   │   └── api.ts
│   ├── utils/               # 工具函数
│   │   ├── request.ts       # Axios 封装
│   │   └── storage.ts       # 本地存储
│   ├── views/               # 页面组件
│   │   ├── Home.vue         # 首页
│   │   ├── Login.vue        # 登录页
│   │   ├── Register.vue     # 注册页
│   │   ├── ProductList.vue  # 商品列表
│   │   ├── ProductDetail.vue # 商品详情
│   │   ├── ProductPublish.vue # 发布商品
│   │   ├── Profile.vue      # 个人中心
│   │   └── Search.vue       # 搜索页
│   ├── App.vue
│   └── main.ts
├── .env.development
├── .env.production
├── .gitignore
├── index.html
├── package.json
├── tsconfig.json
├── vite.config.ts
└── README.md
```

## 4. 页面设计

### 4.1 首页 (Home)

**功能：**

- 轮播图展示热门商品
- 分类导航
- 最新发布商品列表
- 热门推荐

**路由：** `/`

### 4.2 登录页 (Login)

**功能：**

- 用户名/密码登录
- 记住密码
- 跳转注册

**路由：** `/login`

### 4.3 注册页 (Register)

**功能：**

- 用户名注册
- 手机号/邮箱验证
- 密码设置
- 跳转登录

**路由：** `/register`

### 4.4 商品列表页 (ProductList)

**功能：**

- 分类筛选
- 搜索框
- 商品卡片列表
- 下拉刷新/上拉加载更多
- 排序（价格、时间）

**路由：** `/products`

### 4.5 商品详情页 (ProductDetail)

**功能：**

- 商品图片轮播
- 商品信息展示
- 卖家信息
- 商品收藏
- 在线咨询（评论区）
- 立即购买/联系卖家

**路由：** `/products/:id`

### 4.6 发布商品页 (ProductPublish)

**功能：**

- 商品图片上传
- 商品信息填写
- 分类选择
- 价格设置
- 提交发布

**路由：** `/products/publish`

### 4.7 搜索页 (Search)

**功能：**

- 搜索框
- 搜索历史
- 搜索结果列表

**路由：** `/search`

### 4.8 个人中心 (Profile)

**功能：**

- 用户信息展示
- 我的发布
- 我的收藏
- 设置
- 退出登录

**路由：** `/profile`

### 4.9 我的商品 (MyProducts)

**功能：**

- 商品列表
- 状态筛选（在售、已售、已下架）
- 编辑商品
- 下架商品

**路由：** `/profile/products`

### 4.10 我的收藏 (MyFavorites)

**功能：**

- 收藏商品列表
- 取消收藏
- 查看详情

**路由：** `/profile/favorites`

## 5. 核心功能实现

### 5.1 用户认证

- 登录/注册表单
- Token 存储
- 路由守卫
- 自动登录

### 5.2 商品展示

- 商品列表分页加载
- 图片懒加载
- 价格格式化
- 分类筛选

### 5.3 商品发布

- 图片上传（支持多图）
- 表单验证
- 提交确认

### 5.4 商品搜索

- 实时搜索建议
- 搜索历史记录
- 搜索结果高亮

### 5.5 评论系统

- 评论列表展示
- 回复评论
- 发布评论

### 5.6 收藏功能

- 添加/取消收藏
- 收藏列表
- 收藏状态同步

## 6. API 接口对接

### 6.1 基础配置

```typescript
// utils/request.ts
const baseURL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api'

const request = axios.create({
    baseURL,
    timeout: 10000
})

// 请求拦截器
request.interceptors.request.use(
    config => {
        const token = localStorage.getItem('token')
        if (token) {
            config.headers.Authorization = `Bearer ${token}`
        }
        return config
    },
    error => Promise.reject(error)
)

// 响应拦截器
request.interceptors.response.use(
    response => {
        const {code, message, data} = response.data
        if (code === 200) {
            return data
        } else {
            ElMessage.error(message)
            return Promise.reject(new Error(message))
        }
    },
    error => {
        ElMessage.error(error.message)
        return Promise.reject(error)
    }
)
```

### 6.2 用户模块 API

```typescript
// api/user.ts
export const userApi = {
    // 用户注册
    register: (data: UserRegisterDTO) => request.post('/user/register', data),

    // 用户登录
    login: (data: UserLoginDTO) => request.post('/user/login', data),

    // 获取用户信息
    getUserInfo: (id: number) => request.get(`/user/info/${id}`),

    // 更新用户信息
    updateUser: (data: User) => request.put('/user/update', data)
}
```

### 6.3 商品模块 API

```typescript
// api/product.ts
export const productApi = {
    // 发布商品
    publish: (data: ProductPublishDTO) => request.post('/product/publish', data),

    // 获取商品详情
    getDetail: (id: number) => request.get(`/product/${id}`),

    // 获取商品列表
    getList: (params: ProductQueryDTO) => request.get('/product/list', {params}),

    // 搜索商品
    search: (keyword: string, page: number, size: number) =>
        request.get('/product/search', {params: {keyword, page, size}}),

    // 更新商品
    update: (data: Product) => request.put('/product/update', data),

    // 删除商品
    delete: (id: number) => request.delete(`/product/${id}`)
}
```

### 6.4 评论模块 API

```typescript
// api/comment.ts
export const commentApi = {
    // 添加评论
    add: (data: CommentAddDTO) => request.post('/comment/add', data),

    // 获取评论列表
    getList: (productId: number, page: number, size: number) =>
        request.get('/comment/list', {params: {productId, page, size}}),

    // 删除评论
    delete: (id: number) => request.delete(`/comment/${id}`)
}
```

### 6.5 分类模块 API

```typescript
// api/category.ts
export const categoryApi = {
    // 获取所有分类
    getAll: () => request.get('/category/list'),

    // 添加分类
    add: (data: Category) => request.post('/category/add', data),

    // 更新分类
    update: (data: Category) => request.put('/category/update', data),

    // 删除分类
    delete: (id: number) => request.delete(`/category/${id}`)
}
```

### 6.6 收藏模块 API

```typescript
// api/favorite.ts
export const favoriteApi = {
    // 添加收藏
    add: (productId: number) => request.post('/favorite/add', null, {params: {productId}}),

    // 取消收藏
    remove: (productId: number) => request.delete('/favorite/remove', {params: {productId}}),

    // 获取收藏列表
    getList: () => request.get('/favorite/list')
}
```

## 7. 状态管理（Pinia）

### 7.1 用户 Store

```typescript
// stores/user.ts
export const useUserStore = defineStore('user', {
    state: () => ({
        userInfo: null as UserVO | null,
        token: localStorage.getItem('token') || ''
    }),

    actions: {
        setUserInfo(userInfo: UserVO) {
            this.userInfo = userInfo
        },

        setToken(token: string) {
            this.token = token
            localStorage.setItem('token', token)
        },

        logout() {
            this.userInfo = null
            this.token = ''
            localStorage.removeItem('token')
        }
    }
})
```

### 7.2 商品 Store

```typescript
// stores/product.ts
export const useProductStore = defineStore('product', {
    state: () => ({
        currentProduct: null as ProductVO | null,
        productList: [] as ProductVO[],
        total: 0
    }),

    actions: {
        setCurrentProduct(product: ProductVO) {
            this.currentProduct = product
        },

        setProductList(list: ProductVO[], total: number) {
            this.productList = list
            this.total = total
        }
    }
})
```

## 8. 组件设计

### 8.1 ProductCard 组件

```vue
<template>
  <div class="product-card" @click="goToDetail">
    <el-image :src="product.images?.split(',')[0]" fit="cover" />
    <div class="product-info">
      <h3 class="title">{{ product.title }}</h3>
      <div class="price">¥{{ product.price }}</div>
      <div class="meta">
        <span>{{ product.username }}</span>
        <span>{{ formatTime(product.createTime) }}</span>
      </div>
    </div>
  </div>
</template>
```

### 8.2 CommentItem 组件

```vue
<template>
  <div class="comment-item">
    <div class="user-info">
      <el-avatar :src="comment.userAvatar" />
      <span class="username">{{ comment.username }}</span>
      <span class="time">{{ formatTime(comment.createTime) }}</span>
    </div>
    <div class="content">{{ comment.content }}</div>
    <div v-if="comment.parentUsername" class="reply-to">
      回复 {{ comment.parentUsername }}
    </div>
  </div>
</template>
```

## 9. 样式设计

### 9.1 全局样式变量

```scss
// assets/styles/variables.scss
$primary-color: #409eff;
$success-color: #67c23a;
$warning-color: #e6a23c;
$danger-color: #f56c6c;
$info-color: #909399;

$text-primary: #303133;
$text-regular: #606266;
$text-secondary: #909399;
$text-placeholder: #c0c4cc;

$border-color: #dcdfe6;
$border-color-light: #e4e7ed;
$background-color: #f5f7fa;

$border-radius: 4px;
$box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
```

## 10. 部署方案



### 10.1 开发环境

```bash

npm run dev

# 访问 http://localhost:5173

```



### 10.2 生产构建

```bash

npm run build

# 生成 dist 目录

```



### 10.3 部署

- Nginx 静态资源服务

- 配置反向代理到后端 API

- 启用 Gzip 压缩



## 11. 开发计划



### Phase 1: 基础框架搭建

- 项目初始化

- 路由配置

- 基础布局

- API 接口封装



### Phase 2: 用户模块

- 登录/注册页面

- 个人中心

- 认证状态管理



### Phase 3: 商品模块

- 商品列表页

- 商品详情页

- 商品发布页

- 搜索功能



### Phase 4: 交互功能

- 评论系统

- 收藏功能



### Phase 5: 优化完善

- 样式美化

- 响应式适配

- 测试修复



## 12. 注意事项



1. **安全性：**

   - XSS 防护

   - CSRF 防护

   - 敏感信息加密



2. **性能优化：**

   - 图片懒加载

   - 路由懒加载

   - 组件按需加载



3. **用户体验：**

   - 加载状态提示

   - 错误提示

   - 操作反馈



4. **兼容性：**

   - 响应式设计

   - 浏览器兼容性测试



---



**最后更新：** 2026年4月8日