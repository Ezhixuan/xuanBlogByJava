package com.ezhixuan.xuan_framework.constant;
/**
 * @program: xuanBlog
 * @description: redisKey常量 以By结尾则表示后面需要跟上参数，以key结尾表示直接使用
 * @author: Mr.Xuan
 * @create: 2023-09-27 19:33
 */
public class RedisKeyConstant {

  /** 登录用户的key前缀,后面需要自行增加id组成key */
  public static final String BLOG_LOGIN_USER_BY_ID = "blog:login:user:";
  /** 热点文章缓存的key */
  public static final String CACHE_HOT_ARTICLE = "cache:hot:article";
  /** 文章缓存的key前缀，后面需要自行添加id组成key */
  public static final String CACHE_ARTICLE_BY_ID = "cache:article:";
  /** 文章浏览量的key */
  public static final String ARTICLE_VIEW_COUNT_KEY = "article:view";
}
