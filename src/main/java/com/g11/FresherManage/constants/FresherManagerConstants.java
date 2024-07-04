package com.g11.FresherManage.constants;

public class FresherManagerConstants {

  public static class CommonConstants {
    public static final String ENCODING_UTF_8 = "UTF-8";
    public static final String LANGUAGE = "Accept-Language";
  }

  public static class AuditorConstant {
    public static final String ANONYMOUS = "anonymousUser";
    public static final String SYSTEM = "SYSTEM";
  }

  public static class StatusException {
    public static final Integer NOT_FOUND = 404;
    public static final Integer CONFLICT = 409;
    public static final Integer BAD_REQUEST = 400;
    public static final Integer FORBIDDEN = 403;
    public static final Integer UNAUTHORIZED = 401;
    public static final Integer INTERNAL_SERVER_ERROR = 500;
  }

  public static class MessageException {

  }

  public static class AuthConstant {
    public static String TYPE_TOKEN = "Bear ";
    public static String AUTHORIZATION = "Authorization";
    public static String CLAIM_USERNAME_KEY = "username";
    public static String CLAIM_AUTHORITIES_KEY = "authorities";
    public static Integer ENABLED = 1;
    public static Integer DISABLED = 0;
  }

  public static class MessageCode {
    public static String SUCCESS = "Success";
    public static final String CONFIRM_PASSWORD_NOT_MATCH = "ValidationConfirmPassword";
  }


}
