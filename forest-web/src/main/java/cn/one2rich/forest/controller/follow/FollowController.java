package cn.one2rich.forest.controller.follow;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Follow;
import cn.one2rich.forest.service.FollowService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@RestController
@RequestMapping("/api/follow")
public class FollowController {

  @Resource private FollowService followService;

  @GetMapping("/is-follow")
  public Result<?> isFollow(@RequestParam Integer followingId, @RequestParam String followingType)
      throws BaseApiException {
    return Result.OK(followService.isFollow(followingId, followingType));
  }

  @PostMapping
  public Result<?> follow(@RequestBody Follow follow) throws BaseApiException {
    return Result.OK(followService.follow(follow));
  }

  @PostMapping("cancel-follow")
  public Result<?> cancelFollow(@RequestBody Follow follow) throws BaseApiException {
    return Result.OK(followService.cancelFollow(follow));
  }
}
