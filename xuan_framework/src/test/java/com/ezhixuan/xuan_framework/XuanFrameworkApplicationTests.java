package com.ezhixuan.xuan_framework;

import com.ezhixuan.xuan_framework.utils.TencentCosUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
class XuanFrameworkApplicationTests {

    @Resource
    private TencentCosUtil tencentCosUtil;

    @Test
    void contextLoads() {

    }

}
