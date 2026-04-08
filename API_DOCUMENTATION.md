# API 接口文档

## 基础信息

**Base URL:** `http://localhost:8080/api`

**Content-Type:** `application/json`

**响应格式：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {}
}
```

**状态码说明：**
- `200` - 操作成功
- `400` - 参数错误
- `401` - 未授权
- `403` - 禁止访问
- `404` - 资源不存在
- `500` - 服务器错误
- `1001` - 用户不存在
- `1002` - 用户已存在
- `1003` - 密码错误
- `2001` - 商品不存在
- `3001` - 评论不存在
- `4001` - 分类不存在

---

## 1. 用户模块

### 1.1 用户注册

**接口地址：** `POST /user/register`

**请求参数：**
```json
{
  "username": "testuser",
  "password": "123456",
  "phone": "13800138000",
  "email": "test@example.com",
  "nickname": "测试用户"
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名（唯一） |
| password | String | 是 | 密码 |
| phone | String | 否 | 手机号（唯一） |
| email | String | 否 | 邮箱（唯一） |
| nickname | String | 否 | 昵称 |

**响应示例：**
```json
{
  "code": 200,
  "message": "注册成功",
  "data": 1
}
```

---

### 1.2 用户登录

**接口地址：** `POST /user/login`

**请求参数：**
```json
{
  "username": "testuser",
  "password": "123456"
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码 |

**响应示例：**
```json
{
  "code": 200,
  "message": "登录成功",
  "data": {
    "id": 1,
    "username": "testuser",
    "nickname": "测试用户",
    "avatar": null,
    "phone": "13800138000",
    "email": "test@example.com",
    "createTime": "2026-04-08T10:00:00"
  }
}
```

---

### 1.3 获取用户信息

**接口地址：** `GET /user/info/{id}`

**路径参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 用户ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "username": "testuser",
    "nickname": "测试用户",
    "avatar": null,
    "phone": "13800138000",
    "email": "test@example.com",
    "createTime": "2026-04-08T10:00:00"
  }
}
```

---

### 1.4 更新用户信息

**接口地址：** `PUT /user/update`

**请求参数：**
```json
{
  "id": 1,
  "nickname": "新昵称",
  "avatar": "http://example.com/avatar.jpg",
  "phone": "13900139000",
  "email": "new@example.com",
  "password": "newpassword"
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 用户ID |
| nickname | String | 否 | 昵称 |
| avatar | String | 否 | 头像URL |
| phone | String | 否 | 手机号 |
| email | String | 否 | 邮箱 |
| password | String | 否 | 新密码 |

**响应示例：**
```json
{
  "code": 200,
  "message": "更新成功",
  "data": true
}
```

---

## 2. 商品模块

### 2.1 发布商品

**接口地址：** `POST /product/publish`

**请求参数：**
```json
{
  "title": "二手iPhone 13",
  "description": "95新，无划痕，配件齐全",
  "price": 3999.00,
  "categoryId": 5,
  "images": "http://example.com/img1.jpg,http://example.com/img2.jpg"
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| title | String | 是 | 商品标题 |
| description | String | 否 | 商品描述 |
| price | BigDecimal | 是 | 价格 |
| categoryId | Long | 否 | 分类ID |
| images | String | 否 | 商品图片（多张用逗号分隔） |

**响应示例：**
```json
{
  "code": 200,
  "message": "发布成功",
  "data": 1
}
```

---

### 2.2 获取商品详情

**接口地址：** `GET /product/{id}`

**路径参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 商品ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "id": 1,
    "userId": 1,
    "username": "testuser",
    "userAvatar": null,
    "title": "二手iPhone 13",
    "description": "95新，无划痕，配件齐全",
    "price": 3999.00,
    "categoryId": 5,
    "categoryName": "手机",
    "images": "http://example.com/img1.jpg,http://example.com/img2.jpg",
    "status": 1,
    "viewCount": 100,
    "favoriteCount": 5,
    "createTime": "2026-04-08T10:00:00"
  }
}
```

---

### 2.3 获取商品列表（分页）

**接口地址：** `GET /product/list`

**Query 参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |
| keyword | String | 否 | 搜索关键字 |
| categoryId | Long | 否 | 分类ID |
| status | Integer | 否 | 状态（1:在售 2:已售出 3:已下架） |
| userId | Long | 否 | 发布者ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 100,
    "records": [
      {
        "id": 1,
        "userId": 1,
        "username": "testuser",
        "userAvatar": null,
        "title": "二手iPhone 13",
        "description": "95新，无划痕，配件齐全",
        "price": 3999.00,
        "categoryId": 5,
        "categoryName": "手机",
        "images": "http://example.com/img1.jpg",
        "status": 1,
        "viewCount": 100,
        "favoriteCount": 5,
        "createTime": "2026-04-08T10:00:00"
      }
    ]
  }
}
```

---

### 2.4 搜索商品

**接口地址：** `GET /product/search`

