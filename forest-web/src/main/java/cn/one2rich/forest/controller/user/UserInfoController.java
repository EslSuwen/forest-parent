package cn.one2rich.forest.controller.user;

import cn.one2rich.forest.dto.ChangeEmailDTO;
import cn.one2rich.forest.dto.UpdatePasswordDTO;
import cn.one2rich.forest.dto.UserInfoDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.UserExtend;
import cn.one2rich.forest.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@RestController
@RequestMapping("/api/user-info")
public class UserInfoController {

  @Resource private UserService userService;

  @GetMapping("/detail/{idUser}")
  public Result<?> detail(@PathVariable Integer idUser) {
    return userService.findUserInfo(idUser);
  }

  @GetMapping("/check-nickname")
  public Result<?> checkNickname(@RequestParam Integer idUser, @RequestParam String nickname) {
    return userService.checkNickname(idUser, nickname);
  }

  @PatchMapping("/update")
  public Result<?> updateUserInfo(@RequestBody UserInfoDTO user) {
    return userService.updateUserInfo(user);
  }

  @PatchMapping("/update-extend")
  public Result<?> updateUserExtend(@RequestBody UserExtend userExtend) {
    return userService.updateUserExtend(userExtend);
  }

  @PatchMapping("/update-email")
  public Result<?> updateEmail(@RequestBody ChangeEmailDTO changeEmailDTO) {
    return userService.updateEmail(changeEmailDTO);
  }

  @PatchMapping("/update-password")
  public Result<?> updatePassword(@RequestBody UpdatePasswordDTO updatePasswordDTO) {
    return userService.updatePassword(updatePasswordDTO);
  }
}
