package com.yuki.usercenter.service;

import com.yuki.usercenter.common.BaseResponse;
import com.yuki.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author Yuki
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2023-01-02 20:50:32
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return id 用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);

    /**
     * 用户登录
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request 前端发送的请求
     * @return user 脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 查找用户
     *
     * @param username 用户名
     * @return
     */
    List<User> selectByName(String username);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    boolean deleteUser(long id);

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    Integer userLogout(HttpServletRequest request);
}
