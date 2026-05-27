package com.book.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("book")
public class Book {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 书名 */
    private String title;

    /** 作者 */
    private String author;

    /** ISBN */
    private String isbn;

    /** 出版社 */
    private String publisher;

    /** 出版年份 */
    private Integer publishYear;

    /** 分类 */
    private String category;

    /** 库存数量 */
    private Integer stock;

    /** 价格 */
    private BigDecimal price;

    /** 封面图 URL */
    private String coverUrl;

    /** 简介 */
    private String description;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
