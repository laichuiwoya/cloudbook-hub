package com.book.borrow.feign;

import com.book.common.dto.R;
import com.book.common.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * BookFeignClient 降级工厂。
 * 当 book-service 不可用时，返回降级响应，避免借阅服务雪崩。
 */
@Slf4j
@Component
public class BookFeignFallbackFactory implements FallbackFactory<BookFeignClient> {

    @Override
    public BookFeignClient create(Throwable cause) {
        log.error("BookFeignClient 远程调用失败，触发降级: {}", cause.getMessage(), cause);

        return new BookFeignClient() {
            @Override
            public R<Book> getById(Long id) {
                return R.fail(503, "图书服务暂时不可用，请稍后重试");
            }

            @Override
            public R<Void> deductStock(Long id, Integer count) {
                return R.fail(503, "图书服务暂时不可用，请稍后重试");
            }
        };
    }
}
