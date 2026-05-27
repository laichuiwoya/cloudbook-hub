package com.book.book.service.impl;

import com.book.book.config.BookServiceConfig;
import com.book.book.repository.BookMapper;
import com.book.book.service.BookService;
import com.book.common.entity.Book;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookMapper bookMapper;
    private final BookServiceConfig bookServiceConfig;

    public BookServiceImpl(BookMapper bookMapper, BookServiceConfig bookServiceConfig) {
        this.bookMapper = bookMapper;
        this.bookServiceConfig = bookServiceConfig;
    }

    @Override
    public BookServiceConfig getConfig() {
        return bookServiceConfig;
    }

    @Override
    public List<Book> list() {
        return bookMapper.selectList(null);
    }

    @Override
    public Book getById(Long id) {
        return bookMapper.selectById(id);
    }

    @Override
    public Book add(Book book) {
        bookMapper.insert(book);
        return book;
    }

    @Override
    public Book update(Book book) {
        bookMapper.updateById(book);
        return book;
    }

    @Override
    public void delete(Long id) {
        bookMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deductStock(Long id, Integer count) {
        Book book = bookMapper.selectById(id);
        if (book == null) {
            throw new RuntimeException("图书不存在");
        }
        if (book.getStock() < count) {
            throw new RuntimeException("库存不足");
        }
        book.setStock(book.getStock() - count);
        bookMapper.updateById(book);
    }

    @Override
    public List<Book> search(String keyword) {
        return bookMapper.search(keyword);
    }
}
