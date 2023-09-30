package com.ezhixuan.xuan_blog.lineRunner;

import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.dao.ArticleDao;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @program: xuanBlog
 * @description: 启动时注入浏览量
 * @author: Mr.Xuan
 * @create: 2023-09-30 01:42
 */
@Component
@Slf4j
public class ViewCountRunner implements CommandLineRunner {

  @Resource private ArticleDao articleDao;

  @Resource private RedisUtil redisUtil;
  
  /**
   * 项目启动预热数据
   * @param args incoming main method arguments
   * @throws Exception
   */
  @Override
  public void run(String... args) throws Exception {
    // 1. 预热浏览量
    log.info("===================================预热浏览量========================================");
    Map<String, Long> map = articleDao.selectList(null).stream()
            .collect(
                    Collectors.toMap(
                            article -> article.getId().toString(),
                            article -> article.getViewCount()));
    redisUtil.setHash(RedisKeyConstant.ARTICLE_VIEW_COUNT_KEY, map);
    log.info("===================================预热浏览量========================================");
  }
}
