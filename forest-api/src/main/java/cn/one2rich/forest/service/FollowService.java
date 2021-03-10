package cn.one2rich.forest.service;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.dto.UserDTO;
import cn.one2rich.forest.entity.Follow;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/** @author ronger */
public interface FollowService extends IService<Follow> {
  /**
   * 判断是否关注
   *
   * @param followingId
   * @param followingType
   * @return
   * @throws BaseApiException
   */
  Boolean isFollow(Integer followingId, String followingType) throws BaseApiException;

  /**
   * 关注操作
   *
   * @param follow
   * @return
   * @throws BaseApiException
   */
  Boolean follow(Follow follow) throws BaseApiException;

  /**
   * 取消关注操作
   *
   * @param follow
   * @return
   * @throws BaseApiException
   */
  Boolean cancelFollow(Follow follow) throws BaseApiException;

  /**
   * 获取关注用户者数据
   *
   * @param followType
   * @param followingId
   * @return
   */
  List<Follow> findByFollowingId(String followType, Integer followingId);

  /**
   * 查询用户粉丝
   *
   *
   * @param page
   * @param userDTO
   * @return
   */
  IPage<UserDTO> findUserFollowersByUser(Page<?> page, UserDTO userDTO);

  /**
   * 查询用户关注用户
   *
   *
   * @param page
   * @param userDTO
   * @return
   */
  IPage<UserDTO> findUserFollowingsByUser(Page<?> page, UserDTO userDTO);
}
