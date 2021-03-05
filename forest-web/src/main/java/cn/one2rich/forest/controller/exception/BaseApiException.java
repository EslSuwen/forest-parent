package cn.one2rich.forest.controller.exception;

/** 服务（业务）异常如“ 账号或密码错误 ”，该异常只做INFO级别的日志记录 @see WebMvcConfigurer */
public class BaseApiException extends Exception {

  private int code;
  private String message;

  public BaseApiException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.code = errorCode.getCode();
    this.message = errorCode.getMessage();
  }

  public int getCode() {
    return code;
  }

  public String getExtraMessage() {
    return message;
  }
}