**Query 参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| keyword | String | 是 | 搜索关键字 |
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 10,
    "records": [
      {
        "id": 1,
        "userId": 1,
        "username": "testuser",
        "userAvatar": null,
        "title": "二手iPhone 13",
        "description": "95新，无划痕，配件齐全",
        "price": 3999.00,
        "categoryId": 5,
        "categoryName": "手机",
        "images": "http://example.com/img1.jpg",
        "status": 1,
        "viewCount": 100,
        "favoriteCount": 5,
        "createTime": "2026-04-08T10:00:00"
      }
    ]
  }
}
```

---

### 2.5 更新商品

**接口地址：** `PUT /product/update`

**请求参数：**
```json
{
  "id": 1,
  "title": "更新后的标题",
  "description": "更新后的描述",
  "price": 3500.00,
  "categoryId": 5,
  "images": "http://example.com/img1.jpg",
  "status": 2
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 商品ID |
| title | String | 否 | 商品标题 |
| description | String | 否 | 商品描述 |
| price | BigDecimal | 否 | 价格 |
| categoryId | Long | 否 | 分类ID |
| images | String | 否 | 商品图片 |
| status | Integer | 否 | 状态（1:在售 2:已售出 3:已下架） |

**响应示例：**
```json
{
  "code": 200,
  "message": "更新成功",
  "data": true
}
```

---

### 2.6 删除商品

**接口地址：** `DELETE /product/{id}`

**路径参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 商品ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "删除成功",
  "data": true
}
```

---

## 3. 评论模块

### 3.1 添加评论

**接口地址：** `POST /comment/add`

**请求参数：**
```json
{
  "productId": 1,
  "content": "这个商品还在吗？",
  "parentId": 0
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productId | Long | 是 | 商品ID |
| content | String | 是 | 评论内容 |
| parentId | Long | 否 | 父评论ID（0表示一级评论） |

**响应示例：**
```json
{
  "code": 200,
  "message": "评论成功",
  "data": 1
}
```

---

### 3.2 获取商品评论列表

**接口地址：** `GET /comment/list`

**Query 参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productId | Long | 是 | 商品ID |
| page | Integer | 否 | 页码，默认1 |
| size | Integer | 否 | 每页数量，默认10 |

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": {
    "total": 10,
    "records": [
      {
        "id": 1,
        "productId": 1,
        "userId": 2,
        "username": "user2",
        "userAvatar": null,
        "content": "这个商品还在吗？",
        "parentId": 0,
        "parentUsername": null,
        "createTime": "2026-04-08T10:00:00"
      }
    ]
  }
}
```

---

### 3.3 删除评论

**接口地址：** `DELETE /comment/{id}`

**路径参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 评论ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "删除成功",
  "data": true
}
```

---

## 4. 分类模块

### 4.1 获取所有分类

**接口地址：** `GET /category/list`

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [
    {
      "id": 1,
      "name": "数码电子",
      "parentId": 0,
      "sort": 1,
      "createTime": "2026-04-08T10:00:00"
    },
    {
      "id": 5,
      "name": "手机",
      "parentId": 1,
      "sort": 1,
      "createTime": "2026-04-08T10:00:00"
    }
  ]
}
```

---

### 4.2 添加分类

**接口地址：** `POST /category/add`

**请求参数：**
```json
{
  "name": "新分类",
  "parentId": 0,
  "sort": 1
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| name | String | 是 | 分类名称 |
| parentId | Long | 否 | 父分类ID，默认0 |
| sort | Integer | 否 | 排序，默认0 |

**响应示例：**
```json
{
  "code": 200,
  "message": "添加成功",
  "data": 1
}
```

---

### 4.3 更新分类

**接口地址：** `PUT /category/update`

**请求参数：**
```json
{
  "id": 1,
  "name": "更新后的分类名",
  "parentId": 0,
  "sort": 2
}
```

**参数说明：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 分类ID |
| name | String | 否 | 分类名称 |
| parentId | Long | 否 | 父分类ID |
| sort | Integer | 否 | 排序 |

**响应示例：**
```json
{
  "code": 200,
  "message": "更新成功",
  "data": true
}
```

---

### 4.4 删除分类

**接口地址：** `DELETE /category/{id}`

**路径参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | Long | 是 | 分类ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "删除成功",
  "data": true
}
```

---

## 5. 收藏模块

### 5.1 添加收藏

**接口地址：** `POST /favorite/add`

**Query 参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productId | Long | 是 | 商品ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "收藏成功",
  "data": true
}
```

---

### 5.2 取消收藏

**接口地址：** `DELETE /favorite/remove`

**Query 参数：**
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| productId | Long | 是 | 商品ID |

**响应示例：**
```json
{
  "code": 200,
  "message": "取消收藏成功",
  "data": true
}
```

---

### 5.3 获取用户收藏的商品ID列表

**接口地址：** `GET /favorite/list`

**响应示例：**
```json
{
  "code": 200,
  "message": "操作成功",
  "data": [1, 2, 3]
}
```

---

## 错误响应示例

```json
{
  "code": 400,
  "message": "参数错误",
  "data": null
}
```

```json
{
  "code": 1001,
  "message": "用户不存在",
  "data": null
}
```

```json
{
  "code": 2001,
  "message": "商品不存在",
  "data": null
}
```

---

## 注意事项

1. **认证：** 目前 MVP 版本使用临时用户ID（userId = 1），生产环境需要实现 JWT 或 Session 认证
2. **时间格式：** 所有时间字段使用 ISO 8601 格式（YYYY-MM-DDTHH:mm:ss）
3. **图片上传：** MVP 版本使用图片 URL 字符串，生产环境需要实现文件上传功能
4. **分页：** 所有列表接口支持分页，page 从 1 开始
5. **CORS：** 后端已配置 CORS，允许跨域请求

---

**最后更新：** 2026年4月8日