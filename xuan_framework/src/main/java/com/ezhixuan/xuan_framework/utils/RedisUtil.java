package com.ezhixuan.xuan_framework.utils;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @description: redis操作工具类,提供一些常规的用法
 * @author: Mr.Xuan
 */
@Component
public class RedisUtil {

  @Resource private RedisTemplate redisTemplate;

  /**
   * 设置缓存
   *
   * @param key
   * @param value
   */
  public <T> boolean set(@NotNull String key, T value) {
    boolean result = false;
    try {
      ((ValueOperations<Serializable, T>) redisTemplate.opsForValue()).set(key, value);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获得单个对象
   *
   * @param key
   * @param clazz
   * @return
   */
  public <T> T get(@NotNull String key, Class<T> clazz) {
    Object o = redisTemplate.opsForValue().get(key);
    // redis中存入Long类型的数据，取出来是Integer类型的，所以需要转换
    if (clazz == Long.class && o instanceof Integer) {
      long longValue = ((Integer) o).longValue();
      return cast(longValue, clazz);
    }
    return cast(o, clazz);
  }

  /**
   * 获得list
   *
   * @param key
   * @param clazz
   * @return
   * @param <T>
   */
  public <T> List<T> getList(@NotNull String key, Class<T> clazz) {
    Object o = redisTemplate.opsForValue().get(key);
    if (ObjectUtils.isEmpty(o)) {
      return null;
    }
    return castList(o, clazz);
  }

  /**
   * 自增
   *
   * @param key
   * @return
   */
  public Long incr(@NotNull String key) {
    return redisTemplate.opsForValue().increment(key);
  }

  /**
   * 增加指定Long数
   *
   * @param key
   * @param number
   * @return
   */
  public Long incr(@NotNull String key, Long number) {
    return redisTemplate.opsForValue().increment(key, number);
  }

  /**
   * 增加指定Double数
   *
   * @param key
   * @param number
   * @return
   */
  public Double incr(@NotNull String key, Double number) {
    return redisTemplate.opsForValue().increment(key, number);
  }

  /**
   * 提供redisTemplate在提供方法不足以使用
   *
   * @return
   */
  public RedisTemplate<String, String> getRedisTemplate() {
    return redisTemplate;
  }

  /**
   * 清除缓存
   *
   * @param key
   * @return
   */
  public Boolean cleanCache(String... key) {
    if (key != null && key.length > 0) {
      if (key.length == 1) {
        return redisTemplate.delete(key[0]);
      } else {
        List<String> keys = new ArrayList<>();
        for (String s : key) {
          keys.add(s);
        }
        return redisTemplate.delete(keys) > 0;
      }
    }
    return false;
  }

  /**
   * 通过scan模糊查询keys并删除
   *
   * @param pattern
   * @return
   */
  public Long cleanCaches(@NotNull String pattern) {
    List<String> keys = scan(pattern);
    return redisTemplate.delete(keys);
  }

  /**
   * 通过scan模糊查询keys
   *
   * @param pattern
   * @return
   */
  public List<String> scan(@NotNull String pattern) {
    List<String> result = new ArrayList<>();
    Cursor<byte[]> cursor = null;
    try {
      cursor =
          (Cursor)
              redisTemplate.execute(
                  (RedisCallback)
                      redisConnection ->
                          redisConnection.scan(
                              ScanOptions.scanOptions().match(pattern + "*").count(1000).build()));
      while (cursor.hasNext()) {
        result.add(new String(cursor.next()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return result;
  }

  /**
   * 将Object对象转换为T对象
   *
   * @param obj
   * @param clazz
   * @return
   * @param <T>
   */
  public static <T> T cast(Object obj, Class<T> clazz) {
    return clazz.cast(obj);
  }

  /**
   * 将Object对象转换为List<T>对象
   *
   * @param obj
   * @param clazz
   * @return
   * @param <T>
   */
  public static <T> List<T> castList(Object obj, Class<T> clazz) {
    List<T> result = new ArrayList<>();
    if (obj instanceof List<?>) {
      for (Object o : (List<?>) obj) {
        result.add(clazz.cast(o));
      }
      return result;
    }
    return new ArrayList<>();
  }
}
