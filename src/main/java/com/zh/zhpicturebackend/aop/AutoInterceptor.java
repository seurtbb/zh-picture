package com.zh.zhpicturebackend.aop;

import com.zh.zhpicturebackend.annotation.AutoCheck;
import com.zh.zhpicturebackend.exception.BusinessException;
import com.zh.zhpicturebackend.exception.ErrorCode;
import com.zh.zhpicturebackend.model.entity.User;
import com.zh.zhpicturebackend.model.enums.UserRoleEnum;
import com.zh.zhpicturebackend.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author zzh
 * @Date 2025/3/24 13:36
 * @Version 1.0
 * @Description
 */
@Aspect
@Component
public class AutoInterceptor {
    @Resource
    private UserService userService;

    /***
     *
     * @param joinPoint  切入点
     * @param autoCheck 权限校验注解
     */
    @Around("@annotation(autoCheck)")//捕捉注解
    public Object doIntercept(ProceedingJoinPoint joinPoint,AutoCheck autoCheck) throws Throwable {
        String mustRole=autoCheck.mustRole();
        //获取当前登录信息-request
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //获取当前用户登录
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        //如果没有注解,放行
        if (mustRoleEnum == null) {
            return joinPoint.proceed();
        }
        //以下:必须有权限,通过
        //获取当前用户具有的权限
            //逻辑:
                //1.loginUser.getUserRole()->获取当前用户的权限属性
                //2.根据 value 获取枚举
        UserRoleEnum enumByValue = UserRoleEnum.getEnumByValue(loginUser.getUserRole());
        //没有权限,拒绝
        if (enumByValue == null) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //要求必须有管理员的权限,没有管理员的权限,报错->前:校验管理员权限,后:用户权限必须是管理员
        if (UserRoleEnum.ADMIN.equals(mustRoleEnum)&&!UserRoleEnum.ADMIN.equals(enumByValue)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        //通过校验,放行
        return joinPoint.proceed();
    }
}
