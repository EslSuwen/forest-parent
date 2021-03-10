package cn.one2rich.forest.service;

import cn.one2rich.forest.dto.CommentDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/** @author ronger */
public interface CommentService extends IService<Comment> {

  List<CommentDTO> getArticleComments(Integer idArticle);

  Result<?> postComment(Comment comment, HttpServletRequest request);
}
