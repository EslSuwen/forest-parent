package cn.one2rich.forest.service;

import cn.one2rich.forest.core.exception.BaseApiException;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.ArticleListDTO;
import cn.one2rich.forest.dto.ArticleSearchDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Article;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;

/** @author ronger */
public interface ArticleService extends IService<Article> {

  /**
   * 根据检索内容/标签查询文章列表
   *
   * @param searchDTO
   * @return
   */
  IPage<ArticleDTO> findArticles(Page<?> page, ArticleSearchDTO searchDTO);

  /**
   * 根据检索内容/标签查询文章列表
   *
   * @param page
   * @return
   */
  IPage<ArticleListDTO> getArticleList(Page<?> page);

  /**
   * 查询文章详情信息
   *
   * @param id
   * @param type
   * @return
   */
  ArticleDTO findArticleDTOById(Integer id, Integer type);

  /**
   * 查询主题下文章列表
   *
   * @param name
   * @return
   */
  IPage<ArticleDTO> findArticlesByTopicUri(Page<?> page, String name);

  /**
   * 查询标签下文章列表
   *
   * @param name
   * @return
   */
  List<ArticleDTO> findArticlesByTagName(Page<?> page, String name);

  /**
   * 查询用户文章列表
   *
   * @param page
   * @param idUser
   * @return
   */
  IPage<ArticleDTO> findUserArticlesByIdUser(Page<?> page, Integer idUser);

  /**
   * 新增/更新文章
   *
   * @param article
   * @param request
   * @throws UnsupportedEncodingException
   * @throws BaseApiException
   * @return
   */
  Result<?> postArticle(ArticleDTO article, HttpServletRequest request)
      throws UnsupportedEncodingException, BaseApiException;

  /**
   * 删除文章
   *
   * @param id
   * @return
   */
  Result<?> delete(Integer id);

  /**
   * 增量文章浏览数
   *
   * @param id
   */
  void incrementArticleViewCount(Integer id);

  /**
   * 获取分享链接数据
   *
   * @param id
   * @throws BaseApiException
   * @return
   */
  String share(Integer id) throws BaseApiException;

  /**
   * 查询草稿文章类别
   *
   * @throws BaseApiException
   * @return
   * @param page
   */
  IPage<ArticleDTO> findDrafts(Page<?> page) throws BaseApiException;

  /**
   * 查询作品集下文章
   *
   * @param idPortfolio
   * @return
   */
  IPage<ArticleDTO> findArticlesByIdPortfolio(Page<?> page, Integer idPortfolio);

  /**
   * 查询作品集下未绑定文章
   *
   *
   * @param page
   * @param idPortfolio
   * @param searchText
   * @param idUser
   * @return
   */
  IPage<ArticleDTO> selectUnbindArticles(Page<?> page, Integer idPortfolio, String searchText, Integer idUser);

  /**
   * 更新文章标签
   *
   * @param idArticle
   * @param tags
   * @return
   * @throws UnsupportedEncodingException
   * @throws BaseApiException
   */
  Result<?> updateTags(Integer idArticle, String tags)
      throws UnsupportedEncodingException, BaseApiException;

  /**
   * 更新文章优选状态
   *
   * @param idArticle
   * @param articlePerfect
   * @return
   */
  Result<?> updatePerfect(Integer idArticle, String articlePerfect);
}
