package com.book.book.service;

import com.book.book.config.BookServiceConfig;
import com.book.common.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> list();

    Book getById(Long id);

    Book add(Book book);

    Book update(Book book);

    void delete(Long id);

    void deductStock(Long id, Integer count);

    List<Book> search(String keyword);

    BookServiceConfig getConfig();
}
