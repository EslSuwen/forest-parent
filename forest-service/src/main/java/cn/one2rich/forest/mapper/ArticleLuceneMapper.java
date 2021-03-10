package cn.one2rich.forest.mapper;

import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.ArticleLucene;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ArticleLuceneMapper
 *
 * @author suwen
 * @date 2021/2/3 10:00
 */
public interface ArticleLuceneMapper extends BaseMapper<ArticleLucene> {

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
  List<ArticleDTO> getArticlesByIds(@Param("ids") String[] ids);

  /**
   * 加载文章内容
   *
   * @param id 文章id
   * @return
   */
  ArticleLucene getById(@Param("id") String id);
}
