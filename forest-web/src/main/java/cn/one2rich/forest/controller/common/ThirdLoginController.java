package cn.one2rich.forest.controller.common;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import cn.one2rich.forest.dto.ThirdUser;
import cn.one2rich.forest.dto.TokenUser;
import cn.one2rich.forest.entity.Role;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.jwt.service.TokenManager;
import cn.one2rich.forest.mapper.RoleMapper;
import cn.one2rich.forest.mapper.UserMapper;
import cn.one2rich.forest.service.UserService;
import cn.one2rich.forest.util.BeanCopierUtil;
import cn.one2rich.forest.util.Utils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * ThirdLoginController 第三方登录控制器
 *
 * @author suwen
 * @date 2021/2/23 14:05
 */
@Controller
@RequestMapping("/api/thirdLogin")
@Slf4j
public class ThirdLoginController {

  @Resource private UserService userService;
  @Resource private UserMapper userMapper;
  @Resource private RoleMapper roleMapper;
  @Resource private TokenManager tokenManager;

  @RequestMapping("/callback")
  public void loginThird(ThirdUser thirdUser, HttpServletResponse response) throws IOException {
    log.info("第三方登录进入callback：");
    if (!StrUtil.isBlank(thirdUser.getUuid())) {
      User user;
      // 判断有没有这个人
      List<User> userList =
          userService.list(
              new LambdaQueryWrapper<User>().eq(User::getAccount, thirdUser.getUuid()));
      if (userList == null || userList.size() == 0) {
        // 否则直接创建新账号
        user = saveThirdUser(thirdUser);
        userService.save(user);
        Role role = roleMapper.selectRoleByInputCode("user");
        userMapper.insertUserRole(user.getIdUser(), role.getIdRole());
      } else {
        // 已存在更新用户数据
        user = userList.get(0);
        user.setAvatarUrl(thirdUser.getAvatar());
        userService.updateById(user);
      }
      // 生成token
      TokenUser token = saveToken(user);
      response.sendRedirect(
          StrUtil.format(
              "/nebula/index.html#/login?token={}&idUser={}&weights={}&nickname={}&avatarURL={}",
              token.getToken(),
              token.getIdUser(),
              token.getWeights(),
              URLUtil.encode(token.getNickname()),
              token.getAvatarUrl()));
    } else {
      response.sendRedirect("/nebula/index.html#/login?loginFailed");
    }
  }

  /**
   * 创建新用户
   *
   * @param thirdUser 第三方登录信息
   */
  private User saveThirdUser(ThirdUser thirdUser) {
    User user = new User();
    user.setNickname(thirdUser.getNickname());
    user.setRealName(thirdUser.getUsername());
    user.setAccount(thirdUser.getUuid());
    user.setEmail(thirdUser.getEmail());
    user.setPassword(Utils.entryptPassword("123456"));
    user.setCreatedTime(new Date());
    user.setUpdatedTime(user.getCreatedTime());
    user.setAvatarUrl(thirdUser.getAvatar());
    return user;
  }

  private TokenUser saveToken(User user) {
    userMapper.updateLastLoginTime(user.getIdUser());
    TokenUser tokenUser = new TokenUser();
    BeanCopierUtil.copy(user, tokenUser);
    tokenUser.setToken(tokenManager.createToken(user.getAccount()));
    tokenUser.setWeights(userMapper.selectRoleWeightsByUser(user.getIdUser()));
    return tokenUser;
  }
}
