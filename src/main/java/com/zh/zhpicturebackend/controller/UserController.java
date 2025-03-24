package com.zh.zhpicturebackend.controller;

import com.zh.zhpicturebackend.annotation.AutoCheck;
import com.zh.zhpicturebackend.common.BaseResponse;
import com.zh.zhpicturebackend.common.ResultUtils;
import com.zh.zhpicturebackend.constant.UserConstant;
import com.zh.zhpicturebackend.exception.ErrorCode;
import com.zh.zhpicturebackend.exception.ThrowUtils;
import com.zh.zhpicturebackend.model.dto.UserLoginRequest;
import com.zh.zhpicturebackend.model.dto.UserRegisterRequest;
import com.zh.zhpicturebackend.model.entity.User;
import com.zh.zhpicturebackend.model.enums.UserRoleEnum;
import com.zh.zhpicturebackend.model.vo.LoginUserVO;
import com.zh.zhpicturebackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
    /**
     * 用户登录
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userlogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取当前登录用户
     * getLoginUser:getLoginUser是获取当前用户的信息,返回给前端需要封装成VO
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVo(loginUser));
    }

    /**
     * 用户登录退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLoginout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        Boolean result = userService.userLoginout(request);
        return ResultUtils.success(result);
    }
}
