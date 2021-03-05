package cn.one2rich.forest.controller.portfolio;

import cn.one2rich.forest.core.exception.BaseApiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.PortfolioArticleDTO;
import cn.one2rich.forest.dto.PortfolioDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Portfolio;
import cn.one2rich.forest.service.PortfolioService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@RestController
@RequestMapping("/api/portfolio")
public class PortfolioController {

  @Resource private PortfolioService portfolioService;

  @GetMapping("/detail/{id}")
  public Result<PortfolioDTO> detail(
      @PathVariable Integer id, @RequestParam(defaultValue = "0") Integer type) {
    return Result.OK(portfolioService.findPortfolioDTOById(id, type));
  }

  @PostMapping("/post")
  public Result<Portfolio> add(@RequestBody Portfolio portfolio) throws BaseApiException {
    return Result.OK(portfolioService.postPortfolio(portfolio));
  }

  @PutMapping("/post")
  public Result<Portfolio> update(@RequestBody Portfolio portfolio) throws BaseApiException {
    return Result.OK(portfolioService.postPortfolio(portfolio));
  }

  @GetMapping("/{id}/unbind-articles")
  public Result<?> unbindArticles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @RequestParam(defaultValue = "") String searchText,
      @PathVariable Integer id)
      throws BaseApiException {
    return portfolioService.findUnbindArticles(new Page<>(page, rows), searchText, id);
  }

  @PostMapping("/bind-article")
  public Result<?> bindArticle(@RequestBody PortfolioArticleDTO portfolioArticle) {
    return portfolioService.bindArticle(portfolioArticle);
  }

  @PutMapping("/update-article-sort-no")
  public Result<?> updateArticleSortNo(@RequestBody PortfolioArticleDTO portfolioArticle) {
    return portfolioService.updateArticleSortNo(portfolioArticle);
  }

  @DeleteMapping("/unbind-article")
  public Result<?> unbindArticle(Integer idArticle, Integer idPortfolio) {
    return portfolioService.unbindArticle(idPortfolio, idArticle);
  }

  @DeleteMapping("/delete")
  public Result<?> delete(Integer idPortfolio) {
    return portfolioService.deletePortfolio(idPortfolio);
  }
}
