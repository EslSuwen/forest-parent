package cn.one2rich.forest.dto;

import lombok.Data;

import java.util.List;

/** @author ronger */
@Data
public class PortfolioArticleDTO {

  private Integer id;

  private Integer idPortfolio;

  private Integer idArticle;

  private String headImgUrl;

  private String portfolioTitle;

  private Integer sortNo;

  private List<ArticleDTO> articles;
}
