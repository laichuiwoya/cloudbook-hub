package com.book.book.controller;

import com.book.book.service.BookService;
import com.book.common.dto.R;
import com.book.common.entity.Book;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /** 查看动态配置（演示 @RefreshScope） */
    @GetMapping("/config")
    public R<java.util.Map<String, Object>> getConfig() {
        java.util.Map<String, Object> map = new java.util.HashMap<>();
        map.put("maxBorrowDays", bookService.getConfig().getMaxBorrowDays());
        map.put("maxBorrowCount", bookService.getConfig().getMaxBorrowCount());
        return R.ok(map);
    }

    /** 添加图书 */
    @PostMapping
    public R<Book> add(@RequestBody Book book) {
        return R.ok(bookService.add(book));
    }

    /** 删除图书 */
    @DeleteMapping("/{id}")
    public R<Void> delete(@PathVariable Long id) {
        bookService.delete(id);
        return R.ok();
    }

    /** 修改图书 */
    @PutMapping
    public R<Book> update(@RequestBody Book book) {
        return R.ok(bookService.update(book));
    }

    /** 按 ID 查询 */
    @GetMapping("/{id}")
    public R<Book> getById(@PathVariable Long id) {
        return R.ok(bookService.getById(id));
    }

    /** 模糊搜索（按书名或作者） */
    @GetMapping("/search")
    public R<List<Book>> search(@RequestParam String keyword) {
        return R.ok(bookService.search(keyword));
    }

    /** 列表查询 */
    @GetMapping
    public R<List<Book>> list() {
        return R.ok(bookService.list());
    }

    /** 扣减库存（供 borrow-service 通过 OpenFeign 调用） */
    @PutMapping("/{id}/deduct-stock")
    public R<Void> deductStock(@PathVariable Long id, @RequestParam Integer count) {
        bookService.deductStock(id, count);
        return R.ok();
    }
}
