package cn.one2rich.forest.controller.common;

import cn.hutool.core.util.StrUtil;
import cn.one2rich.forest.dto.ThirdUser;
import cn.one2rich.forest.service.ThirdUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * NebulaIndexController
 *
 * @author suwen
 * @date 2021/3/7 14:05
 */
@Api(value = "首页跳转", tags = "首页跳转")
@Controller
@RequestMapping("/nebula")
public class NebulaIndexController {

  @Resource ThirdUserService thirdUserService;

  @ApiOperation("首页跳转")
  @GetMapping
  public void index(HttpServletResponse response) throws IOException {
    ThirdUser thirdUser = thirdUserService.getCurrentUser();
    response.sendRedirect(
        StrUtil.format(
            "/api/thirdLogin/callback?uuid={}&nickname={}&username={}&avatar={}&email={}",
            thirdUser.getUuid(),
            thirdUser.getNickname(),
            thirdUser.getUsername(),
            thirdUser.getAvatar(),
            thirdUser.getEmail()));
  }
}
