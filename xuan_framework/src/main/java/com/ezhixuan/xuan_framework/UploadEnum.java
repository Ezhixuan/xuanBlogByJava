package com.ezhixuan.xuan_framework;

/**
 * @author ezhixuan
 */
public enum UploadEnum {
  LOCAL_UPLOAD("xuan_framework\\src\\main\\resources\\static\\img", "local"),
  COS_UPLOAD("xuanBlog/img", "cos");

  private String path;
  private String desc;

  UploadEnum(String path, String desc) {
    this.path = path;
    this.desc = desc;
  }

  public String getPath() {
    return path;
  }

  public String getDesc() {
    return desc;
  }
}
