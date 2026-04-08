# 校园/社区二手交易vue网页端

> 一个为校园和社区用户打造的便捷二手交易平台，让闲置物品流转更高效、更安全。

## 📋 项目概述

本项目是一个典型的 CRUD（增删改查）应用，业务逻辑直观清晰，采用 Spring Boot + MyBatis-Plus
技术栈构建后端服务，提供完整的二手交易功能。系统支持用户注册登录、商品发布、商品浏览、搜索过滤、留言评论等核心功能。

### 项目特点

- **技术成熟**：基于 Spring Boot 3.5.13 + MyBatis-Plus，快速构建稳定可靠的后端服务
- **业务清晰**：经典的 CRUD 业务模型，易于理解和维护
- **贴近生活**：校园/社区二手交易场景，用户易于理解
- **功能完整**：涵盖注册、登录、个人中心、商品管理等完整功能模块
- **易于扩展**：模块化设计，便于后续功能扩展

## 🎯 核心功能

### 用户管理

- 用户注册（手机号/邮箱）
- 用户登录（支持会话管理）
- 个人信息管理
- 密码修改
- 用户注销

### 商品管理

- 发布商品（图片、标题、描述、价格、分类）
- 编辑商品信息
- 删除商品
- 商品上下架管理
- 商品状态跟踪

### 商品浏览

- 首页推荐（最新发布、热门商品）
- 分类浏览
- 商品列表展示
- 商品详情查看
- 商品搜索（关键字搜索）

### 交易功能

- 留言咨询
- 评论回复
- 商品收藏
- 购买意向
- 即时消息（联系卖家）

### 个人中心

- 我的发布
- 我的收藏
- 我的订单
- 个人资料管理

## 🛠 技术栈

### 后端技术

- **框架**: Spring Boot 3.5.13
- **ORM**: MyBatis 3.0.5
- **数据库**: MySQL 8.0+
- **JDK**: Java 17
- **工具库**: Lombok、Apache Commons Lang3

### 前端技术

- **框架**: Vue 3 + TypeScript
- **构建工具**: Vite
- **UI组件库**: Element Plus
- **状态管理**: Pinia
- **路由管理**: Vue Router
- **HTTP客户端**: Axios

## 📁 后端项目结构

```
SWE/
├── src/
│   ├── main/
│   │   ├── java/com/caicai/swe/
│   │   │   ├── controller/          # 控制器层
│   │   │   │   ├── UserController.java
│   │   │   │   ├── ProductController.java
│   │   │   │   └── CommentController.java
│   │   │   ├── service/             # 服务层
│   │   │   │   ├── UserService.java
│   │   │   │   ├── ProductService.java
│   │   │   │   └── CommentService.java
│   │   │   ├── mapper/              # 数据访问层
│   │   │   │   ├── UserMapper.java
│   │   │   │   ├── ProductMapper.java
│   │   │   │   └── CommentMapper.java
│   │   │   ├── entity/              # 实体类
│   │   │   │   ├── User.java
│   │   │   │   ├── Product.java
│   │   │   │   └── Comment.java
│   │   │   ├── dto/                 # 数据传输对象
│   │   │   │   ├── UserDTO.java
│   │   │   │   ├── ProductDTO.java
│   │   │   │   └── CommentDTO.java
│   │   │   ├── vo/                  # 视图对象
│   │   │   │   ├── UserVO.java
│   │   │   │   ├── ProductVO.java
│   │   │   │   └── CommentVO.java
│   │   │   ├── config/              # 配置类
│   │   │   │   ├── MyBatisConfig.java
│   │   │   │   └── WebConfig.java
│   │   │   ├── common/              # 通用模块
│   │   │   │   ├── Result.java      # 统一响应结果
│   │   │   │   ├── ResultCode.java  # 响应状态码
│   │   │   │   └── PageResult.java  # 分页结果
│   │   │   └── SweApplication.java  # 启动类
│   │   └── resources/
│   │       ├── application.yml      # 应用配置
│   │       ├── mapper/              # MyBatis XML映射文件
│   │       │   ├── UserMapper.xml
│   │       │   ├── ProductMapper.xml
│   │       │   └── CommentMapper.xml
│   │       ├── static/              # 静态资源
│   │       └── templates/           # 模板文件
│   └── test/                        # 测试代码
├── pom.xml                          # Maven配置文件
├── .gitignore                       # Git忽略文件
└── README.md                        # 项目说明文档
```

## 📁 前端项目结构

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

## 🗄 数据库设计

### 用户表 (user)

```sql
CREATE TABLE `user`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username`    varchar(50)  NOT NULL COMMENT '用户名',
    `password`    varchar(100) NOT NULL COMMENT '密码',
    `nickname`    varchar(50)  DEFAULT NULL COMMENT '昵称',
    `avatar`      varchar(255) DEFAULT NULL COMMENT '头像',
    `phone`       varchar(20)  DEFAULT NULL COMMENT '手机号',
    `email`       varchar(100) DEFAULT NULL COMMENT '邮箱',
    `status`      tinyint      DEFAULT '1' COMMENT '状态 1:正常 0:禁用',
    `create_time` datetime     DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_phone` (`phone`),
    UNIQUE KEY `uk_email` (`email`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';
```

### 商品表 (product)

