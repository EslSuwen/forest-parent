package cn.one2rich.forest.controller.common;

import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.ArticleSearchDTO;
import cn.one2rich.forest.dto.PortfolioDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.PortfolioService;
import cn.one2rich.forest.util.log.annotation.VisitLogger;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/** @author ronger */
@Api(value = "通用", tags = "通用")
@RestController
@RequestMapping("/api/console")
public class CommonApiController {

  @Resource private ArticleService articleService;
  @Resource private PortfolioService portfolioService;

  @ApiOperation("文章查看分页信息")
  @GetMapping("/articles")
  @VisitLogger
  public Result<IPage<ArticleDTO>> articles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      ArticleSearchDTO searchDTO) {
    IPage<ArticleDTO> list = articleService.findArticles(new Page<>(page, rows), searchDTO);
    return Result.OK(list);
  }

  @ApiOperation("文章查看详请信息")
  @GetMapping("/article/{id}")
  @VisitLogger
  public Result<?> article(@PathVariable Integer id) {
    return Result.OK(articleService.findArticleDTOById(id, 1));
  }

  @GetMapping("/portfolio/{id}")
  @VisitLogger
  public Result<PortfolioDTO> portfolio(@PathVariable Integer id) {
    return Result.OK(portfolioService.findPortfolioDTOById(id, 1));
  }

  @GetMapping("/portfolio/{id}/articles")
  public Result<IPage<ArticleDTO>> articles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @PathVariable Integer id) {
    return Result.OK(articleService.findArticlesByIdPortfolio(new Page<>(page, rows), id));
  }
}
