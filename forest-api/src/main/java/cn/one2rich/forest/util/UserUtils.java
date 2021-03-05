package cn.one2rich.forest.util;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.dto.TokenUser;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.jwt.def.JwtConstants;
import cn.one2rich.forest.jwt.model.TokenModel;
import cn.one2rich.forest.jwt.service.TokenManager;
import cn.one2rich.forest.mapper.UserMapper;
import cn.one2rich.forest.core.exception.ErrorCode;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/** @author ronger */
public class UserUtils {

  private static UserMapper userMapper = SpringContextHolder.getBean(UserMapper.class);
  private static TokenManager tokenManager = SpringContextHolder.getBean(TokenManager.class);

  /**
   * 通过token获取当前用户的信息
   *
   * @return
   */
  public static User getCurrentUserByToken() throws BaseApiException {
    String authHeader = ContextHolderUtils.getRequest().getHeader(JwtConstants.AUTHORIZATION);
    if (authHeader != null) {
      // 验证token
      Claims claims;
      try {
        claims =
            Jwts.parser()
                .setSigningKey(JwtConstants.JWT_SECRET)
                .parseClaimsJws(authHeader)
                .getBody();
      } catch (final SignatureException e) {
        throw new BaseApiException(ErrorCode.UNAUTHORIZED);
      }
      Object account = claims.getId();
      if (StringUtils.isNotBlank(Objects.toString(account, ""))) {
        TokenModel model = tokenManager.getToken(authHeader, account.toString());
        if (tokenManager.checkToken(model)) {
          return userMapper.findByAccount(account.toString());
        }
      }
    }
    throw new BaseApiException(ErrorCode.UNAUTHORIZED);
  }

  public static TokenUser getTokenUser(String token) {
    if (StringUtils.isNotBlank(token)) {
      // 验证token
      Claims claims;
      try {
        claims =
            Jwts.parser().setSigningKey(JwtConstants.JWT_SECRET).parseClaimsJws(token).getBody();
      } catch (final SignatureException e) {
        return null;
      }
      Object account = claims.getId();
      if (StringUtils.isNotBlank(Objects.toString(account, ""))) {
        TokenModel model = tokenManager.getToken(token, account.toString());
        if (tokenManager.checkToken(model)) {
          User user = userMapper.findByAccount(account.toString());
          if (user != null) {
            TokenUser tokenUser = new TokenUser();
            BeanCopierUtil.copy(user, tokenUser);
            tokenUser.setToken(token);
            tokenUser.setWeights(userMapper.selectRoleWeightsByUser(user.getIdUser()));
            return tokenUser;
          }
        }
      }
    }
    return null;
  }
}
