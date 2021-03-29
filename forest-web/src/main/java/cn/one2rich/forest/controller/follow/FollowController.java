package cn.one2rich.forest.controller.follow;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Follow;
import cn.one2rich.forest.service.FollowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@Api(value = "用户关注", tags = "用户关注")
@RestController
@RequestMapping("/api/follow")
public class FollowController {

  @Resource private FollowService followService;

  @ApiOperation("是否关注")
  @GetMapping("/is-follow")
  public Result<?> isFollow(@RequestParam Integer followingId, @RequestParam String followingType)
      throws BaseApiException {
    return Result.OK(followService.isFollow(followingId, followingType));
  }

  @ApiOperation("关注用户")
  @PostMapping
  public Result<?> follow(@RequestBody Follow follow) throws BaseApiException {
    return Result.OK(followService.follow(follow));
  }

  @ApiOperation("取消关注用户")
  @PostMapping("cancel-follow")
  public Result<?> cancelFollow(@RequestBody Follow follow) throws BaseApiException {
    return Result.OK(followService.cancelFollow(follow));
  }
}
