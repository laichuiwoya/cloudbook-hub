package com.book.borrow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.book.borrow.feign.BookFeignClient;
import com.book.borrow.repository.BorrowRecordMapper;
import com.book.borrow.service.BorrowService;
import com.book.common.dto.R;
import com.book.common.entity.Book;
import com.book.common.entity.BorrowRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class BorrowServiceImpl implements BorrowService {

    private final BorrowRecordMapper borrowRecordMapper;
    private final BookFeignClient bookFeignClient;

    public BorrowServiceImpl(BorrowRecordMapper borrowRecordMapper, BookFeignClient bookFeignClient) {
        this.borrowRecordMapper = borrowRecordMapper;
        this.bookFeignClient = bookFeignClient;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BorrowRecord borrow(Long userId, Long bookId) {
        // 1. 通过 OpenFeign（LoadBalancer 负载均衡）查询图书
        R<Book> result = bookFeignClient.getById(bookId);
        if (result.getCode() != 200 || result.getData() == null) {
            throw new RuntimeException(result.getMessage() != null ? result.getMessage() : "图书不存在或服务不可用");
        }
        Book book = result.getData();
        if (book.getStock() <= 0) {
            throw new RuntimeException("图书库存不足");
        }

        // 2. 扣减库存
        R<Void> deductResult = bookFeignClient.deductStock(bookId, 1);
        if (deductResult.getCode() != 200) {
            throw new RuntimeException(deductResult.getMessage() != null ? deductResult.getMessage() : "扣减库存失败");
        }

        // 3. 创建借阅记录
        BorrowRecord record = new BorrowRecord();
        record.setUserId(userId);
        record.setBookId(bookId);
        record.setBorrowDate(LocalDateTime.now());
        record.setDueDate(LocalDateTime.now().plusDays(30));
        record.setStatus(0); // 借阅中
        borrowRecordMapper.insert(record);

        log.info("用户 {} 借阅图书 {} 成功，借阅记录 ID: {}", userId, bookId, record.getId());
        return record;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void returnBook(Long borrowId) {
        BorrowRecord record = borrowRecordMapper.selectById(borrowId);
        if (record == null) {
            throw new RuntimeException("借阅记录不存在");
        }
        if (record.getStatus() != 0) {
            throw new RuntimeException("该记录已归还或已逾期处理");
        }
        record.setReturnDate(LocalDateTime.now());
        record.setStatus(1); // 已归还
        borrowRecordMapper.updateById(record);

        log.info("借阅记录 {} 归还成功", borrowId);
    }

    @Override
    public List<BorrowRecord> listByUserId(Long userId) {
        LambdaQueryWrapper<BorrowRecord> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(BorrowRecord::getUserId, userId)
               .orderByDesc(BorrowRecord::getCreateTime);
        return borrowRecordMapper.selectList(wrapper);
    }

    @Override
    public List<BorrowRecord> listAll() {
        return borrowRecordMapper.selectList(null);
    }
}
