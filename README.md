# CloudBook Hub

一个基于 Spring Cloud Alibaba 的图书借阅管理微服务示例项目。项目采用 Maven 多模块结构，将图书、借阅、用户、网关与公共模型拆分为独立模块，适合作为分布式系统课程设计、Spring Cloud 微服务学习 Demo 或 GitHub 作品集项目。

## 项目简介

CloudBook Hub 实现了一个简洁的图书馆借阅系统，包含用户注册登录、图书管理、图书搜索、借阅、归还、借阅记录查询等功能。前端使用原生 HTML/CSS/JavaScript，后端使用 Spring Boot、Spring Cloud Gateway、Nacos、OpenFeign、MyBatis-Plus 和 MySQL。

推荐 GitHub 仓库名：

```text
cloudbook-hub
```

## 功能特性

- 用户注册、登录与 JWT Token 生成
- 图书列表、详情、搜索、新增、修改、删除
- 图书借阅与库存扣减
- 图书归还与借阅状态更新
- 按用户查询借阅记录
- Gateway 统一入口与静态页面托管
- Nacos 服务注册、发现与配置中心
- OpenFeign 服务间调用
- Feign Fallback 降级处理
- Gateway 全局请求日志与 traceId
- MySQL 三库拆分：`book_db`、`borrow_db`、`user_db`

## 技术栈

| 层级 | 技术 |
| --- | --- |
| 前端 | HTML、CSS、JavaScript、Fetch API |
| 后端 | Spring Boot 2.7.18 |
| 微服务 | Spring Cloud 2021.0.9、Spring Cloud Alibaba 2021.0.4.0 |
| 网关 | Spring Cloud Gateway |
| 注册与配置 | Nacos 2.x |
| 服务调用 | OpenFeign、Spring Cloud LoadBalancer |
| ORM | MyBatis-Plus 3.5.3.1 |
| 数据库 | MySQL 5.7+/8.0 |
| 认证 | JWT、MD5 密码摘要 |
| 构建工具 | Maven |
| Java 版本 | JDK 8+ |

## 系统架构

```text
Browser
   |
   | http://localhost:8080
   v
gateway-service 8080
   |-- /api/book/**   -> book-service 8081
   |-- /api/borrow/** -> borrow-service 8082
   |-- /api/user/**   -> user-service 8083
   |
   +-- static pages: index/login/register/books/borrows

borrow-service
   |
   +-- OpenFeign -> book-service

Nacos 8848
   |
   +-- service discovery
   +-- shared config

MySQL
   |
   |-- book_db
   |-- borrow_db
   +-- user_db
```

## 模块说明

```text
book-parent/
├── pom.xml
├── db-init.sql
├── README.md
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── register.html
│   ├── books.html
│   ├── borrows.html
│   ├── css/style.css
│   └── js/
│       ├── api.js
│       └── auth.js
├── book-common/
│   └── 公共实体、DTO、JWT 工具类
├── book-service/
│   └── 图书服务，端口 8081
├── borrow-service/
│   └── 借阅服务，端口 8082
├── user-service/
│   └── 用户服务，端口 8083
└── gateway-service/
    └── 网关服务，端口 8080，同时托管前端静态页面
```

## 环境要求

- JDK 8 或更高版本
- Maven 3.6+
- MySQL 5.7 或 8.0
- Nacos 2.x

默认配置：

| 服务 | 端口 |
| --- | --- |
| gateway-service | 8080 |
| book-service | 8081 |
| borrow-service | 8082 |
| user-service | 8083 |
| Nacos | 8848 |

MySQL 默认连接信息位于各服务的 `application.yml` 中：

```yaml
username: root
password: 123456
```

如果你的本地 MySQL 密码不同，请修改 `book-service`、`borrow-service`、`user-service` 中的 `src/main/resources/application.yml`。

## 快速启动

### 1. 初始化数据库

在 `book-parent` 目录执行：

```bash
mysql -u root -p < db-init.sql
```

脚本会自动创建并初始化：

- `book_db`
- `borrow_db`
- `user_db`

### 2. 启动 Nacos

进入 Nacos 安装目录：

```bash
cd nacos/bin
startup.cmd -m standalone
```

启动后访问：

```text
http://localhost:8848/nacos
```

默认账号密码通常为：

```text
nacos / nacos
```

### 3. 创建 Nacos 共享配置

在 Nacos 控制台创建配置：

- Data ID：`common-config.yaml`
- Group：`DEFAULT_GROUP`
- Format：`YAML`

