# MVP 快速开始指南

## 项目完成情况

✅ **后端 MVP 已完成**
- 完整的项目结构（entity, dto, vo, mapper, service, controller）
- 数据库配置和 MyBatis 集成
- 统一响应结果封装
- 用户模块（注册、登录、信息管理）
- 商品模块（发布、浏览、搜索、管理）
- 评论模块（发表、查看、删除）
- 分类模块（CRUD）
- 收藏模块（添加、取消、列表）
- 数据库初始化脚本

✅ **前端规划已完成**
- 技术栈选择（3种方案）
- 项目结构设计
- 页面设计（10个核心页面）
- API 接口对接方案
- 组件设计
- 开发计划

✅ **文档已完成**
- README.md - 项目完整说明
- FRONTEND_PLAN.md - 前端项目规划
- API_DOCUMENTATION.md - API 接口文档

---

## 快速启动后端

### 1. 初始化数据库

```bash
# 登录 MySQL
mysql -u root -p

# 执行初始化脚本
source /Users/a0000/work_space/java/SWE/src/main/resources/sql/init.sql
```

或直接在 MySQL 客户端中执行：
```sql
USE swe;
SOURCE /Users/a0000/work_space/java/SWE/src/main/resources/sql/init.sql;
```

### 2. 配置数据库连接

检查 `src/main/resources/application.yml` 中的数据库配置：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/swe?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true
    username: root
    password: root  # 修改为你的 MySQL 密码
```

### 3. 启动应用

```bash
cd /Users/a0000/work_space/java/SWE

# 使用 Maven 启动
mvn spring-boot:run

# 或在 IDE 中运行 SweApplication 类的 main 方法
```

### 4. 测试接口

应用启动后，访问 `http://localhost:8080/api`

测试用户登录：
```bash
curl -X POST http://localhost:8080/api/user/login \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"123456"}'
```

### 5. 启动前端应用

```bash
cd frontend
npm install
npm run dev
```

访问 `http://localhost:5173`

---

## 项目结构说明

```
SWE/
├── src/main/java/com/caicai/swe/
│   ├── common/              # 通用模块
│   │   ├── Result.java      # 统一响应结果
│   │   ├── ResultCode.java  # 响应状态码
│   │   └── PageResult.java  # 分页结果
│   ├── config/              # 配置类
│   │   ├── MyBatisConfig.java
│   │   └── WebConfig.java
│   ├── controller/          # 控制器
│   │   ├── UserController.java
│   │   ├── ProductController.java
│   │   ├── CommentController.java
│   │   ├── CategoryController.java
│   │   └── FavoriteController.java
│   ├── dto/                 # 数据传输对象
│   │   ├── UserLoginDTO.java
│   │   ├── UserRegisterDTO.java
│   │   ├── ProductPublishDTO.java
│   │   ├── ProductQueryDTO.java
│   │   └── CommentAddDTO.java
│   ├── entity/              # 实体类
│   │   ├── User.java
│   │   ├── Product.java
│   │   ├── Category.java
│   │   ├── Comment.java
│   │   └── Favorite.java
│   ├── mapper/              # 数据访问层
│   │   ├── UserMapper.java
│   │   ├── ProductMapper.java
│   │   ├── CommentMapper.java
│   │   ├── CategoryMapper.java
│   │   └── FavoriteMapper.java
│   ├── service/             # 服务层
│   │   ├── UserService.java
│   │   ├── ProductService.java
│   │   ├── CommentService.java
│   │   └── impl/
│   │       ├── UserServiceImpl.java
│   │       ├── ProductServiceImpl.java
│   │       └── CommentServiceImpl.java
│   ├── vo/                  # 视图对象
│   │   ├── UserVO.java
│   │   ├── ProductVO.java
│   │   ├── CommentVO.java
│   │   └── CategoryVO.java
│   └── SweApplication.java  # 启动类
├── src/main/resources/
│   ├── mapper/              # MyBatis XML 映射文件
│   │   ├── UserMapper.xml
│   │   ├── ProductMapper.xml
│   │   ├── CommentMapper.xml
│   │   ├── CategoryMapper.xml
│   │   └── FavoriteMapper.xml
│   ├── sql/
│   │   └── init.sql         # 数据库初始化脚本
│   └── application.yml      # 应用配置
├── README.md                # 项目说明文档
├── FRONTEND_PLAN.md         # 前端项目规划
├── API_DOCUMENTATION.md     # API 接口文档
└── MVP_GUIDE.md             # MVP 快速开始指南
```

