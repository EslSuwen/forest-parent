package cn.one2rich.forest.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/** @author ronger */
@Data
public class ArticleDTO {
  private Integer idArticle;
  /** 文章标题 */
  private String articleTitle;
  /** 文章缩略图 */
  private String articleThumbnailUrl;
  /** 文章作者id */
  private Integer articleAuthorId;
  /** 文章作者 */
  private String articleAuthorName;
  /** 文章作者头像 */
  private String articleAuthorAvatarUrl;
  /** 文章类型 */
  private String articleType;
  /** 文章标签 */
  private String articleTags;
  /** 浏览总数 */
  private Integer articleViewCount;
  /** 预览内容 */
  private String articlePreviewContent;
  /** 文章内容 */
  private String articleContent;
  /** 文章内容html */
  private String articleContentHtml;
  /** 评论总数 */
  private Integer articleCommentCount;
  /** 过去时长 */
  private String timeAgo;
  /** 文章永久链接 */
  private String articlePermalink;
  /** 站内链接 */
  private String articleLink;
  /** 文章状态 */
  private String articleStatus;
  /** 更新时间 */
  private Date updatedTime;

  private Author articleAuthor;

  private List<ArticleTagDTO> tags;

  private List<PortfolioArticleDTO> portfolios;

  private Integer sortNo;
  /** 0:非优选1：优选;0 */
  private String articlePerfect;
  /** 点赞总数 */
  private Integer articleThumbsUpCount;
  /** 赞赏总数 */
  private Integer articleSponsorCount;
}
