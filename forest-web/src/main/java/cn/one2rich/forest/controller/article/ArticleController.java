package cn.one2rich.forest.controller.article;

import cn.one2rich.forest.core.exception.BaseApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Article;
import cn.one2rich.forest.entity.ArticleThumbsUp;
import cn.one2rich.forest.entity.Sponsor;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.ArticleThumbsUpService;
import cn.one2rich.forest.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

/** @author ronger */
@RestController
@RequestMapping("/api/article")
public class ArticleController {

  @Resource private ArticleService articleService;
  @Resource private CommentService commentService;
  @Resource private ArticleThumbsUpService articleThumbsUpService;

  @GetMapping("/list")
  public Result<?> listArticle(
      @RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    return Result.OK(articleService.getArticleList(new Page<>(pageNum, pageSize)));
  }

  @GetMapping("/detail/{id}")
  public Result<ArticleDTO> detailArticle(
      @PathVariable Integer id, @RequestParam(defaultValue = "2") Integer type) {
    return Result.OK(articleService.findArticleDTOById(id, type));
  }

  @PutMapping("/update")
  public Result<Boolean> updateArticle(@RequestBody Article article) {
    return Result.OK(articleService.updateById(article));
  }

  @RequestMapping(
      value = "/post",
      method = {RequestMethod.POST, RequestMethod.PUT})
  public Result<?> postArticle(@RequestBody ArticleDTO article, HttpServletRequest request)
      throws BaseApiException, UnsupportedEncodingException {
    return articleService.postArticle(article, request);
  }

  @DeleteMapping("/delete/{id}")
  public Result<?> delete(@PathVariable Integer id) {
    return articleService.delete(id);
  }

  @GetMapping("/{id}/comments")
  public Result<?> commons(@PathVariable Integer id) {
    return Result.OK(commentService.getArticleComments(id));
  }

  @GetMapping("/drafts")
  public Result<IPage<ArticleDTO>> drafts(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows)
      throws BaseApiException {
    return Result.OK(articleService.findDrafts(new Page<>(page, rows)));
  }

  @GetMapping("/{id}/share")
  public Result<String> share(@PathVariable Integer id) throws BaseApiException {
    return Result.OK(articleService.share(id));
  }

  @PostMapping("/{id}/update-tags")
  public Result<?> updateTags(@PathVariable Integer id, @RequestBody Article article)
      throws BaseApiException, UnsupportedEncodingException {
    return articleService.updateTags(id, article.getArticleTags());
  }

  @PatchMapping("/update-perfect")
  public Result<?> updatePerfect(@RequestBody Article article) {
    return articleService.updatePerfect(article.getIdArticle(), article.getArticlePerfect());
  }

  @PostMapping("/thumbs-up")
  public Result<?> thumbsUp(@RequestBody ArticleThumbsUp articleThumbsUp) throws BaseApiException {
    return articleThumbsUpService.thumbsUp(articleThumbsUp);
  }
}
