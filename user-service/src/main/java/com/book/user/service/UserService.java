package com.book.user.service;

import com.book.common.entity.User;

public interface UserService {

    /** 注册 */
    User register(User user);

    /** 登录，返回 JWT token */
    String login(String username, String password);

    /** 根据 ID 查询 */
    User getById(Long id);
}
