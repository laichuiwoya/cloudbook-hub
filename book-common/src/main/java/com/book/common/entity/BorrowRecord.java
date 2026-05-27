package com.book.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("borrow_record")
public class BorrowRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 借阅用户 ID */
    private Long userId;

    /** 图书 ID */
    private Long bookId;

    /** 借阅日期 */
    private LocalDateTime borrowDate;

    /** 应还日期 */
    private LocalDateTime dueDate;

    /** 实际归还日期 */
    private LocalDateTime returnDate;

    /** 状态: 0-借阅中, 1-已归还, 2-逾期 */
    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
