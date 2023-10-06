package com.ezhixuan.xuan_framework.domain.vo;

import com.ezhixuan.xuan_framework.domain.enums.AppHttpCodeEnum;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * 通用的结果返回类
 *
 * @author ezhixuan
 * @param <T>
 */
public class ResponseResult<T> implements Serializable {

  private static final long serialVersionUID = 1L;
  public static final int SUCCESS_CODE = 200;
  public static final String SUCCESS_MSG = "操作成功";
  public static final int FAIL_CODE = 500;
  public static final String FAIL_MSG = "操作失败";

  public static final ResponseResult<String> SUCCESS =
      new ResponseResult<>(SUCCESS_CODE, SUCCESS_MSG, null);
  public static final ResponseResult<String> FAIL = new ResponseResult<>(FAIL_CODE, FAIL_MSG, null);
  private Integer code;
  private String message;
  private T data;

  public ResponseResult() {
    this.code = SUCCESS_CODE;
  }

  public ResponseResult(Integer code, T data) {
    this.code = code;
    this.data = data;
  }

  public ResponseResult(Integer code, String msg) {
    this.code = code;
    this.message = msg;
  }

  public ResponseResult(Integer code, String msg, T data) {
    this.code = code;
    this.message = msg;
    this.data = data;
  }

  /**
   * 操作成功，只传入data,默认code=200,msg=操作成功
   *
   * @param data
   * @return
   * @param <T>
   */
  public static <T> ResponseResult<T> okResult(T data) {
    ResponseResult<T> result = new ResponseResult<>(SUCCESS_CODE, SUCCESS_MSG);
    if (!ObjectUtils.isEmpty(data)) {
      result.setData(data);
    }
    return result;
  }

  /**
   * 操作成功，传入code和msg,默认data=null
   *
   * @param code
   * @param msg
   * @return
   * @param <T>
   */
  public static <T> ResponseResult<T> okResult(int code, String msg) {
    return new ResponseResult<>(code, msg, null);
  }

  /**
   * 操作成功，传入AppHttpCodeEnum,默认data=null
   *
   * @param enums
   * @return
   * @param <T>
   */
  public static <T> ResponseResult<T> okResult(AppHttpCodeEnum enums) {
    return okResult(enums.getCode(), enums.getMessage());
  }

  /**
   * 出现错误，输入code以及msg,默认data=null
   *
   * @param code
   * @param msg
   * @return
   * @param <T>
   */
  public static <T> ResponseResult<T> errorResult(int code, String msg) {
    return new ResponseResult<>(code, msg, null);
  }

  /**
   * 出现错误,传入AppHttpCodeEnum,默认data=null
   *
   * @param enums
   * @return
   * @param <T>
   */
  public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums) {
    return errorResult(enums.getCode(), enums.getMessage());
  }

  /**
   * 出现错误，使用enum枚举好的code码，自定义错误信息,默认data=null
   *
   * @param enums
   * @param errorMessage
   * @return
   * @param <T>
   */
  public static <T> ResponseResult<T> errorResult(AppHttpCodeEnum enums, String errorMessage) {
    return errorResult(enums.getCode(), errorMessage);
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResponseResult<?> that = (ResponseResult<?>) o;
    return Objects.equals(code, that.code)
        && Objects.equals(message, that.message)
        && Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code, message, data);
  }

  @Override
  public String toString() {
    return "ResponseResult{"
        + "code="
        + code
        + ", message='"
        + message
        + '\''
        + ", data="
        + data
        + '}';
  }

  public static void main(String[] args) {
    /*System.out.println(ResponseResult.SUCCESS);
    System.out.println(ResponseResult.FAIL);

    ResponseResult<Object> responseResult = ResponseResult.okResult(AppHttpCodeEnum.NEED_LOGIN);
    System.out.println(responseResult);

    ResponseResult<Object> responseResult1 = ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "啊？卡拉~");
    System.out.println(responseResult1);

    Integer code1 = ResponseResult.SUCCESS.getCode();
    System.out.println(code1);

    boolean equals = ResponseResult.SUCCESS.equals(responseResult);
    System.out.println(equals);*/
  }
}
