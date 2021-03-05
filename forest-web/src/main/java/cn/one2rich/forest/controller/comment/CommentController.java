package cn.one2rich.forest.controller.comment;

import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Comment;
import cn.one2rich.forest.service.CommentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/** @author ronger */
@RestController
@RequestMapping("/api/comment")
public class CommentController {

  @Resource private CommentService commentService;

  @PostMapping("/post")
  public Result<?> postComment(@RequestBody Comment comment, HttpServletRequest request) {
    return commentService.postComment(comment, request);
  }
}
