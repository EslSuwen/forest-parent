package cn.one2rich.forest.service;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.ArticleThumbsUp;
import com.baomidou.mybatisplus.extension.service.IService;

/** @author ronger */
public interface ArticleThumbsUpService extends IService<ArticleThumbsUp> {
  /**
   * 点赞
   *
   * @param articleThumbsUp
   * @throws BaseApiException
   * @return
   */
  Result<?> thumbsUp(ArticleThumbsUp articleThumbsUp) throws BaseApiException;
}
