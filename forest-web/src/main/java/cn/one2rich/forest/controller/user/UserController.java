package cn.one2rich.forest.controller.user;

import cn.one2rich.forest.util.log.annotation.VisitLogger;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.UserDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.FollowService;
import cn.one2rich.forest.service.PortfolioService;
import cn.one2rich.forest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@Api(value = "用户", tags = "用户")
@RestController
@RequestMapping("/api/user")
public class UserController {

  @Resource private UserService userService;
  @Resource private ArticleService articleService;
  @Resource private PortfolioService portfolioService;
  @Resource private FollowService followService;

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/{nickname}")
  @VisitLogger
  public Result<UserDTO> detail(@PathVariable String nickname) {
    return Result.OK(userService.findUserDTOByNickname(nickname));
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/{nickname}/articles")
  public Result<?> userArticles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "12") Integer rows,
      @PathVariable String nickname) {
    UserDTO userDTO = userService.findUserDTOByNickname(nickname);
    if (userDTO == null) {
      return Result.error("用户不存在！");
    }
    return Result.OK(
        articleService.findUserArticlesByIdUser(new Page<>(page, rows), userDTO.getIdUser()));
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/{nickname}/portfolios")
  public Result<?> userPortfolios(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "12") Integer rows,
      @PathVariable String nickname) {
    UserDTO userDTO = userService.findUserDTOByNickname(nickname);
    if (userDTO == null) {
      return Result.error("用户不存在！");
    }
    return Result.OK(portfolioService.findUserPortfoliosByUser(new Page<>(page, rows), userDTO));
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/{nickname}/followers")
  public Result<?> userFollowers(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "12") Integer rows,
      @PathVariable String nickname) {
    UserDTO userDTO = userService.findUserDTOByNickname(nickname);
    if (userDTO == null) {
      return Result.error("用户不存在！");
    }
    return Result.OK(followService.findUserFollowersByUser(new Page<>(page, rows), userDTO));
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/{nickname}/followings")
  public Result<?> userFollowings(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "12") Integer rows,
      @PathVariable String nickname) {
    UserDTO userDTO = userService.findUserDTOByNickname(nickname);
    if (userDTO == null) {
      return Result.error("用户不存在！");
    }
    return Result.OK(followService.findUserFollowingsByUser(new Page<>(page, rows), userDTO));
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/{nickname}/user-extend")
  public Result<?> userExtend(@PathVariable String nickname) {
    return Result.OK(userService.selectUserExtendByNickname(nickname));
  }
}
