package com.ezhixuan.xuan_framework.aspect;

import com.alibaba.fastjson.JSON;
import com.ezhixuan.xuan_framework.annotation.Log;
import java.io.FileWriter;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

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

  @Value("${blog.log.path}")
  private String path;

  @Pointcut("@annotation(com.ezhixuan.xuan_framework.annotation.Log)")
  public void controllerRuntimeLogPointCut() {}
  
  @Pointcut("execution(* com.ezhixuan.xuan_framework..*.*(..))")
  public void afterThrowing(){}

  /**
   * 打印controller执行日志
   *
   * @param pjp 切点
   * @return 原方法返回值
   */
  @Around("controllerRuntimeLogPointCut()")
  @SneakyThrows
  public Object printLog(ProceedingJoinPoint pjp) {
    Object proceed;
    StringBuilder sb = new StringBuilder();
    try {
      beforeProceed(pjp, sb);
      proceed = pjp.proceed();
      afterProceed(proceed, sb);
    } finally {
      sb.append("=======================end=======================").append(System.lineSeparator());
      FileWriter fileWriter = new FileWriter(path, true);
      fileWriter.write(sb.toString());
      fileWriter.flush();
      fileWriter.close();
    }
    return proceed;
  }

  /**
   * 原代码执行前执行
   *
   * @param pjp 切点
   * @param sb 日志
   */
  private void beforeProceed(ProceedingJoinPoint pjp, StringBuilder sb) {
    RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    HttpServletRequest request =
        (HttpServletRequest)
            Objects.requireNonNull(requestAttributes)
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
    Log systemlog = getLogAnnotation(pjp);
    sb.append("======================Start======================").append(System.lineSeparator());
    sb.append("请求URL   : ").append(request.getRequestURI()).append(System.lineSeparator());
    sb.append("接口描述   : ").append(systemlog.businessName()).append(System.lineSeparator());
    sb.append("请求方式   : ").append(request.getMethod()).append(System.lineSeparator());
    sb.append("请求类名   : ")
        .append(pjp.getSignature().getDeclaringTypeName())
        .append(".")
        .append(pjp.getSignature().getName())
        .append(System.lineSeparator());
    sb.append("访问IP    : ").append(request.getRemoteHost()).append(System.lineSeparator());
    if ("上传图片".equals(systemlog.businessName())) {
      sb.append("传入参数    : ").append("图片").append(System.lineSeparator());
    }else{
      sb.append("传入参数   : ").append(JSON.toJSONString(pjp.getArgs())).append(System.lineSeparator());
    }
    
  }

  /**
   * 原代码执行后执行
   *
   * @param proceed 原方法返回值
   * @param sb 日志
   */
  private void afterProceed(Object proceed, StringBuilder sb) {
    sb.append("返回参数   : ").append(JSON.toJSONString(proceed)).append(System.lineSeparator());
  }

  /**
   * 获取Log注解
   *
   * @param pjp 切点
   * @return Log注解
   */
  private Log getLogAnnotation(ProceedingJoinPoint pjp) {
    MethodSignature signature = (MethodSignature) pjp.getSignature();
    return signature.getMethod().getAnnotation(Log.class);
  }

  @AfterThrowing(pointcut = "afterThrowing()", throwing = "ex")
  public void afterThrowing(JoinPoint joinPoint, Throwable ex) {
    log.error("出现异常{}", ex);
  }
  
}
