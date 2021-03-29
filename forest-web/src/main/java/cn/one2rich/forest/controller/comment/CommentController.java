package cn.one2rich.forest.controller.comment;

import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Comment;
import cn.one2rich.forest.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/** @author ronger */
@Api(value = "评论",tags = "评论")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

  @Resource private CommentService commentService;

  @ApiOperation("发布评论")
  @PostMapping("/post")
  public Result<?> postComment(@RequestBody Comment comment, HttpServletRequest request) {
    return commentService.postComment(comment, request);
  }
}
