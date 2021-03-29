package cn.one2rich.forest.controller.user;

import cn.one2rich.forest.dto.ChangeEmailDTO;
import cn.one2rich.forest.dto.UpdatePasswordDTO;
import cn.one2rich.forest.dto.UserInfoDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.UserExtend;
import cn.one2rich.forest.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@RestController
@RequestMapping("/api/user-info")
public class UserInfoController {

  @Resource private UserService userService;

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/detail/{idUser}")
  public Result<?> detail(@PathVariable Integer idUser) {
    return userService.findUserInfo(idUser);
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/check-nickname")
  public Result<?> checkNickname(@RequestParam Integer idUser, @RequestParam String nickname) {
    return userService.checkNickname(idUser, nickname);
  }

  @ApiOperation("用户未读消息分页信息")
  @PatchMapping("/update")
  public Result<?> updateUserInfo(@RequestBody UserInfoDTO user) {
    return userService.updateUserInfo(user);
  }

  @ApiOperation("用户未读消息分页信息")
  @PatchMapping("/update-extend")
  public Result<?> updateUserExtend(@RequestBody UserExtend userExtend) {
    return userService.updateUserExtend(userExtend);
  }

  @ApiOperation("用户未读消息分页信息")
  @PatchMapping("/update-email")
  public Result<?> updateEmail(@RequestBody ChangeEmailDTO changeEmailDTO) {
    return userService.updateEmail(changeEmailDTO);
  }

  @ApiOperation("用户未读消息分页信息")
  @PatchMapping("/update-password")
  public Result<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
    return userService.updatePassword(updatePasswordDTO);
  }
}
