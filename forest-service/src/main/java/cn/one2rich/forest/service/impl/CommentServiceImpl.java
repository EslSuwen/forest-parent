package cn.one2rich.forest.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.one2rich.forest.core.constant.NotificationConstant;
import cn.one2rich.forest.dto.Author;
import cn.one2rich.forest.dto.CommentDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Article;
import cn.one2rich.forest.entity.Comment;
import cn.one2rich.forest.mapper.CommentMapper;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.CommentService;
import cn.one2rich.forest.util.Html2TextUtil;
import cn.one2rich.forest.util.NotificationUtils;
import cn.one2rich.forest.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/** @author ronger */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService {

  @Resource private CommentMapper commentMapper;
  @Resource private ArticleService articleService;

  private static final int MAX_PREVIEW = 200;

  @Override
  public List<CommentDTO> getArticleComments(Integer idArticle) {
    List<CommentDTO> commentDTOList = commentMapper.selectArticleComments(idArticle);
    commentDTOList.forEach(
        commentDTO -> {
          commentDTO.setTimeAgo(Utils.getTimeAgo(commentDTO.getCreatedTime()));
          if (commentDTO.getCommentAuthorId() != null) {
            Author author = commentMapper.selectAuthor(commentDTO.getCommentAuthorId());
            if (author != null) {
              commentDTO.setCommenter(author);
            }
          }
          if (commentDTO.getCommentOriginalCommentId() != null
              && commentDTO.getCommentOriginalCommentId() > 0) {
            Author commentOriginalAuthor =
                commentMapper.selectCommentOriginalAuthor(commentDTO.getCommentOriginalCommentId());
            if (commentOriginalAuthor != null) {
              commentDTO.setCommentOriginalAuthorThumbnailURL(
                  commentOriginalAuthor.getUserAvatarURL());
              commentDTO.setCommentOriginalAuthorNickname(commentOriginalAuthor.getUserNickname());
            }
            Comment comment = getById(commentDTO.getCommentOriginalCommentId());
            commentDTO.setCommentOriginalContent(comment.getCommentContent());
          }
        });
    return commentDTOList;
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Result<?> postComment(Comment comment, HttpServletRequest request) {
    if (comment.getCommentArticleId() == null) {
      return Result.error("非法访问,文章主键异常！");
    }
    if (comment.getCommentAuthorId() == null) {
      return Result.error("非法访问,用户未登录！");
    }
    if (StringUtils.isBlank(comment.getCommentContent())) {
      return Result.error("回帖内容不能为空！");
    }
    Article article = articleService.getById(comment.getCommentArticleId().toString());
    if (article == null) {
      return Result.error("文章不存在！");
    }
    String ip = Utils.getIpAddress(request);
    String ua = request.getHeader("user-agent");
    comment.setCommentIP(ip);
    comment.setCommentUA(ua);
    comment.setCreatedTime(new Date());
    save(comment);
    commentMapper.updateCommentSharpUrl(
        comment.getIdComment(),
        article.getArticlePermalink() + "#comment-" + comment.getIdComment());

    String commentContent = comment.getCommentContent();
    if (StringUtils.isNotBlank(commentContent)) {
      int length = commentContent.length();
      if (length > MAX_PREVIEW) {
        length = 200;
      }
      String commentPreviewContent = commentContent.substring(0, length);
      commentContent = Html2TextUtil.getContent(commentPreviewContent);
      // 评论者不是作者本人则进行消息通知
      if (!article.getArticleAuthorId().equals(comment.getCommentAuthorId())) {
        NotificationUtils.saveNotification(
            article.getArticleAuthorId(),
            comment.getIdComment(),
            NotificationConstant.Comment,
            commentContent);
      }
      // 判断是否是回复消息
      if (comment.getCommentOriginalCommentId() != null
          && comment.getCommentOriginalCommentId() != 0) {
        Comment originalComment = getById(comment.getCommentOriginalCommentId());
        // 回复消息时,评论者不是上级评论作者则进行消息通知
        if (!comment.getCommentAuthorId().equals(originalComment.getCommentAuthorId())) {
          NotificationUtils.saveNotification(
              originalComment.getCommentAuthorId(),
              comment.getIdComment(),
              NotificationConstant.Comment,
              commentContent);
        }
      }
    }
    return Result.OK("评论成功");
  }
}
