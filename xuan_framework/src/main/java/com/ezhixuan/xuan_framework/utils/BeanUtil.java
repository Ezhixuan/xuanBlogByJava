package com.ezhixuan.xuan_framework.utils;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.poi.ss.formula.functions.T;

/**
 * @program: xuanBlog
 * @description: BeanUtil
 * @author: Mr.Xuan
 * @create: 2023-09-24 17:05
 */
public class BeanUtil {
  private BeanUtil() {}

  /**
   * 对象拷贝
   *
   * @param source 源对象
   * @param clazz 目标对象
   * @return T
   * @param <T> 泛型
   */
  public static <T> T copyBean(Object source, Class<T> clazz) {
    T t = null;
    try {
      t = clazz.newInstance();
      org.springframework.beans.BeanUtils.copyProperties(source, t);
    } catch (InstantiationException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return t;
  }

  public static <T> List<T> copyBeanList(List<?> objectList, Class<T> clazz) {
    return objectList.stream().map(object -> copyBean(object, clazz)).collect(Collectors.toList());
  }
}
