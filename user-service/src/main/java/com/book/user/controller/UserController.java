package com.book.user.controller;

import com.book.common.dto.R;
import com.book.common.entity.User;
import com.book.user.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** 注册 */
    @PostMapping("/register")
    public R<User> register(@RequestBody User user) {
        return R.ok(userService.register(user));
    }

    /** 登录，返回 JWT token */
    @PostMapping("/login")
    public R<Map<String, String>> login(@RequestParam String username,
                                        @RequestParam String password) {
        String token = userService.login(username, password);
        Map<String, String> data = new HashMap<>();
        data.put("token", token);
        return R.ok(data);
    }

    /** 查询用户信息 */
    @GetMapping("/{id}")
    public R<User> getById(@PathVariable Long id) {
        return R.ok(userService.getById(id));
    }
}
