package cn.one2rich.forest.service.impl;

import cn.one2rich.forest.core.exception.BaseApiException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.core.constant.NotificationConstant;
import cn.one2rich.forest.dto.UserDTO;
import cn.one2rich.forest.entity.Follow;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.mapper.FollowMapper;
import cn.one2rich.forest.service.FollowService;
import cn.one2rich.forest.util.NotificationUtils;
import cn.one2rich.forest.util.UserUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/** @author ronger */
@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {

  @Resource private FollowMapper followMapper;

  @Override
  public Boolean isFollow(Integer followingId, String followingType) throws BaseApiException {
    User tokenUser = UserUtils.getCurrentUserByToken();
    Boolean b = followMapper.isFollow(followingId, tokenUser.getIdUser(), followingType);
    return b;
  }

  @Override
  public Boolean follow(Follow follow) throws BaseApiException {
    User tokenUser = UserUtils.getCurrentUserByToken();
    follow.setFollowerId(tokenUser.getIdUser());
    if (save(follow)) {
      NotificationUtils.saveNotification(
          follow.getFollowingId(),
          follow.getIdFollow(),
          NotificationConstant.Follow,
          tokenUser.getNickname() + " 关注了你!");
    }
    return true;
  }

  @Override
  public Boolean cancelFollow(Follow follow) throws BaseApiException {
    User tokenUser = UserUtils.getCurrentUserByToken();
    follow.setFollowerId(tokenUser.getIdUser());
    // TODO 测试删除逻辑
    int result =
        followMapper.delete(
            new LambdaQueryWrapper<Follow>().eq(Follow::getFollowerId, tokenUser.getIdUser()));
    return result == 0;
  }

  @Override
  public List<Follow> findByFollowingId(String followType, Integer followingId) {
    return list(
        new LambdaQueryWrapper<Follow>()
            .eq(Follow::getFollowingType, followType)
            .eq(Follow::getFollowingId, followingId));
  }

  @Override
  public IPage<UserDTO> findUserFollowersByUser(Page<?> page, UserDTO userDTO) {
    return followMapper.selectUserFollowersByUser(page, userDTO.getIdUser());
  }

  @Override
  public IPage<UserDTO> findUserFollowingsByUser(Page<?> page, UserDTO userDTO) {
    return followMapper.selectUserFollowingsByUser(page, userDTO.getIdUser());
  }
}
