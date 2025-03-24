package com.zh.zhpicturebackend.aop;

import com.zh.zhpicturebackend.annotation.AutoCheck;
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
 * @Description TODO
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
    @Around("@annotation(autoCheck)")
    public Object doIntercept(ProceedingJoinPoint joinPoint,AutoCheck autoCheck)  {
        String mustRole=autoCheck.mustRole();
        //获取当前登录信息-request
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
        //获取当前用户登录
        User loginUser = userService.getLoginUser(request);
        UserRoleEnum mustRoleEnum = UserRoleEnum.getEnumByValue(mustRole);
        //todo :如果不需要权限,放行
    }
}
