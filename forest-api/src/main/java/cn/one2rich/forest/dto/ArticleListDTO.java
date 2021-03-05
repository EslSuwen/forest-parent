package cn.one2rich.forest.dto;

import cn.one2rich.forest.entity.Article;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * AnswerListDTO
 *
 * @author suwen
 * @date 2021/2/18 09:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ArticleListDTO extends Article {

  private String nickname;

}
