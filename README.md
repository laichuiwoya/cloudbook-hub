# CloudBook Hub

基于 **Spring Cloud Alibaba** 的图书借阅管理微服务 Demo，适合作为分布式系统课程设计、微服务学习项目或 GitHub 作品集项目。

## 功能

- 用户注册、登录与 JWT 认证
- 图书增删改查、搜索与库存管理
- 图书借阅、归还与借阅记录查询
- Gateway 统一入口与前端静态页面托管
- Nacos 服务注册、发现与配置中心
- OpenFeign 实现服务间调用与降级处理

## 技术栈

| 分类 | 技术 |
| --- | --- |
| 前端 | HTML、CSS、JavaScript |
| 后端 | Spring Boot 2.7.18 |
| 微服务 | Spring Cloud、Spring Cloud Alibaba |
| 网关 | Spring Cloud Gateway |
| 注册配置 | Nacos |
| 服务调用 | OpenFeign、LoadBalancer |
| ORM | MyBatis-Plus |
| 数据库 | MySQL |
| 构建 | Maven 多模块 |

## 项目结构

```text
book-parent/
├── book-common       # 公共实体、DTO、JWT 工具
├── book-service      # 图书服务，端口 8081
├── borrow-service    # 借阅服务，端口 8082
├── user-service      # 用户服务，端口 8083
├── gateway-service   # 网关服务，端口 8080
├── frontend          # 原生前端页面
├── db-init.sql       # 数据库初始化脚本
└── pom.xml
```

## 快速启动

环境要求：

- JDK 8+
- Maven 3.6+
- MySQL 5.7+/8.0
- Nacos 2.x

初始化数据库：

```bash
mysql -u root -p < db-init.sql
```

启动 Nacos：

```bash
startup.cmd -m standalone
```

编译项目：

```bash
mvn clean install -DskipTests
```

分别启动四个服务：

```bash
mvn -pl book-service spring-boot:run
mvn -pl user-service spring-boot:run
mvn -pl borrow-service spring-boot:run
mvn -pl gateway-service spring-boot:run
```

访问系统：

```text
http://localhost:8080/
```

## 服务端口

| 服务 | 端口 |
| --- | --- |
| gateway-service | 8080 |
| book-service | 8081 |
| borrow-service | 8082 |
| user-service | 8083 |
| Nacos | 8848 |

## 主要接口

| 模块 | 接口 |
| --- | --- |
| 用户 | `POST /api/user/register`、`POST /api/user/login`、`GET /api/user/{id}` |
| 图书 | `GET /api/book`、`GET /api/book/search`、`POST /api/book`、`PUT /api/book`、`DELETE /api/book/{id}` |
| 借阅 | `POST /api/borrow`、`PUT /api/borrow/{id}/return`、`GET /api/borrow/user/{userId}` |

## 页面

- `/index.html` 首页
- `/login.html` 登录
- `/register.html` 注册
- `/books.html` 图书列表
- `/borrows.html` 我的借阅

## 说明

默认 MySQL 账号配置为 `root/123456`，如本地密码不同，请修改各服务 `src/main/resources/application.yml`。
