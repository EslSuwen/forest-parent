package cn.one2rich.forest.controller.lucene;

import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.entity.UserDic;
import cn.one2rich.forest.service.UserDicService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * UserDicController
 *
 * @author suwen
 * @date 2021/2/4 09:29
 */
@Api(value = "用户词典", tags = "用户词典")
@RestController
@RequestMapping("/api/lucene/dic")
public class UserDicController {

  @Resource private UserDicService dicService;

  @ApiOperation("用户词典分页信息")
  @GetMapping("/getAll")
  public Result<IPage<UserDic>> getAll(
      @RequestParam(defaultValue = "0") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize) {
    return Result.OK(dicService.page(new Page<>(pageNum, pageSize)));
  }

  @ApiOperation("用户词典增加")
  @PostMapping("/addDic/{dic}")
  public Result<?> addDic(@PathVariable String dic) {
    dicService.addDic(dic);
    return Result.OK();
  }

  @ApiOperation("用户词典编辑")
  @PutMapping("/editDic")
  public Result<?> getAllDic(@RequestBody UserDic dic) {
    dicService.updateDic(dic);
    return Result.OK();
  }

  @ApiOperation("用户词典删除")
  @DeleteMapping("/deleteDic/{id}")
  public Result<?> deleteDic(@PathVariable String id) {
    dicService.deleteDic(id);
    return Result.OK();
  }
}
