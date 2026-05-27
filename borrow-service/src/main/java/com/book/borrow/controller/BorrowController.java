package com.book.borrow.controller;

import com.book.borrow.service.BorrowService;
import com.book.common.dto.R;
import com.book.common.entity.BorrowRecord;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    /** 借阅图书（传入用户 ID 和图书 ID） */
    @PostMapping
    public R<BorrowRecord> borrow(@RequestParam Long userId, @RequestParam Long bookId) {
        return R.ok(borrowService.borrow(userId, bookId));
    }

    /** 归还图书 */
    @PutMapping("/{id}/return")
    public R<Void> returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
        return R.ok();
    }

    /** 查询用户借阅记录 */
    @GetMapping("/user/{userId}")
    public R<List<BorrowRecord>> listByUserId(@PathVariable Long userId) {
        return R.ok(borrowService.listByUserId(userId));
    }

    /** 查询所有借阅记录 */
    @GetMapping
    public R<List<BorrowRecord>> listAll() {
        return R.ok(borrowService.listAll());
    }
}
