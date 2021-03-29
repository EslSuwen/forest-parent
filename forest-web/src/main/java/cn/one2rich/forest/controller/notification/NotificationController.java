package cn.one2rich.forest.controller.notification;

import cn.one2rich.forest.core.exception.BaseApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.NotificationDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.service.NotificationService;
import cn.one2rich.forest.util.UserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 消息通知
 *
 * @author ronger
 */
@Api(value = "消息通知", tags = "消息通知")
@RestController
@RequestMapping("/api/notification")
public class NotificationController {

  @Resource private NotificationService notificationService;

  @ApiOperation("用户消息分页信息")
  @GetMapping("/all")
  public Result<IPage<NotificationDTO>> notifications(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows)
      throws BaseApiException {
    User user = UserUtils.getCurrentUserByToken();
    return Result.OK(
        notificationService.findNotifications(new Page<>(page, rows), user.getIdUser()));
  }

  @ApiOperation("用户未读消息分页信息")
  @GetMapping("/unread")
  public Result<?> unreadNotification(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows)
      throws BaseApiException {
    User user = UserUtils.getCurrentUserByToken();
    return Result.OK(
        notificationService.findUnreadNotifications(new Page<>(page, rows), user.getIdUser()));
  }

  @ApiOperation("用户确定已读消息")
  @PutMapping("/read/{id}")
  public Result<?> read(@PathVariable Integer id) {
    Integer result = notificationService.readNotification(id);
    return result != 0 ? Result.OK("标记已读成功") : Result.error("标记已读失败");
  }
}
