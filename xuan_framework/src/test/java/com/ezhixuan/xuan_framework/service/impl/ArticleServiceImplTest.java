package com.ezhixuan.xuan_framework.service.impl;

import static org.junit.jupiter.api.Assertions.*;

import com.ezhixuan.xuan_framework.domain.vo.article.HotArticleVo;
import com.ezhixuan.xuan_framework.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
@Slf4j
class ArticleServiceImplTest {

    @Resource
    private ArticleService articleService;

    @Test
    void HotArticleList(){
        List<HotArticleVo> hotArticleVos = articleService.hotArticleList();
        log.info("hotArticleList = {}", hotArticleVos);
    }
}
