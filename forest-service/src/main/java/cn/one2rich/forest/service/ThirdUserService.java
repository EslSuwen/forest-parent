package cn.one2rich.forest.service;

import cn.one2rich.forest.dto.ThirdUser;

/**
 * ThirdUserService
 *
 * @author suwen
 * @date 2021/3/7 14:05
 */
public interface ThirdUserService {

  /**
   * 三方登录时返回用户信息
   *
   * @return 默认用户
   */
  default ThirdUser getCurrentUser() {
    return ThirdUser.builder().uuid("1").username("admin").nickname("admin").build();
  }
}
