package com.yuki.usercenter.service;

import com.yuki.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setEmail("1360110615@qq.com");
        user.setPhone("18214861378");
        user.setUserPassword("ljw32949184");
        user.setGender(0);
        user.setAvatarUrl("https://www.code-nav.cn/");
        user.setUserAccount("1360110615");
        user.setUsername("yuki");
        boolean result = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(result);

    }

    @Test
    void userRegister() {
        String userAccount = "yuki";
        String userPassword = "123456789";
        String checkPassword = "123456789";
        String planetCode = "6666";

        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertTrue(result > 0);
    }
}