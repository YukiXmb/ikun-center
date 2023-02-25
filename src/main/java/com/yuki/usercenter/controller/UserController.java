package com.yuki.usercenter.controller;

import com.yuki.usercenter.common.BaseResponse;
import com.yuki.usercenter.common.ErrorCode;
import com.yuki.usercenter.exception.BusinessException;
import com.yuki.usercenter.model.domain.User;
import com.yuki.usercenter.model.domain.request.UserLoginRequest;
import com.yuki.usercenter.model.domain.request.UserRegisterRequest;
import com.yuki.usercenter.service.UserService;
import com.yuki.usercenter.utils.ResponseUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.yuki.usercenter.constant.UserConstant.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long id = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResponseUtils.success(id);

    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户登录请求态为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"账号或密码为空");
        }
        User userLogin = userService.userLogin(userAccount, userPassword, request);
        return ResponseUtils.success(userLogin);

    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求为空");
        }
        Integer userLogout = userService.userLogout(request);
        return ResponseUtils.success(userLogout);

    }

    @GetMapping("/select")
    public BaseResponse<List<User>> selectByName(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH,"不是管理员");
    }
        List<User> userList = userService.selectByName(username);
        List<User> collect = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResponseUtils.success(collect);
    }

    @PostMapping("delete")
    public BaseResponse<Boolean> deleteUser(long id, HttpServletRequest request) {
        if (id <= 0 || !isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户不存在或没有权限");
        }

        boolean b = userService.deleteUser(id);
        return ResponseUtils.success(b);
    }

    /**
     * 获取当前用户态
     *
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN,"用户未登录");
        }
        Long id = currentUser.getId();
        User user = userService.getById(id);
        User safetyUser = userService.getSafetyUser(user);
        return ResponseUtils.success(safetyUser);

    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATUS);
        User user = (User) userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"用户不存在或不为管理员");
        }
        return true;
    }
}
