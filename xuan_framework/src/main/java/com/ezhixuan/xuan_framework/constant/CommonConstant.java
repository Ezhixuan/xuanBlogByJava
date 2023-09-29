package com.ezhixuan.xuan_framework.constant;
/**
 * @program: xuanBlog
 * @description: 状态
 * @author: Mr.Xuan
 * @create: 2023-09-23 20:16
 */
public class CommonConstant {
  /** 文章已发布 */
  public static final Integer ARTICLE_PUBLISHED_DRAFT = 0;
  /** 文章草稿 */
  public static final Integer ARTICLE_STATUS_DRAFT = 1;
  /** 友链审核通过 */
  public static final Integer LINK_STATUS_PASSED = 0;
  /** 友链审核未通过 */
  public static final Integer LINK_STATUS_UNPASS = 1;
  /** 友链未审核 */
  public static final Integer LINK_STATUS_UNCHECKED = 2;
  /** 文章置顶 */
  public static final String ARTICLE_IS_TOP = "1";
  /** 文章未置顶 */
  public static final String ARTICLE_UN_TOP = "0";
  /** 文章评论根节点 */
  public static final Long ROOT_COMMENT = -1L;
  /** 评论类型（0代表文章评论，1代表友链评论） */
  public static final Integer ARTICLE_COMMENT = 0;
  /** 评论类型（0代表文章评论，1代表友链评论） */
  public static final Integer LINK_COMMENT = 1;
}
