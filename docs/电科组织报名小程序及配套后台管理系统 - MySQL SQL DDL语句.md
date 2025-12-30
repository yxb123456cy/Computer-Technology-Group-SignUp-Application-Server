# 电科组织报名小程序及配套后台管理系统 - MySQL SQL DDL语句

说明：本SQL脚本包含项目所需全部核心数据表，均添加详细注释、合理索引，未设置外键以降低耦合度；字段类型及长度根据业务场景优化，确保数据存储高效可靠。

```sql

-- ----------------------------
-- 1. 管理员表（admin）：存储后台管理系统唯一管理员账号信息
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `username` VARCHAR(50) NOT NULL DEFAULT 'admin' COMMENT '管理员用户名，默认admin，可自定义',
  `password` VARCHAR(100) NOT NULL COMMENT '管理员密码，MD5+盐值加密存储',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，自动更新',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_admin_username` (`username`) USING BTREE COMMENT '用户名唯一索引，确保管理员账号唯一'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='管理员表';

-- ----------------------------
-- 2. 用户表（user）：存储小程序端学生用户注册及个人信息
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `student_id` VARCHAR(20) NOT NULL COMMENT '学号，作为用户登录账号，唯一',
  `password` VARCHAR(100) NOT NULL COMMENT '用户密码，MD5+盐值加密存储',
  `name` VARCHAR(50) NOT NULL COMMENT '用户姓名',
  `major` VARCHAR(100) DEFAULT '' COMMENT '所属专业，可空',
  `grade` VARCHAR(20) DEFAULT '' COMMENT '年级，如：2023级',
  `class` VARCHAR(20) DEFAULT '' COMMENT '班级，如：计科1班',
  `phone` VARCHAR(20) DEFAULT '' COMMENT '联系电话，11位手机号',
  `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL地址，默认null（使用占位头像）',
  `register_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间，默认当前时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，自动更新',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_user_student_id` (`student_id`) USING BTREE COMMENT '学号唯一索引，确保登录账号不重复',
  KEY `idx_user_name` (`name`) USING BTREE COMMENT '姓名索引，用于用户查询筛选',
  KEY `idx_user_major` (`major`) USING BTREE COMMENT '专业索引，用于用户按专业统计筛选',
  KEY `idx_user_register_time` (`register_time`) USING BTREE COMMENT '注册时间索引，用于按注册时间范围查询'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生用户表';

-- ----------------------------
-- 3. 报名批次表（registration_batch）：存储社团各报名批次的配置信息
-- ----------------------------
DROP TABLE IF EXISTS `registration_batch`;
CREATE TABLE `registration_batch` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `batch_name` VARCHAR(100) NOT NULL COMMENT '批次名称，如：202X年秋季招新',
  `start_time` DATETIME NOT NULL COMMENT '报名开始时间',
  `end_time` DATETIME NOT NULL COMMENT '报名截止时间',
  `notice` TEXT DEFAULT NULL COMMENT '报名须知文本',
  `is_active` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否为当前活跃批次：0-否，1-是（小程序仅展示活跃批次）',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '批次创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，自动更新',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_batch_is_active` (`is_active`) USING BTREE COMMENT '活跃批次唯一索引，确保同一时间只有一个活跃批次',
  KEY `idx_batch_time` (`start_time`,`end_time`) USING BTREE COMMENT '报名时间范围索引，用于查询当前是否有进行中的批次',
  KEY `idx_batch_name` (`batch_name`) USING BTREE COMMENT '批次名称索引，用于快速筛选特定批次'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='报名批次表';

