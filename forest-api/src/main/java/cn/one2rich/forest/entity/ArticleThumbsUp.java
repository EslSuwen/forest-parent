package cn.one2rich.forest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/** @author ronger */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("forest_article_thumbs_up")
public class ArticleThumbsUp implements Serializable, Cloneable {
  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer idArticleThumbsUp;
  /** 文章表主键 */
  private Integer idArticle;
  /** 用户表主键 */
  private Integer idUser;
  /** 点赞时间 */
  private Date thumbsUpTime;
}
