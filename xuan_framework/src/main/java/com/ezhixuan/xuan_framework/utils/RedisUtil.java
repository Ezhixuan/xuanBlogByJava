package com.ezhixuan.xuan_framework.utils;

import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import java.util.*;
import javax.annotation.Resource;
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
   * @param key redis键
   * @param value redis值
   */
  public <T> boolean setValue(@NotNull String key, T value) {
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
   * 通过map设置缓存
   *
   * @param map map对象<redis键,value>
   * @return boolean
   * @param <T> 泛型
   */
  public <T> boolean setValue(@NotNull Map<String, T> map) {
    boolean result = false;
    try {
      ((ValueOperations<Serializable, T>) redisTemplate.opsForValue()).multiSet(map);
      result = true;
    } catch (Exception e) {
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 获得单个对象
   *
   * @param key redis键
   * @param clazz 目标对象类型
   * @return T
   */
  public <T> T getValue(@NotNull String key, Class<T> clazz) {
    Object o = redisTemplate.opsForValue().get(key);
    return cast(o, clazz);
  }

  /**
   * 获得list
   *
   * @param key redis键
   * @param clazz 目标对象类型
   * @return List<T>
   * @param <T> 泛型
   */
  public <T> List<T> getValueList(@NotNull String key, Class<T> clazz) {
    Object o = redisTemplate.opsForValue().get(key);
    if (ObjectUtils.isEmpty(o)) {
      return null;
    }
    return castList(o, clazz);
  }

  /**
   * 模糊查询
   *
   * @param pattern 模糊查询的key
   * @return Map
   * @param <T> 泛型
   */
  public <T> Map<String, T> getValueMap(@NotNull String pattern) {
    List<String> scan = scan(pattern);
    ValueOperations<String, T> valueOperations = redisTemplate.opsForValue();
    List<T> list = valueOperations.multiGet(scan);
    Map<String, T> map = new HashMap<>();
    for (int i = 0; i < scan.size(); i++) {
      map.put(scan.get(i), cast(list.get(i), (Class<T>) list.get(i).getClass()));
    }
    return map;
  }

  /**
   * 自增
   *
   * @param key redis键
   * @return Long
   */
  public Long incrValue(@NotNull String key) {
    return redisTemplate.opsForValue().increment(key);
  }

  /**
   * 增加指定Long数
   *
   * @param key redis键
   * @param number 自增数
   * @return Long
   */
  public Long incrValue(@NotNull String key, Long number) {
    return redisTemplate.opsForValue().increment(key, number);
  }

  /**
   * 增加指定Double数
   *
   * @param key redis键
   * @param number 自增数
   * @return Double
   */
  public Double incrValue(@NotNull String key, Double number) {
    return redisTemplate.opsForValue().increment(key, number);
  }

  /**
   * 提供redisTemplate在提供方法不足以使用
   *
   * @return RedisTemplate
   */
  public RedisTemplate getRedisTemplate() {
    return redisTemplate;
  }

  /**
   * 缓存List数据
   *
   * @param key 缓存的键值
   * @param dataList 待缓存的List数据
   * @return 缓存的对象
   */
  public <T> long setListR(@NotNull String key, List<T> dataList) {
    Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
    return count == null ? 0 : count;
  }
  /**
   * 缓存List数据
   *
   * @param key 缓存的键值
   * @param dataList 待缓存的List数据
   * @return 缓存的对象
   */
  public <T> long setListL(@NotNull String key, List<T> dataList) {
    Long count = redisTemplate.opsForList().leftPushAll(key, dataList);
    return count == null ? 0 : count;
  }

  /**
   * 获得缓存的list对象
   *
   * @param key 缓存的键值
   * @return 缓存键值对应的数据
   */
  public <T> List<T> getList(@NotNull String key) {
    List range = redisTemplate.opsForList().range(key, 0, -1);
    return castList(range, (Class<T>) range.get(0).getClass());
  }

  /**
   * 缓存Set
   *
   * @param key 缓存键值
   * @param dataSet 缓存的数据
   * @return 缓存数据的对象
   */
  public <T> BoundSetOperations<String, T> setSet(@NotNull String key, Set<T> dataSet) {
    BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
    Iterator<T> it = dataSet.iterator();
    while (it.hasNext()) {
      setOperation.add(it.next());
    }
    return setOperation;
  }

  /**
   * 获得缓存的set
   *
   * @param key 缓存键值
   * @return Set<T>
   */
  public <T> Set<T> getSet(@NotNull String key, Class<T> clazz) {
    Set members = redisTemplate.opsForSet().members(key);
    members.forEach(
        member -> {
          cast(member, clazz);
        });
    return members;
  }

  /**
   * 往Hash中存入数据
   *
   * @param key Redis键
   * @param hKey Hash键
   * @param value 值
   */
  public <T> void setHash(String key, String hKey, T value) {
    redisTemplate.opsForHash().put(key, hKey, value);
  }

  /**
   * 缓存Map
   *
   * @param key 缓存键值
   * @param dataMap 缓存的数据
   */
  public <T> void setHash(@NotNull String key, Map<String, T> dataMap) {
    if (dataMap != null) {
      redisTemplate.opsForHash().putAll(key, dataMap);
    }
  }

  /**
   * 获取Hash中的数据
   *
   * @param key Redis键
   * @param hKey Hash键
   * @return Hash中的对象
   */
  public <T> T getHash(@NotNull String key, String hKey, Class<T> clazz) {
    HashOperations opsForHash = redisTemplate.opsForHash();
    Object o = opsForHash.get(key, hKey);
    return cast(o, clazz);
  }

  /**
   * 获得缓存的Map
   *
   * @param key Redis键
   * @return Map对象
   */
  public <T> Map<String, T> getHashMap(@NotNull String key, Class<T> clazz) {
    Map<String, Object> entries = redisTemplate.opsForHash().entries(key);
    // 对entries遍历，将其转换为T类型
    HashMap<String, T> stringTHashMap = new HashMap<>();
    for (Map.Entry<String, Object> entry : entries.entrySet()) {
      T cast = cast(entry.getValue(), clazz);
      stringTHashMap.put(entry.getKey(),cast);
    }
    return stringTHashMap;
  }

  /**
   * 删除Hash中的数据
   *
   * @param key Redis键
   * @param hkey Hash键
   */
  public void delCacheMapValue(@NotNull String key, String hkey) {
    HashOperations hashOperations = redisTemplate.opsForHash();
    hashOperations.delete(key, hkey);
  }

  /**
   * hash自增
   *
   * @param key redis键
   * @param string hash键
   */
  public void incrHash(String key, String string) {
    redisTemplate.opsForHash().increment(key, string, 1L);
  }

  /**
   * hash自减
   *
   * @param key redis键
   * @param string hash键
   */
  public void decrHash(String key, String string) {
    incrHash(key, string, -1L);
  }

  /**
   * hash指定Long自增
   *
   * @param key redis键
   * @param string hash键
   * @param number 自增数
   */
  public void incrHash(String key, String string, Long number) {
    redisTemplate.opsForHash().increment(key, string, number);
  }

  /**
   * hash指定Double自增
   *
   * @param key redis键
   * @param string hash键
   * @param number 自增数
   */
  public void incrHash(String key, String string, Double number) {
    redisTemplate.opsForHash().increment(key, string, number);
  }

  /**
   * 获取多个Hash中的数据
   *
   * @param key Redis键
   * @param hKeys Hash键集合
   * @return Hash对象集合
   */
  public <T> List<T> getMultiCacheMapValue(@NotNull String key, Collection<Object> hKeys) {
    List list = redisTemplate.opsForHash().multiGet(key, hKeys);
    list.forEach(
        o -> {
          cast(o, (Class<T>) o.getClass());
        });
    return list;
  }

  /**
   * 清除缓存
   *
   * @param key redis键
   * @return Boolean
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
   * @param pattern 模糊查询的key
   * @return Long
   */
  public Long cleanCaches(@NotNull String pattern) {
    List<String> keys = scan(pattern);
    return redisTemplate.delete(keys);
  }

  /**
   * 通过scan模糊查询keys
   *
   * @param pattern 模糊查询的key
   * @return List<String>
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
   * @param obj 源对象
   * @param clazz 目标对象类型
   * @return 目标对象
   * @param <T> 泛型
   */
  public static <T> T cast(Object obj, Class<T> clazz) {
    // redis中存入Long类型的数据，取出来是Integer类型的，所以需要转换
    if (clazz == Long.class && obj instanceof Integer) {
      long longValue = ((Integer) obj).longValue();
      return clazz.cast(longValue);
    }
    return clazz.cast(obj);
  }

  /**
   * 将Object对象转换为List<T>对象
   *
   * @param obj 源对象
   * @param clazz 目标对象类型
   * @return 目标对象
   * @param <T> 泛型
   */
  public static <T> List<T> castList(Object obj, Class<T> clazz) {
    List<T> result = new ArrayList<>();
    if (obj instanceof List<?>) {
      for (Object o : (List<?>) obj) {
        if (clazz == Long.class && o instanceof Integer) {
          long longValue = ((Integer) o).longValue();
        }
        result.add(clazz.cast(o));
      }
      return result;
    }
    return new ArrayList<>();
  }
}