-- ----------------------------
-- 4. 报名记录表（registration_record）：存储学生的报名信息及审核状态
-- ----------------------------
DROP TABLE IF EXISTS `registration_record`;
CREATE TABLE `registration_record` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `student_id` VARCHAR(20) NOT NULL COMMENT '报名学生学号，关联user表student_id',
  `batch_id` INT UNSIGNED NOT NULL COMMENT '报名批次ID，关联registration_batch表id',
  `intention` VARCHAR(100) DEFAULT '' COMMENT '报名意向，多个意向用逗号分隔，如：硬件开发,软件开发',
  `introduction` TEXT DEFAULT NULL COMMENT '个人简介，可空',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '报名状态：0-待审核，1-已通过，2-未通过',
  `review_opinion` TEXT DEFAULT NULL COMMENT '审核意见，未通过时建议填写',
  `review_time` DATETIME DEFAULT NULL COMMENT '审核时间，审核后自动填充',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '报名提交时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，自动更新',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_student_batch` (`student_id`,`batch_id`) USING BTREE COMMENT '学生-批次唯一索引，确保同一学生同一批次仅能报名一次',
  KEY `idx_record_batch_id` (`batch_id`) USING BTREE COMMENT '批次ID索引，用于按批次查询报名记录',
  KEY `idx_record_status` (`status`) USING BTREE COMMENT '报名状态索引，用于按状态筛选（待审核/已通过等）',
  KEY `idx_record_create_time` (`create_time`) USING BTREE COMMENT '报名时间索引，用于按报名时间范围查询',
  KEY `idx_record_review_time` (`review_time`) USING BTREE COMMENT '审核时间索引，用于按审核时间筛选'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='学生报名记录表';

-- ----------------------------
-- 5. 反馈表（feedback）：存储小程序用户提交的反馈信息
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `student_id` VARCHAR(20) DEFAULT NULL COMMENT '提交人学号，关联user表student_id；null表示匿名反馈',
  `type` VARCHAR(50) NOT NULL COMMENT '反馈类型，如：功能异常、建议优化、其他',
  `content` TEXT NOT NULL COMMENT '反馈内容，必填',
  `contact` VARCHAR(50) DEFAULT NULL COMMENT '联系方式，如手机号/微信号，可空',
  `status` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '处理状态：0-未处理，1-已处理',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '反馈提交时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，自动更新',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_feedback_student_id` (`student_id`) USING BTREE COMMENT '提交人学号索引，用于查询特定用户的反馈',
  KEY `idx_feedback_status` (`status`) USING BTREE COMMENT '处理状态索引，用于筛选未处理/已处理反馈',
  KEY `idx_feedback_create_time` (`create_time`) USING BTREE COMMENT '提交时间索引，用于按时间排序查询反馈'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户反馈表';

-- ----------------------------
-- 6. 系统配置表（system_config）：存储项目相关的系统参数配置
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID，自增',
  `config_key` VARCHAR(50) NOT NULL COMMENT '配置项键名，唯一，如：login_lock_time、export_max_num',
  `config_value` VARCHAR(255) NOT NULL COMMENT '配置项值，如：5（分钟）、1000（条）',
  `config_desc` VARCHAR(200) DEFAULT '' COMMENT '配置项描述，说明该配置的作用',
  `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '配置创建时间',
  `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后更新时间，自动更新',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_config_key` (`config_key`) USING BTREE COMMENT '配置项键名唯一索引，确保配置项不重复'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='系统配置表';

-- ----------------------------
-- 初始化系统配置数据（核心默认配置）
-- ----------------------------
INSERT INTO `system_config` (`config_key`, `config_value`, `config_desc`)
VALUES 
('login_lock_time', '5', '登录失败锁定时间（单位：分钟）'),
('export_max_num', '1000', 'Excel导出最大数据量（单位：条）'),
('app_name', '电子科技小组报名小程序', '小程序名称'),
('admin_system_name', '电子科技小组报名管理系统', '后台管理系统名称');

```

## SQL脚本说明

- 数据表覆盖范围：包含管理员表、学生用户表、报名批次表、报名记录表、用户反馈表、系统配置表，完整支撑小程序端和后台管理系统的所有业务场景。

- 索引设计：
        

    - 主键索引：所有表均以id作为主键，采用自增INT UNSIGNED类型，确保唯一性和查询效率。

    - 唯一索引：针对学号、管理员用户名、配置项键名等需要唯一约束的字段设置唯一索引，避免重复数据。

    - 普通索引：针对高频查询字段（如报名状态、批次ID、反馈状态等）设置普通索引，提升筛选和排序效率。

- 字段设计：
        

    - 密码字段：采用VARCHAR(100)，预留足够长度存储MD5+盐值加密后的密码，保障安全性。

    - 时间字段：所有表均包含create_time（创建时间）和update_time（更新时间），update_time设置为自动更新，便于追溯数据变更记录。

    - 文本字段：报名须知、个人简介、反馈内容等长文本采用TEXT类型，普通文本采用VARCHAR并合理设置长度，平衡存储效率和业务需求。

- 初始化数据：系统配置表添加了核心默认配置，无需手动录入即可支撑系统基础运行，后续可通过后台管理系统修改。

- 兼容性：使用utf8mb4字符集，支持中文及特殊字符，适用于各种高校学生信息录入场景；ENGINE=InnoDB支持事务，保障数据操作的原子性。
> （注：文档部分内容可能由 AI 生成）