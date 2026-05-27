-- ============================================================
-- 图书管理系统 — 数据库初始化脚本（三个独立数据库）
-- ============================================================

-- ==================== 1. book_db ====================
DROP DATABASE IF EXISTS book_db;
CREATE DATABASE book_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE book_db;

CREATE TABLE IF NOT EXISTS book (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    title        VARCHAR(200)  NOT NULL COMMENT '书名',
    author       VARCHAR(100)  NOT NULL COMMENT '作者',
    isbn         VARCHAR(20)   NOT NULL COMMENT 'ISBN',
    publisher    VARCHAR(100)  DEFAULT NULL COMMENT '出版社',
    publish_year INT           DEFAULT NULL COMMENT '出版年份',
    category     VARCHAR(50)   DEFAULT NULL COMMENT '分类',
    stock        INT           NOT NULL DEFAULT 0 COMMENT '库存数量',
    price        DECIMAL(10,2) DEFAULT 0.00 COMMENT '价格',
    cover_url    VARCHAR(500)  DEFAULT NULL COMMENT '封面图 URL',
    description  TEXT          DEFAULT NULL COMMENT '简介',
    create_time  DATETIME      DEFAULT CURRENT_TIMESTAMP,
    update_time  DATETIME      DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_isbn (isbn)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='图书表';

INSERT INTO book (title, author, isbn, publisher, publish_year, category, stock, price) VALUES
('深入理解Java虚拟机', '周志明', '978-7-111-42190-8', '机械工业出版社', 2013, '计算机', 10, 79.00),
('Spring实战(第5版)', 'Craig Walls', '978-7-115-51294-3', '人民邮电出版社', 2019, '计算机', 5, 99.00),
('Java并发编程的艺术', '方腾飞', '978-7-111-50159-4', '机械工业出版社', 2015, '计算机', 8, 59.00),
('高性能MySQL(第3版)', 'Baron Schwartz', '978-7-121-19885-1', '电子工业出版社', 2013, '数据库', 3, 128.00);

-- ==================== 2. borrow_db ====================
DROP DATABASE IF EXISTS borrow_db;
CREATE DATABASE borrow_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE borrow_db;

CREATE TABLE IF NOT EXISTS borrow_record (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT   NOT NULL COMMENT '借阅用户 ID',
    book_id     BIGINT   NOT NULL COMMENT '图书 ID',
    borrow_date DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '借阅日期',
    due_date    DATETIME NOT NULL COMMENT '应还日期',
    return_date DATETIME DEFAULT NULL COMMENT '实际归还日期',
    status      TINYINT  DEFAULT 0 COMMENT '状态: 0-借阅中, 1-已归还, 2-逾期',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    KEY idx_user_id (user_id),
    KEY idx_book_id (book_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='借阅记录表';

-- ==================== 3. user_db ====================
DROP DATABASE IF EXISTS user_db;
CREATE DATABASE user_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE user_db;

CREATE TABLE IF NOT EXISTS user (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(50)  NOT NULL COMMENT '用户名',
    password    VARCHAR(128) NOT NULL COMMENT '密码（MD5 加密）',
    nickname    VARCHAR(50)  DEFAULT NULL COMMENT '昵称',
    email       VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    phone       VARCHAR(20)  DEFAULT NULL COMMENT '手机号',
    role        VARCHAR(20)  DEFAULT 'USER' COMMENT '角色',
    status      TINYINT      DEFAULT 1 COMMENT '状态: 0-禁用, 1-启用',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP,
    update_time DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';