```sql
CREATE TABLE `product`
(
    `id`             bigint         NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `user_id`        bigint         NOT NULL COMMENT '发布者ID',
    `title`          varchar(200)   NOT NULL COMMENT '商品标题',
    `description`    text COMMENT '商品描述',
    `price`          decimal(10, 2) NOT NULL COMMENT '价格',
    `category_id`    bigint        DEFAULT NULL COMMENT '分类ID',
    `images`         varchar(1000) DEFAULT NULL COMMENT '商品图片（多张用逗号分隔）',
    `status`         tinyint       DEFAULT '1' COMMENT '状态 1:在售 2:已售出 3:已下架',
    `view_count`     int           DEFAULT '0' COMMENT '浏览次数',
    `favorite_count` int           DEFAULT '0' COMMENT '收藏次数',
    `create_time`    datetime      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`    datetime      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_category_id` (`category_id`),
    KEY `idx_status` (`status`),
    KEY `idx_create_time` (`create_time`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='商品表';
```

### 分类表 (category)

```sql
CREATE TABLE `category`
(
    `id`          bigint      NOT NULL AUTO_INCREMENT COMMENT '分类ID',
    `name`        varchar(50) NOT NULL COMMENT '分类名称',
    `parent_id`   bigint   DEFAULT '0' COMMENT '父分类ID',
    `sort`        int      DEFAULT '0' COMMENT '排序',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_parent_id` (`parent_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='分类表';
```

### 评论表 (comment)

```sql
CREATE TABLE `comment`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `product_id`  bigint       NOT NULL COMMENT '商品ID',
    `user_id`     bigint       NOT NULL COMMENT '评论者ID',
    `content`     varchar(500) NOT NULL COMMENT '评论内容',
    `parent_id`   bigint   DEFAULT '0' COMMENT '父评论ID（0表示一级评论）',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_product_id` (`product_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='评论表';
```

### 收藏表 (favorite)

```sql
CREATE TABLE `favorite`
(
    `id`          bigint NOT NULL AUTO_INCREMENT COMMENT '收藏ID',
    `user_id`     bigint NOT NULL COMMENT '用户ID',
    `product_id`  bigint NOT NULL COMMENT '商品ID',
    `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_product` (`user_id`, `product_id`),
    KEY `idx_product_id` (`product_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='收藏表';
```

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- IDE（推荐 IntelliJ IDEA）

### 1. 克隆项目

```bash
git clone <repository-url>
cd SWE
```

### 2. 创建数据库

```sql
CREATE DATABASE swe CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 配置数据库连接

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/swe?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 4. 执行数据库脚本

运行上述数据库设计中的 SQL 脚本，创建表结构。

### 5. 安装依赖

```bash
mvn clean install
```

### 6. 启动项目

```bash
mvn spring-boot:run
```

或直接运行 `SweApplication` 类的 `main` 方法。

### 7. 访问应用

- 后端应用地址: http://localhost:8080
- 前端应用地址: http://localhost:5173
- API 文档: http://localhost:8080/api（详见 API_DOCUMENTATION.md）

### 8. 启动前端应用

```bash
cd frontend
npm install
npm run dev
```

## 📡 API 接口文档

### 用户模块

#### 用户注册

```http
POST /api/user/register
Content-Type: application/json

{
  "username": "testuser",
  "password": "123456",
  "phone": "13800138000",
  "email": "test@example.com"
}
```

#### 用户登录

```http
POST /api/user/login
Content-Type: application/json

{
  "username": "testuser",
  "password": "123456"
}
```

#### 获取用户信息

```http
GET /api/user/info
Authorization: Bearer <token>
```

### 商品模块

#### 发布商品

```http
POST /api/product/publish
Content-Type: application/json
Authorization: Bearer <token>

{
  "title": "二手自行车",
  "description": "九成新，骑了一年",
  "price": 500.00,
  "categoryId": 1,
  "images": "url1,url2,url3"
}
```

#### 获取商品列表

```http
GET /api/product/list?page=1&size=10&keyword=&categoryId=&status=
```

#### 获取商品详情

```http
GET /api/product/{id}
```

#### 搜索商品

```http
GET /api/product/search?keyword=自行车&page=1&size=10
```

### 评论模块

#### 发表评论

```http
POST /api/comment/add
Content-Type: application/json
Authorization: Bearer <token>

{
  "productId": 1,
  "content": "这个商品还在吗？",
  "parentId": 0
}
```

#### 获取商品评论

```http
GET /api/comment/list?productId=1&page=1&size=10
```

## 🔧 开发指南

### 代码规范

- 遵循阿里巴巴 Java 开发手册
- 使用 Lombok 简化代码
- 统一使用 RESTful API 设计风格
- 统一返回格式（Result）

### 分支管理

- `master`: 主分支，用于生产环境
- `develop`: 开发分支
- `feature/*`: 功能分支
- `bugfix/*`: 修复分支

### 提交规范

```
feat: 新功能
fix: 修复bug
docs: 文档更新
style: 代码格式调整
refactor: 重构
test: 测试相关
chore: 构建/工具相关
```

## 🧪 测试

### 运行单元测试

```bash
mvn test
```

### 运行集成测试

```bash
mvn verify
```

## 📦 部署

### 打包应用

```bash
mvn clean package
```

### 运行 JAR 包

```bash
java -jar target/SWE-0.0.1-SNAPSHOT.jar
```

### Docker 部署

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/SWE-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
docker build -t swe-app .
docker run -p 8080:8080 swe-app
```

## 🤝 贡献指南

欢迎提交 Issue 和 Pull Request！

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'feat: Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 开启 Pull Request

## 📄 许可证

本项目采用 MIT 许可证。

## 👥 作者

- **caicai** - *初始开发* - [GitHub](https://github.com/caicai)

## 📮 联系方式

- 邮箱: 2649263674@qq.com
- 项目地址: [GitHub Repository](https://github.com/caicai/SWE)

## 🙏 致谢

感谢所有为这个项目做出贡献的开发者！

---

**最后更新**: 2026年4月8日