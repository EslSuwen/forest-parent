package cn.one2rich.forest.controller.common;

import cn.one2rich.forest.util.log.annotation.VisitLogger;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.*;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.service.*;
import cn.one2rich.forest.util.UserUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@RestController
@RequestMapping("/api/console")
public class CommonApiController {

  @Resource private UserService userService;
  @Resource private ArticleService articleService;
  @Resource private PortfolioService portfolioService;

  @PostMapping("/register")
  public Result<?> register(@RequestBody UserRegisterInfoDTO registerInfo) {
    return userService.register(
        registerInfo.getEmail(), registerInfo.getPassword(), registerInfo.getCode());
  }

  @PostMapping("/login")
  public Result<?> login(@RequestBody User user) {
    return userService.login(user.getAccount(), user.getPassword());
  }

  @GetMapping("/heartbeat")
  public Result<String> heartbeat() {
    return Result.OK("heartbeat");
  }

  @GetMapping("/articles")
  @VisitLogger
  public Result<IPage<ArticleDTO>> articles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      ArticleSearchDTO searchDTO) {
    IPage<ArticleDTO> list = articleService.findArticles(new Page<>(page, rows), searchDTO);
    return Result.OK(list);
  }

  @GetMapping("/article/{id}")
  @VisitLogger
  public Result<?> article(@PathVariable Integer id) {
    return Result.OK(articleService.findArticleDTOById(id, 1));
  }

  @GetMapping("/token/{token}")
  public Result<?> token(@PathVariable String token) {
    return Result.OK(UserUtils.getTokenUser(token));
  }

  @PatchMapping("/forget-password")
  public Result<?> forgetPassword(@RequestBody ForgetPasswordDTO forgetPassword) {
    return userService.forgetPassword(forgetPassword.getCode(), forgetPassword.getPassword());
  }

  @GetMapping("/portfolio/{id}")
  @VisitLogger
  public Result<PortfolioDTO> portfolio(@PathVariable Integer id) {
    return Result.OK(portfolioService.findPortfolioDTOById(id, 1));
  }

  @GetMapping("/portfolio/{id}/articles")
  public Result<IPage<ArticleDTO>> articles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @PathVariable Integer id) {
    return Result.OK(articleService.findArticlesByIdPortfolio(new Page<>(page, rows), id));
  }
}
