package cn.one2rich.forest.mapper;

import cn.one2rich.forest.dto.UserDTO;
import cn.one2rich.forest.entity.Follow;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/** @author ronger */
public interface FollowMapper extends BaseMapper<Follow> {
  /**
   * 判断是否关注
   *
   * @param followingId
   * @param followerId
   * @param followingType
   * @return
   */
  Boolean isFollow(
          @Param("followingId") Integer followingId,
          @Param("followerId") Integer followerId,
          @Param("followingType") String followingType);

  /**
   * 查询用户粉丝
   *
   * @param idUser
   * @return
   */
  IPage<UserDTO> selectUserFollowersByUser(Page<?> page, @Param("idUser") Integer idUser);

  /**
   * 查询用户关注用户
   *
   * @param idUser
   * @return
   */
  IPage<UserDTO> selectUserFollowingsByUser(IPage<?> page, @Param("idUser") Integer idUser);
}
