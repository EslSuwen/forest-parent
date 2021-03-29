package cn.one2rich.forest.controller.admin;

import cn.one2rich.forest.dto.admin.TopicTagDTO;
import cn.one2rich.forest.dto.admin.UserRoleDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Role;
import cn.one2rich.forest.entity.Tag;
import cn.one2rich.forest.entity.Topic;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.service.RoleService;
import cn.one2rich.forest.service.TagService;
import cn.one2rich.forest.service.TopicService;
import cn.one2rich.forest.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** @author ronger */
@Api(value = "管理员", tags = "管理员")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

  @Resource private UserService userService;
  @Resource private RoleService roleService;
  @Resource private TopicService topicService;
  @Resource private TagService tagService;

  @ApiOperation("用户分页信息")
  @GetMapping("/users")
  public Result<IPage<User>> users(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows) {
    // 按最后登录时间进行倒序排序
    IPage<User> list =
        userService.page(
            new Page<>(page, rows),
            new LambdaQueryWrapper<User>().orderByDesc(User::getLastLoginTime));
    return Result.OK(list);
  }

  @ApiOperation("用户角色分页信息")
  @GetMapping("/user/{idUser}/role")
  public Result<List<Role>> userRole(@PathVariable Integer idUser) {
    return Result.OK(roleService.findByIdUser(idUser));
  }

  @ApiOperation("角色分页信息")
  @GetMapping("/roles")
  public Result<IPage<Role>> roles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows) {
    return Result.OK(roleService.page(new Page<>(page, rows)));
  }

  @ApiOperation("更新角色信息")
  @PatchMapping("/user/update-role")
  public Result<?> updateUserRole(@RequestBody UserRoleDTO userRole) {
    return userService.updateUserRole(userRole.getIdUser(), userRole.getIdRole());
  }

  @ApiOperation("更新用户状态信息")
  @PatchMapping("/user/update-status")
  public Result<?> updateUserStatus(@RequestBody User user) {
    return userService.updateStatus(user.getIdUser(), user.getStatus());
  }

  @ApiOperation("更新角色状态信息")
  @PatchMapping("/role/update-status")
  public Result<?> updateRoleStatus(@RequestBody Role role) {
    return roleService.updateStatus(role.getIdRole(), role.getStatus());
  }

  @ApiOperation("增加角色")
  @RequestMapping(
      value = "/role/post",
      method = {RequestMethod.POST, RequestMethod.PUT})
  public Result<Role> addRole(@RequestBody Role role) {
    return Result.OK(roleService.saveRole(role));
  }

  @ApiOperation("专题分页信息")
  @GetMapping("/topics")
  public Result<IPage<Topic>> topics(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows) {
    IPage<Topic> list = topicService.page(new Page<>(page, rows));
    return Result.OK(list);
  }

  @ApiOperation("专题信息")
  @GetMapping("/topic/{topicUri}")
  public Result<?> topic(@PathVariable String topicUri) {
    if (StringUtils.isBlank(topicUri)) {
      return Result.error("数据异常!");
    }
    return Result.OK(topicService.findTopicByTopicUri(topicUri));
  }

  @ApiOperation("专题关联标签信息")
  @GetMapping("/topic/{topicUri}/tags")
  public Result<?> topicTags(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @PathVariable String topicUri) {
    if (StringUtils.isBlank(topicUri)) {
      return Result.error("数据异常!");
    }
    return topicService.findTagsByTopicUri(new Page<>(page, rows), topicUri);
  }

  @ApiOperation("专题详细信息")
  @GetMapping("/topic/detail/{idTopic}")
  public Result<Topic> topicDetail(@PathVariable Integer idTopic) {
    return Result.OK(topicService.getById(idTopic.toString()));
  }

  @ApiOperation("专题未关联标签分页信息")
  @GetMapping("/topic/unbind-topic-tags")
  public Result<?> unbindTopicTags(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @RequestParam Integer idTopic,
      @RequestParam String tagTitle) {
    return Result.OK(topicService.findUnbindTagsById(new Page<>(page, rows), idTopic, tagTitle));
  }

  @ApiOperation("专题关联标签")
  @PostMapping("/topic/bind-topic-tag")
  public Result<?> bindTopicTag(@RequestBody TopicTagDTO topicTag) {
    return topicService.bindTopicTag(topicTag);
  }

  @ApiOperation("专题取消关联标签")
  @DeleteMapping("/topic/unbind-topic-tag")
  public Result<?> unbindTopicTag(@RequestBody TopicTagDTO topicTag) {
    return topicService.unbindTopicTag(topicTag);
  }

  @ApiOperation("新增专题信息")
  @RequestMapping(
      value = "/topic/post",
      method = {RequestMethod.PUT, RequestMethod.POST})
  public Result<?> addTopic(@RequestBody Topic topic) {
    return topicService.saveTopic(topic);
  }

  @ApiOperation("标签分页信息")
  @GetMapping("/tags")
  public Result<IPage<Tag>> tags(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows) {
    return Result.OK(tagService.page(new Page<>(page, rows)));
  }

  @ApiOperation("清除未使用标签")
  @DeleteMapping("/tag/clean-unused")
  public Result<?> cleanUnusedTag() {
    return Result.OK(tagService.cleanUnusedTag());
  }

  @ApiOperation("标签详细信息")
  @GetMapping("/tag/detail/{idTag}")
  public Result<Tag> tagDetail(@PathVariable Integer idTag) {
    return Result.OK(tagService.getById(idTag));
  }

  @ApiOperation("新增标签")
  @RequestMapping(
      value = "/tag/post",
      method = {RequestMethod.POST, RequestMethod.PUT})
  public Result<?> addTag(@RequestBody Tag tag) {
    return Result.OK(tagService.saveTag(tag));
  }
}
