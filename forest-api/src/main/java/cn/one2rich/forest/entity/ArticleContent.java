package cn.one2rich.forest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/** @author ronger */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@TableName("forest_article_content")
public class ArticleContent {

  @TableId(value = "id", type = IdType.INPUT)
  private Integer idArticle;

  private String articleContent;

  private String articleContentHtml;

  private Date createdTime;

  private Date updatedTime;
}
