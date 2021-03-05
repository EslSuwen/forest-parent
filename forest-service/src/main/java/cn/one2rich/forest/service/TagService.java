package cn.one2rich.forest.service;

import cn.one2rich.forest.core.exception.BaseApiException;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.one2rich.forest.dto.LabelModel;
import cn.one2rich.forest.entity.Article;
import cn.one2rich.forest.entity.Tag;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

/** @author ronger */
public interface TagService extends IService<Tag> {

  /**
   * 保存文章标签
   *
   * @param article
   * @param articleContentHtml
   * @throws UnsupportedEncodingException
   * @throws BaseApiException
   * @return
   */
  Integer saveTagArticle(Article article, String articleContentHtml)
      throws UnsupportedEncodingException, BaseApiException;

  /**
   * 清除未使用标签
   *
   * @return
   */
  Map cleanUnusedTag();

  /**
   * 添加/更新标签
   *
   * @param tag
   * @return
   */
  Map saveTag(Tag tag);

  /**
   * 获取标签列表
   *
   * @return
   */
  List<LabelModel> findTagLabels();
}
