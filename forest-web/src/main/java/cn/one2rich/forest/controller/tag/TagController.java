package cn.one2rich.forest.controller.tag;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** @author ronger */
@Api(value = "标签", tags = "标签")
@RestController
@RequestMapping("/api/tag")
public class TagController {

  @Resource private ArticleService articleService;
  @Resource private TagService tagService;

  @ApiOperation("标签关联文章分页信息")
  @GetMapping("/{name}")
  public Result<List<ArticleDTO>> articles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @PathVariable String name) {
    List<ArticleDTO> list = articleService.findArticlesByTagName(new Page<>(page, rows), name);
    return Result.OK(list);
  }

  @ApiOperation("标签名列表信息")
  @GetMapping("/tags")
  public Result<?> tags() {
    return Result.OK(tagService.findTagLabels());
  }
}
