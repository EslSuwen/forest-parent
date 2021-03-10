package cn.one2rich.forest.service;

import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.ArticleLucene;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * LuceneService
 *
 * @author suwen
 * @date 2021/2/3 10:10
 */
public interface LuceneService extends IService<ArticleLucene> {

  /**
   * 将文章的数据解析为一个个关键字词存储到索引文件中
   *
   * @param list
   */
  void writeArticle(List<ArticleLucene> list);

  /**
   * 写入单个文章索引
   *
   * @param id
   */
  void writeArticle(String id);


  /**
   * 写入单个文章索引
   *
   * @param articleLucene
   */
  void writeArticle(ArticleLucene articleLucene);

  /**
   * 更新单个文章索引
   *
   * @param id
   */
  void updateArticle(String id);

  /**
   * 删除单个文章索引
   *
   * @param id
   */
  void deleteArticle(String id);

  /**
   * 关键词搜索
   *
   * @param value
   * @return
   * @throws Exception
   */
  List<ArticleLucene> searchArticle(String value);

  /**
   * 加载所有文章内容
   *
   * @return
   */
  List<ArticleLucene> getAllArticleLucene();

  /**
   * 加载所有文章内容
   *
   * @param ids 文章id(半角逗号分隔)
   * @return
   */
  List<ArticleDTO> getArticlesByIds(String[] ids);
}
