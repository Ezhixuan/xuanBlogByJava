package com.ezhixuan.xuan_framework.aspect;

import com.alibaba.fastjson.JSON;
import com.ezhixuan.xuan_framework.annotation.Log;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @program: xuanBlog
 * @description: 日志AOP
 * @author: Mr.Xuan
 * @create: 2023-09-29 14:54
 */
@Aspect
@Component
@Slf4j
public class logAspect {

  @Pointcut("@annotation(com.ezhixuan.xuan_framework.annotation.Log)")
  public void pt() {}

  @Around("pt()")
  public Object printLog(ProceedingJoinPoint pjp) throws Throwable {
    Object proceed;
    try {
      beforeProceed(pjp);
      proceed = pjp.proceed();
      afterProceed(proceed);
    } finally {
      log.info("=======================end=======================" + System.lineSeparator());
    }

    return proceed;
  }

  private void afterProceed(Object proceed) {
    // 打印出参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
    log.info("返回参数   : {}", JSON.toJSONString(proceed));
  }

  private void beforeProceed(ProceedingJoinPoint pjp) {
    // ServletRequestAttributes是RequestAttributes是spring接口的实现类
    ServletRequestAttributes requestAttributes =
        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

    // 下面那行就可以拿到请求的报文了，其中有我们需要的url、请求方式、ip。这里拿到的request会在下面的log中大量使用
    HttpServletRequest request = requestAttributes.getRequest();

    // 获取被增强方法的注解对象，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解
    // getSystemlog是我们下面写的方法
    Log systemlog = getLogAnnotation(pjp);

    log.info(
        "======================Start======================"); // 下面的几个log输出，第一个参数{}表示占位符，具体的值是第二个参数的变量
    // 打印请求 URL
    log.info("请求URL   : {}", request.getRequestURL());
    // 打印描述信息，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解的描述信息
    log.info("接口描述   : {}", systemlog.businessName());
    // 打印 Http method
    log.info("请求方式   : {}", request.getMethod());
    // 打印调用 controller 的全路径(全类名)、方法名
    log.info(
        "请求类名   : {}.{}",
        pjp.getSignature().getDeclaringTypeName(),
        ((MethodSignature) pjp.getSignature()).getName());
    // 打印请求的 IP
    log.info("访问IP    : {}", request.getRemoteHost());
    // 打印请求入参。JSON.toJSONString十FastJson提供的工具方法，能把数组转成JSON
    log.info("传入参数   : {}", JSON.toJSONString(pjp.getArgs()));
  }

  private Log getLogAnnotation(ProceedingJoinPoint pjp) {
    // Signature是spring提供的接口，MethodSignature是Signature的子接口
    MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
    // mySystemlog是我们写的自定义注解的接口
    // 下面那行就能获取被增强方法的注解对象，例如获取UserController类的updateUserInfo方法上一行的@mySystemlog注解
    Log systemlog = methodSignature.getMethod().getAnnotation(Log.class);
    return systemlog;
  }
}
