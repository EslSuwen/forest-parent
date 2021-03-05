package cn.one2rich.forest.jwt.def;

/** @author ronger */
public class JwtConstants {

  /** 上线需要变更 */
  public static final String JWT_SECRET = "JYJ5Qv2WF4lA6jPl5GKuAG";

  public static final String AUTHORIZATION = "Authorization";
  public static final String UPLOAD_TOKEN = "X-Upload-Token";
  public static final String CURRENT_USER_NAME = "CURRENT_TOKEN_USER_NAME";
  public static final String CURRENT_TOKEN_CLAIMS = "CURRENT_TOKEN_CLAIMS";
  public static final long TOKEN_EXPIRES_HOUR = 2 * 60;
}
