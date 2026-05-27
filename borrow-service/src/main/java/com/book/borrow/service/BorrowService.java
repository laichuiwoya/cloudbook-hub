package com.book.borrow.service;

import com.book.common.entity.BorrowRecord;

import java.util.List;

public interface BorrowService {

    /** 借阅图书 */
    BorrowRecord borrow(Long userId, Long bookId);

    /** 归还图书 */
    void returnBook(Long borrowId);

    /** 查询用户借阅记录 */
    List<BorrowRecord> listByUserId(Long userId);

    /** 查询所有借阅记录 */
    List<BorrowRecord> listAll();
}