示例内容：

```yaml
feign:
  client:
    config:
      default:
        connectTimeout: 5000
        readTimeout: 10000
        loggerLevel: BASIC
  compression:
    request:
      enabled: true
      mime-types: text/xml,application/xml,application/json
      min-request-size: 2048
    response:
      enabled: true

spring:
  cloud:
    loadbalancer:
      ribbon:
        enabled: false
      cache:
        enabled: true
        ttl: 30s

logging:
  level:
    com.book: debug
    com.alibaba.nacos: info
```

可选：如果希望演示 `@RefreshScope` 动态配置，可继续创建 `book-service-dev.yaml`：

```yaml
book:
  config:
    max-borrow-days: 30
    max-borrow-count: 5
```

### 4. 编译项目

```bash
mvn clean install -DskipTests
```

### 5. 启动服务

建议按以下顺序分别打开终端启动：

```bash
mvn -pl book-service spring-boot:run
mvn -pl user-service spring-boot:run
mvn -pl borrow-service spring-boot:run
mvn -pl gateway-service spring-boot:run
```

服务全部启动后，访问：

```text
http://localhost:8080/
```

## 页面入口

| 页面 | 路径 | 功能 |
| --- | --- | --- |
| 首页 | `/` 或 `/index.html` | 系统入口与统计展示 |
| 登录 | `/login.html` | 用户登录并保存 Token |
| 注册 | `/register.html` | 创建新用户 |
| 图书列表 | `/books.html` | 浏览、搜索、新增、借阅图书 |
| 我的借阅 | `/borrows.html` | 查看借阅记录与归还图书 |

## API 接口

### 用户服务

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/user/register` | 用户注册，请求体为用户 JSON |
| POST | `/api/user/login?username=&password=` | 用户登录，返回 JWT Token |
| GET | `/api/user/{id}` | 查询用户信息 |

### 图书服务

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| GET | `/api/book` | 查询图书列表 |
| GET | `/api/book/{id}` | 按 ID 查询图书 |
| GET | `/api/book/search?keyword=` | 按书名或作者搜索 |
| POST | `/api/book` | 新增图书 |
| PUT | `/api/book` | 修改图书 |
| DELETE | `/api/book/{id}` | 删除图书 |
| GET | `/api/book/config` | 查看动态配置 |
| PUT | `/api/book/{id}/deduct-stock?count=` | 扣减库存，供借阅服务调用 |

### 借阅服务

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/borrow?userId=&bookId=` | 借阅图书 |
| PUT | `/api/borrow/{id}/return` | 归还图书 |
| GET | `/api/borrow/user/{userId}` | 查询指定用户借阅记录 |
| GET | `/api/borrow` | 查询全部借阅记录 |

## 使用流程

1. 启动 MySQL、Nacos 和四个后端服务。
2. 打开 `http://localhost:8080/`。
3. 注册一个新用户。
4. 登录后进入图书列表。
5. 搜索或浏览图书。
6. 点击借阅，系统会通过 `borrow-service` 调用 `book-service` 扣减库存并生成借阅记录。
7. 进入“我的借阅”页面查看记录，也可以执行归还操作。

## 常见问题

| 问题 | 排查方式 |
| --- | --- |
| 页面无法访问 | 确认 `gateway-service` 已启动，8080 端口未被占用 |
| 接口 404 | 确认通过 Gateway 访问 `/api/**` 路径 |
| 服务调用失败 | 检查 Nacos 服务列表中是否有四个服务实例 |
| 数据库连接失败 | 检查 MySQL 是否启动，账号密码和数据库名是否正确 |
| 借阅失败 | 检查图书库存是否大于 0，`book-service` 是否可用 |
| 登录失败 | 检查用户是否已注册，密码是否正确 |
| Nacos 配置不生效 | 检查 Data ID、Group、namespace 与 `bootstrap.yml` 是否一致 |

## 适合展示的亮点

- 使用 Maven 父子模块管理多个微服务
- 使用 Gateway 统一路由并提供静态页面访问
- 使用 Nacos 完成注册发现和集中配置
- 使用 OpenFeign 实现借阅服务到图书服务的远程调用
- 使用 MyBatis-Plus 简化数据库访问
- 使用 JWT 完成登录态保存
- 采用三数据库拆分，体现微服务数据隔离思路

## License

本项目可作为课程设计、学习 Demo 或个人作品集使用。上传 GitHub 前可根据需要补充自己的开源协议。
