package cn.one2rich.forest.controller.topic;

import cn.one2rich.forest.dto.ArticleDTO;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.Topic;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.TopicService;
import cn.one2rich.forest.util.log.annotation.VisitLogger;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/** @author ronger */
@Api(value = "专题", tags = "专题")
@RestController
@RequestMapping("/api/topic")
public class TopicController {
  @Resource private ArticleService articleService;
  @Resource private TopicService topicService;

  @ApiOperation("导航栏专题获取")
  @GetMapping("/topic-nav")
  public Result<List<Topic>> topicNav() {
    return Result.OK(topicService.findTopicNav());
  }

  @ApiOperation("专题关联文章分页信息")
  @GetMapping("/{name}")
  @VisitLogger
  public Result<IPage<ArticleDTO>> articles(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer rows,
      @PathVariable String name) {
    IPage<ArticleDTO> list = articleService.findArticlesByTopicUri(new Page<>(page, rows), name);
    return Result.OK(list);
  }
}