---

## 核心功能说明

### 1. 用户模块
- **注册**: `/api/user/register`
- **登录**: `/api/user/login`
- **获取用户信息**: `/api/user/info/{id}`
- **更新用户信息**: `/api/user/update`

### 2. 商品模块
- **发布商品**: `/api/product/publish`
- **获取商品详情**: `/api/product/{id}`
- **获取商品列表**: `/api/product/list`
- **搜索商品**: `/api/product/search`
- **更新商品**: `/api/product/update`
- **删除商品**: `/api/product/{id}`

### 3. 评论模块
- **添加评论**: `/api/comment/add`
- **获取评论列表**: `/api/comment/list`
- **删除评论**: `/api/comment/{id}`

### 4. 分类模块
- **获取所有分类**: `/api/category/list`
- **添加分类**: `/api/category/add`
- **更新分类**: `/api/category/update`
- **删除分类**: `/api/category/{id}`

### 5. 收藏模块
- **添加收藏**: `/api/favorite/add`
- **取消收藏**: `/api/favorite/remove`
- **获取收藏列表**: `/api/favorite/list`

---

## 测试数据

数据库初始化脚本已包含以下测试数据：

### 测试用户
- 用户名: `testuser` / 密码: `123456`
- 用户名: `admin` / 密码: `admin123`

### 商品分类
- 数码电子（手机、电脑、平板）
- 图书教材（考研资料、教材、小说）
- 生活用品
- 运动健身
- 衣物鞋包

### 测试商品
- 二手iPhone 13 (¥3999)
- 考研数学复习全书 (¥35)
- 台灯 (¥45)
- 篮球 (¥80)

---

## 前端开发建议

### 推荐技术栈
3. **Vue 3 Web**: Element Plus + Pinia

### 开发步骤
1. 参阅 `FRONTEND_PLAN.md` 了解项目架构
2. 查看 `API_DOCUMENTATION.md` 了解接口规范
3. 创建前端项目
4. 实现 API 封装
5. 开发核心页面（登录、首页、商品列表、商品详情）
6. 完善其他功能模块

### 注意事项
- 当前版本使用临时用户ID（userId = 1），生产环境需要实现认证
- 图片上传功能待实现，当前使用图片URL字符串
- 密码未加密，生产环境需要使用 BCrypt 等加密算法
- 需要添加参数校验和异常处理

---

## 下一步工作

### 短期优化
1. 实现 JWT 认证
2. 添加参数校验（@Valid）
3. 实现图片上传功能
4. 密码加密
5. 添加日志记录

### 中期优化
1. 添加 Redis 缓存
2. 实现消息通知
3. 添加数据统计
4. 优化查询性能
5. 添加单元测试

### 长期规划
1. 微服务拆分
2. 引入消息队列
3. 实现实时通讯
4. 添加支付功能
5. 用户体验优化

---

## 常见问题

### Q: 启动时报错 "Access denied for user"
**A:** 检查 `application.yml` 中的数据库用户名和密码是否正确

### Q: 接口返回 500 错误
**A:** 查看控制台日志，检查数据库连接是否正常

### Q: 如何修改端口
**A:** 在 `application.yml` 中修改 `server.port` 配置

### Q: 如何添加新的接口
**A:** 参考现有模块，按照 Controller -> Service -> Mapper 的顺序实现

---

## 联系方式

- 邮箱: 2649263674@qq.com
- 项目地址: /Users/a0000/work_space/java/SWE

---

**最后更新：** 2026年4月8日