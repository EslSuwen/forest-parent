package cn.one2rich.forest.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.PortfolioDTO;
import cn.one2rich.forest.entity.Portfolio;
import org.apache.ibatis.annotations.Param;

/** @author ronger */
public interface PortfolioMapper extends BaseMapper<Portfolio> {
  /**
   * 查询用户作品集
   *
   * @param idUser
   * @return
   */
  IPage<PortfolioDTO> selectUserPortfoliosByIdUser(Page<?> page, @Param("idUser") Integer idUser);

  /**
   * 查询作品集
   *
   * @param id
   * @param type
   * @return
   */
  PortfolioDTO selectPortfolioDTOById(@Param("id") Integer id, @Param("type") Integer type);

  /**
   * 统计作品集下文章数
   *
   * @param idPortfolio
   * @return
   */
  Integer selectCountArticleNumber(@Param("idPortfolio") Integer idPortfolio);

  /**
   * 查询文章是否已绑定
   *
   * @param idArticle
   * @param idPortfolio
   * @return
   */
  Integer selectCountPortfolioArticle(
          @Param("idArticle") Integer idArticle, @Param("idPortfolio") Integer idPortfolio);

  /**
   * 插入文章与作品集绑定数据
   *
   * @param idArticle
   * @param idPortfolio
   * @param maxSortNo
   * @return
   */
  Integer insertPortfolioArticle(
          @Param("idArticle") Integer idArticle,
          @Param("idPortfolio") Integer idPortfolio,
          @Param("maxSortNo") Integer maxSortNo);

  /**
   * 查询作品集下最大排序号
   *
   * @param idPortfolio
   * @return
   */
  Integer selectMaxSortNo(@Param("idPortfolio") Integer idPortfolio);

  /**
   * 更新文章排序号
   *
   * @param idPortfolio
   * @param idArticle
   * @param sortNo
   * @return
   */
  Integer updateArticleSortNo(
          @Param("idPortfolio") Integer idPortfolio,
          @Param("idArticle") Integer idArticle,
          @Param("sortNo") Integer sortNo);

  /**
   * 取消绑定文章
   *
   * @param idPortfolio
   * @param idArticle
   * @return
   */
  Integer unbindArticle(
          @Param("idPortfolio") Integer idPortfolio, @Param("idArticle") Integer idArticle);
}
