package cn.one2rich.forest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.one2rich.forest.dto.*;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.entity.UserExtend;
import org.apache.ibatis.exceptions.TooManyResultsException;

/**
 * @author CodeGenerator
 * @date 2018/05/29
 */
public interface UserService extends IService<User> {

  /**
   * 通过账号查询用户信息
   *
   * @param account
   * @throws TooManyResultsException
   * @return User
   */
  User findByAccount(String account) throws TooManyResultsException;

  /**
   * 注册接口
   *
   * @param email 邮箱
   * @param password 密码
   * @param code 验证码
   * @return Map
   */
  Result<?> register(String email, String password, String code);

  /**
   * 登录接口
   *
   * @param account 邮箱
   * @param password 密码
   * @return Map
   */
  Result<?> login(String account, String password);

  /**
   * 通过 nickname 获取用户信息接口
   *
   * @param nickname 昵称
   * @return UserDTO
   */
  UserDTO findUserDTOByNickname(String nickname);

  /**
   * 找回密码接口
   *
   * @param code 验证码
   * @param password 密码
   * @return Map
   */
  Result<?> forgetPassword(String code, String password);

  /**
   * 更新用户角色接口
   *
   * @param idUser 用户 id
   * @param idRole 角色 id
   * @return Map
   */
  Result<?> updateUserRole(Integer idUser, Integer idRole);

  /**
   * 更新用户状态
   *
   * @param idUser 用户 id
   * @param status 状态
   * @return Map
   */
  Result<?> updateStatus(Integer idUser, String status);

  /**
   * 获取用户信息
   *
   * @param idUser
   * @return
   */
  Result<?> findUserInfo(Integer idUser);

  /**
   * 更新用户信息
   *
   * @param user
   * @return
   */
  Result<?> updateUserInfo(UserInfoDTO user);

  /**
   * 验证昵称是否重复
   *
   * @param idUser
   * @param nickname
   * @return
   */
  Result<?> checkNickname(Integer idUser, String nickname);

  /**
   * 获取用户权限
   *
   * @param idUser
   * @return
   */
  Integer findRoleWeightsByUser(Integer idUser);

  /**
   * 查询作者信息
   *
   * @param idUser
   * @return
   */
  Author selectAuthor(Integer idUser);

  /**
   * 更新用户扩展信息
   *
   * @param userExtend
   * @return
   */
  Result<?> updateUserExtend(UserExtend userExtend);

  /**
   * 获取用户扩展信息
   *
   * @param nickname
   * @return
   */
  UserExtend selectUserExtendByNickname(String nickname);

  /**
   * 更换邮箱
   *
   * @param changeEmailDTO
   * @return
   */
  Result<?> updateEmail(ChangeEmailDTO changeEmailDTO);

  /**
   * 更新密码
   *
   * @param updatePasswordDTO
   * @return
   */
  Result<?> updatePassword(UpdatePasswordDTO updatePasswordDTO);
}
