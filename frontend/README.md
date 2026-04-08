# 校园二手交易 - 前端

校园/社区二手交易 Web 应用前端项目。

## 技术栈

- Vue 3 + TypeScript
- Vite
- Element Plus
- Pinia
- Vue Router
- Axios

## 项目结构

```
frontend/
├── src/
│   ├── api/                 # API 接口定义
│   ├── assets/              # 静态资源
│   │   └── styles/          # 全局样式
│   ├── components/          # 通用组件
│   ├── layouts/             # 布局组件
│   ├── router/              # 路由配置
│   ├── stores/              # 状态管理（Pinia）
│   ├── types/               # TypeScript 类型定义
│   ├── utils/               # 工具函数
│   ├── views/               # 页面组件
│   ├── App.vue
│   └── main.ts
├── public/
├── index.html
├── package.json
├── vite.config.ts
├── tsconfig.json
└── .env.development
```

## 快速开始

### 1. 安装依赖

```bash
npm install
```

### 2. 启动开发服务器

```bash
npm run dev
```

访问 `http://localhost:5173`

### 3. 构建生产版本

```bash
npm run build
```

## 页面说明

- **首页** (`/`) - 展示最新商品和分类
- **登录** (`/login`) - 用户登录
- **注册** (`/register`) - 用户注册
- **商品列表** (`/products`) - 商品浏览和筛选
- **商品详情** (`/products/:id`) - 商品详细信息
- **发布商品** (`/products/publish`) - 发布新商品
- **个人中心** (`/profile`) - 用户信息和收藏
- **搜索** (`/search`) - 商品搜索

## 功能特性

- ✅ 用户注册/登录
- ✅ 商品发布
- ✅ 商品浏览
- ✅ 商品搜索
- ✅ 商品详情
- ✅ 评论系统
- ✅ 收藏功能
- ✅ 个人中心

## 开发说明

### API 接口

所有 API 接口定义在 `src/api/` 目录下，包括：
- `user.ts` - 用户相关接口
- `product.ts` - 商品相关接口
- `comment.ts` - 评论相关接口
- `category.ts` - 分类相关接口
- `favorite.ts` - 收藏相关接口

### 状态管理

使用 Pinia 进行状态管理，Store 定义在 `src/stores/` 目录下：
- `user.ts` - 用户状态
- `app.ts` - 应用全局状态

### 路由配置

路由配置在 `src/router/index.ts`，包含路由守卫和权限控制。

### 样式

全局样式定义在 `src/assets/styles/` 目录下：
- `variables.scss` - 样式变量
- `global.scss` - 全局样式

## 注意事项

1. 当前版本使用模拟 Token，生产环境需要实现真实的 JWT 认证
2. 图片上传功能待实现，当前使用图片 URL 字符串
3. 建议在浏览器中安装 Vue DevTools 进行调试

## 相关文档

- [前端项目规划](../FRONTEND_PLAN.md)
- [API 接口文档](../API_DOCUMENTATION.md)
- [MVP 快速开始指南](../MVP_GUIDE.md)

---

**最后更新：** 2026年4月8日