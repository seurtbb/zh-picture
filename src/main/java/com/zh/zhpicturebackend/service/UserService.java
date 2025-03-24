package com.zh.zhpicturebackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zh.zhpicturebackend.model.dto.user.UserQueryRequest;
import com.zh.zhpicturebackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zh.zhpicturebackend.model.vo.LoginUserVO;
import com.zh.zhpicturebackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author zhouzhou
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-03-18 10:42:48
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
    /**
     * 获取加密后的密码
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取当前登录用户的信息,不需要返回给前端
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);
    /**
     *获得脱敏后的用户信息
     * @param user 用户信息
     * @return
     */
    LoginUserVO getLoginUserVo(User user);
    /**
     * 获取用户脱敏信息
     * @param user 脱敏前的信息
     * @return 脱敏后的信息
     */
    UserVO getUserVO(User user);

    /**
     * 批量获取用户脱敏信息
     * @param userList 脱敏前的信息
     * @return 脱敏后的 List 列表
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 获取查询条件
     *
     * @param userQueryRequest 查询条件
     * @return 查询条件
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);


    /**
     * 用户登录退出
     * @param request
     * @return
     */
    Boolean userLoginout(HttpServletRequest request);



}

