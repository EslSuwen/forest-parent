package cn.one2rich.forest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.one2rich.forest.entity.ArticleThumbsUp;
import org.apache.ibatis.annotations.Param;

/** @author ronger */
public interface ArticleThumbsUpMapper extends BaseMapper<ArticleThumbsUp> {
  /**
   * 更新文章点赞数
   *
   * @param idArticle
   * @param thumbsUpNumber
   * @return
   */
  Integer updateArticleThumbsUpNumber(
          @Param("idArticle") Integer idArticle, @Param("thumbsUpNumber") Integer thumbsUpNumber);
}
