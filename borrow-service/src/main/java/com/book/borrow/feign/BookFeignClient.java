package com.book.borrow.feign;

import com.book.common.dto.R;
import com.book.common.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 通过 OpenFeign + Spring Cloud LoadBalancer 调用 book-service。
 * name = "book-service" 即 Nacos 注册中心的服务名，自动负载均衡。
 */
@FeignClient(
        name = "book-service",
        path = "/api/book",
        fallbackFactory = BookFeignFallbackFactory.class
)
public interface BookFeignClient {

    @GetMapping("/{id}")
    R<Book> getById(@PathVariable("id") Long id);

    @PutMapping("/{id}/deduct-stock")
    R<Void> deductStock(@PathVariable("id") Long id, @RequestParam("count") Integer count);
}
