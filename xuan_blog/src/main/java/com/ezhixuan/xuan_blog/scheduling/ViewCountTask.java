package com.ezhixuan.xuan_blog.scheduling;

import com.ezhixuan.xuan_framework.constant.RedisKeyConstant;
import com.ezhixuan.xuan_framework.domain.entity.Article;
import com.ezhixuan.xuan_framework.service.ArticleService;
import com.ezhixuan.xuan_framework.utils.RedisUtil;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @program: xuanBlog
 * @description: 浏览量定时推送任务
 * @author: Mr.Xuan
 * @create: 2023-09-30 01:36
 */
@Component
@Slf4j
public class ViewCountTask {

    @Resource
    private ArticleService articleService;

    @Resource private RedisUtil redisUtil;

    /**
     * 每10分钟推送一次
     */
    @Scheduled(cron = "0 0/10 * * * ?")
    public void viewCountTask(){
        //获取redis中的浏览量
        log.info("===================================定时推送浏览量=start=====================================");
        Map<String, Long> viewCountMap = redisUtil.getHashMap(RedisKeyConstant.ARTICLE_VIEW_COUNT_KEY, Long.class);
        List<Article> articleList = viewCountMap.entrySet().stream().map(entry -> {
            Article article = new Article();
            article.setId(Long.valueOf(entry.getKey()));
            article.setViewCount(entry.getValue());
            log.info("文章:{}的浏览量为{}", entry.getKey(), entry.getValue());
            return article;
        }).collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articleList);
        log.info("===================================定时推送浏览量=end=====================================");
    }

}
