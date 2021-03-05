package cn.one2rich.forest.service.impl;

import cn.one2rich.forest.core.exception.BaseApiException;
import cn.one2rich.forest.dto.Author;
import cn.one2rich.forest.dto.PortfolioArticleDTO;
import cn.one2rich.forest.dto.PortfolioDTO;
import cn.one2rich.forest.dto.UserDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Portfolio;
import cn.one2rich.forest.entity.User;
import cn.one2rich.forest.mapper.PortfolioMapper;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.PortfolioService;
import cn.one2rich.forest.service.UserService;
import cn.one2rich.forest.util.UserUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/** @author ronger */
@Service
public class PortfolioServiceImpl extends ServiceImpl<PortfolioMapper, Portfolio>
    implements PortfolioService {

  @Resource private PortfolioMapper portfolioMapper;
  @Resource private UserService userService;
  @Resource private ArticleService articleService;

  @Override
  public IPage<PortfolioDTO> findUserPortfoliosByUser(Page<?> page, UserDTO userDTO) {
    IPage<PortfolioDTO> list =
        portfolioMapper.selectUserPortfoliosByIdUser(page, userDTO.getIdUser());
    Author author = new Author();
    author.setIdUser(userDTO.getIdUser());
    author.setUserAvatarURL(userDTO.getAvatarUrl());
    author.setUserNickname(userDTO.getNickname());
    list.getRecords().forEach(portfolioDTO -> genPortfolioAuthor(portfolioDTO, author));
    return list;
  }

  @Override
  public PortfolioDTO findPortfolioDTOById(Integer idPortfolio, Integer type) {
    PortfolioDTO portfolio = portfolioMapper.selectPortfolioDTOById(idPortfolio, type);
    if (portfolio == null) {
      return new PortfolioDTO();
    }
    Author author = userService.selectAuthor(portfolio.getPortfolioAuthorId());
    genPortfolioAuthor(portfolio, author);
    Integer articleNumber = portfolioMapper.selectCountArticleNumber(portfolio.getIdPortfolio());
    portfolio.setArticleNumber(articleNumber);
    return portfolio;
  }

  @Override
  public Portfolio postPortfolio(Portfolio portfolio) throws BaseApiException {
    /*    User user = UserUtils.getCurrentUserByToken();
    if (StringUtils.isNotBlank(portfolio.getHeadImgType())) {
      String headImgUrl = UploadController.uploadBase64File(portfolio.getHeadImgUrl(), 0);
      portfolio.setHeadImgUrl(headImgUrl);
    }
    if (portfolio.getIdPortfolio() == null || portfolio.getIdPortfolio() == 0) {
      portfolio.setPortfolioAuthorId(user.getIdUser());
      portfolio.setCreatedTime(new Date());
      portfolio.setUpdatedTime(portfolio.getCreatedTime());
      save(portfolio);
    } else {
      portfolio.setUpdatedTime(new Date());
      updateById(portfolio);
    }
    return portfolio;*/
    throw new BaseApiException("未实现接口");
  }

  @Override
  public Result<?> findUnbindArticles(Page<?> page, String searchText, Integer idPortfolio)
      throws BaseApiException {
    User user = UserUtils.getCurrentUserByToken();
    Portfolio portfolio = getById(idPortfolio);
    if (portfolio == null) {
      return Result.error("该作品集不存在或已被删除!");
    } else {
      if (!user.getIdUser().equals(portfolio.getPortfolioAuthorId())) {
        return Result.error("非法操作!");
      } else {
        return Result.OK(
            articleService.selectUnbindArticles(page, idPortfolio, searchText, user.getIdUser()));
      }
    }
  }

  @Override
  public Result<?> bindArticle(PortfolioArticleDTO portfolioArticle) {
    Integer count =
        portfolioMapper.selectCountPortfolioArticle(
            portfolioArticle.getIdArticle(), portfolioArticle.getIdPortfolio());
    if (count.equals(0)) {
      Integer maxSortNo = portfolioMapper.selectMaxSortNo(portfolioArticle.getIdPortfolio());
      portfolioMapper.insertPortfolioArticle(
          portfolioArticle.getIdArticle(), portfolioArticle.getIdPortfolio(), maxSortNo);
      return Result.OK();
    } else {
      return Result.error("该文章已经在作品集下!!");
    }
  }

  @Override
  public Result<?> updateArticleSortNo(PortfolioArticleDTO portfolioArticle) {
    if (portfolioArticle.getIdPortfolio() == null || portfolioArticle.getIdPortfolio().equals(0)) {
      return Result.error("作品集数据异常!");
    }
    if (portfolioArticle.getIdArticle() == null || portfolioArticle.getIdArticle().equals(0)) {
      return Result.error("文章数据异常!");
    }
    if (portfolioArticle.getSortNo() == null) {
      return Result.error("排序号不能为空!");
    }
    Integer result =
        portfolioMapper.updateArticleSortNo(
            portfolioArticle.getIdPortfolio(),
            portfolioArticle.getIdArticle(),
            portfolioArticle.getSortNo());
    return result > 0 ? Result.OK() : Result.error("更新失败!");
  }

  @Override
  public Result<?> unbindArticle(Integer idPortfolio, Integer idArticle) {
    if (idPortfolio == null || idPortfolio.equals(0)) {
      return Result.error("作品集数据异常!");
    }
    if (idArticle == null || idArticle.equals(0)) {
      return Result.error("文章数据异常!");
    }
    Integer result = portfolioMapper.unbindArticle(idPortfolio, idArticle);
    return result > 0 ? Result.OK() : Result.error("操作失败!");
  }

  @Override
  public Result<?> deletePortfolio(Integer idPortfolio) {
    if (idPortfolio == null || idPortfolio.equals(0)) {
      return Result.error("作品集数据异常!");
    }
    Integer articleNumber = portfolioMapper.selectCountArticleNumber(idPortfolio);
    if (articleNumber > 0) {
      return Result.error("该作品集已绑定文章不允许删除!");
    } else {
      return removeById(idPortfolio) ? Result.OK() : Result.error("操作失败!");
    }
  }

  private PortfolioDTO genPortfolioAuthor(PortfolioDTO portfolioDTO, Author author) {
    portfolioDTO.setPortfolioAuthorAvatarUrl(author.getUserAvatarURL());
    portfolioDTO.setPortfolioAuthorName(author.getUserNickname());
    portfolioDTO.setPortfolioAuthorId(author.getIdUser());
    portfolioDTO.setPortfolioAuthor(author);
    return portfolioDTO;
  }
}
