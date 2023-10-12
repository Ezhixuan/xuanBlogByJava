package com.ezhixuan.xuan_framework.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.ezhixuan.xuan_framework.utils.UserUtils;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * @program: xuanBlog
 * @description: mybatisPlus自动填充
 * @author: Mr.Xuan
 * @create: 2023-09-28 18:25
 */
@Component
@Slf4j
public class MybatisPlusHandler implements MetaObjectHandler {
    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充");
        Date date = new Date();
        this.strictInsertFill(metaObject,"createBy",Long.class, UserUtils.getUser().getId());
        this.strictInsertFill(metaObject,"createTime",Date.class,date);
        this.strictInsertFill(metaObject,"updateBy",Long.class, UserUtils.getUser().getId());
        this.strictInsertFill(metaObject,"updateTime",Date.class,date);

    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充");
        UserUtils userUtils = new UserUtils();
        this.strictUpdateFill(metaObject,"updateBy",Long.class, UserUtils.getUser().getId());
        this.strictUpdateFill(metaObject,"updateTime",Date.class,new Date());
    }
}
