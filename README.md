# 电科组织报名小程序及配套后台管理系统

> 为高校社团电子科技小组开发的轻量化快捷报名小程序及配套后台管理系统，实现学生线上便捷报名、个人信息管理，以及管理员对报名数据、用户数据的集中管控、统计分析等功能。

## 项目概述

本项目包含两个核心应用：

- **小程序端**：面向高校电子科技小组成员及意向报名学生，提供便捷的在线报名服务
- **后台管理系统**：面向社团管理员，提供报名数据管理、用户管理、数据统计等功能

## 技术栈

### 后端服务

| 技术             | 版本     | 说明        |
|----------------|--------|-----------|
| Java           | 21     | 开发语言      |
| Spring Boot    | 3.5.9  | 应用框架      |
| MyBatis-Plus   | 3.5.15 | ORM框架     |
| MySQL          | Latest | 数据库       |
| Redis          | Latest | 缓存/会话存储   |
| Sa-Token       | 1.44.0 | 权限认证      |
| Knife4j        | 4.4.0  | API文档     |
| EasyExcel      | 4.0.3  | Excel导入导出 |
| Hutool         | 5.8.35 | 工具库       |
| Fastjson2      | 2.0.52 | JSON处理    |
| x-file-storage | 2.3.0  | 文件存储      |
| Minio          | 8.5.2  | 对象存储      |
| 阿里云OSS         | 3.16.1 | 云存储       |
| 微信SDK          | 4.7.0  | 微信集成      |

### 小程序端（规划）

- **框架**：Uni-app / Taro + Vue3 + TypeScript
- **风格**：轻量化、简洁清晰、科技蓝主色调

### 后台管理系统（规划）

- **框架**：React19 + React Compiler + TypeScript + Ant Design v6
- **风格**：专业、简洁、高效

## 核心功能

### 小程序端

| 模块   | 功能               |
|------|------------------|
| 登录注册 | 学号登录、密码注册、记住密码   |
| 报名管理 | 查看报名状态、快速报名、报名记录 |
| 个人中心 | 个人信息编辑、头像设置、密码修改 |
| 用户反馈 | 提交反馈、查看反馈状态      |
| 关于我们 | 社团简介、联系方式        |

### 后台管理系统

| 模块     | 功能                |
|--------|-------------------|
| 仪表板    | 数据概览、报名趋势、最新记录    |
| 报名记录管理 | 查询筛选、审核管理、Excel导出 |
| 用户管理   | 用户查询、重置密码、用户导出    |
| 数据统计   | 用户分布、报名统计、审核通过率   |
| 系统设置   | 管理员设置、小程序配置、反馈管理  |

## 数据库设计

### 核心数据表

| 表名                    | 说明    |
|-----------------------|-------|
| `admin`               | 管理员表  |
| `user`                | 学生用户表 |
| `registration_batch`  | 报名批次表 |
| `registration_record` | 报名记录表 |
| `feedback`            | 用户反馈表 |
| `system_config`       | 系统配置表 |

详细的数据库DDL语句请参考 [docs/电科组织报名小程序及配套后台管理系统 - MySQL SQL DDL语句.md](docs/电科组织报名小程序及配套后台管理系统 - MySQL SQL DDL语句.md)

## 快速开始

### 环境要求

- JDK 21+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

### 使用 Docker Compose 启动依赖服务

```bash
docker-compose up -d
```

这将启动以下服务：
- MySQL (端口 3306)
- Redis (端口 6379)

### 配置数据库

1. 创建数据库并执行DDL脚本：

```bash
mysql -u root -p < docs/电科组织报名小程序及配套后台管理系统\ -\ MySQL\ SQL\ DDL语句.md
```

1. 修改 `application.yml` 中的数据库连接配置

### 启动后端服务

```bash
# 使用 Maven Wrapper
./mvnw spring-boot:run

# 或使用 Maven
mvn spring-boot:run
```

### 访问 API 文档

启动成功后，访问 Knife4j 文档：

```
http://localhost:8080/doc.html
```

## 项目结构

```
Computer-Technology-Group-SignUp-Application-Server/
├── docs/                          # 项目文档
│   ├── 电科组织报名小程序及配套后台管理系统 - MySQL SQL DDL语句.md
│   └── 电科组织报名小程序及配套后台管理系统 - 项目需求与界面需求规划.md
├── src/
│   ├── main/
│   │   ├── java/com/lemon/computer/
│   │   │   ├── cotroller/         # 控制器层
│   │   │   ├── service/           # 服务层
│   │   │   ├── mapper/            # 数据访问层
│   │   │   ├── entity/            # 实体类
│   │   │   ├── dto/               # 数据传输对象
│   │   │   ├── vo/                # 视图对象
│   │   │   ├── common/            # 公共模块
│   │   │   └── config/            # 配置类
│   │   └── resources/
│   │       ├── application.yml   # 应用配置
│   │       └── mapper/            # MyBatis映射文件
│   └── test/                      # SpringBoot单元测试代码
├── vitest/                        # 使用Vitest编写的接口自动化测试
├── compose.yaml                   # Docker Compose配置
├── pom.xml                        # Maven配置
└── README.md                      # 项目说明
```

## API 接口

### 接口规范

- **风格**：restful API
- **请求头**：统一携带 token
- **响应格式**：
```json
{
  "code": 0,
  "msg": "成功",
  "data": {}
}
```

### 核心接口

| 模块   | 路径                           | 说明        |
|------|------------------------------|-----------|
| 管理员  | `/api/v1/admin`              | 管理员相关接口   |
| 用户   | `/api/v1/user`               | 小程序用户相关接口 |
| 报名批次 | `/api/v1/registrationBatch`  | 报名批次管理接口  |
| 报名记录 | `/api/v1/registrationRecord` | 报名记录管理接口  |
| 反馈   | `/api/v1/feedback`           | 用户反馈接口    |
| 文件   | `/api/v1/file`               | 文件上传下载接口  |
| 系统配置 | `/api/v1/systemConfig`       | 系统配置接口    |

## 安全特性

- **密码加密**：MD5 + 盐值加密存储
- **权限认证**：Sa-Token 权限框架
- **接口限流**：防止恶意请求
- **HTTPS**：传输加密

## 性能要求

| 指标      | 要求            |
|---------|---------------|
| 小程序页面加载 | ≤ 2秒          |
| 小程序表单提交 | ≤ 1秒          |
| 后台页面加载  | ≤ 3秒          |
| 后台列表查询  | ≤ 1秒 (≤1000条) |
| Excel导出 | ≤ 5秒 (≤1000条) |

## 开发指南

### 运行测试

```bash
./mvnw test
```

### 打包部署

```bash
./mvnw clean package
```

生成的 JAR 文件位于 `target/` 目录。

## 文档

- [项目需求与界面需求规划](docs/电科组织报名小程序及配套后台管理系统 - 项目需求与界面需求规划.md)
- [MySQL SQL DDL语句](docs/电科组织报名小程序及配套后台管理系统 - MySQL SQL DDL语句.md)

## 许可证

本项目采用 MIT 许可证。

## 联系方式

如有问题或建议，欢迎提交 Issue 或 Pull Request。
