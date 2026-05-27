package com.book.book.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 演示 @RefreshScope + @ConfigurationProperties 动态刷新。
 * 在 Nacos 中修改 book-service-dev.yaml 的对应配置后，
 * 无需重启服务即可生效。
 */
@Data
@Component
@RefreshScope
@ConfigurationProperties(prefix = "book.config")
public class BookServiceConfig {

    /** 借阅最大天数，默认 30 */
    private Integer maxBorrowDays = 30;

    /** 每人最多借阅本数 */
    private Integer maxBorrowCount = 5;
}
