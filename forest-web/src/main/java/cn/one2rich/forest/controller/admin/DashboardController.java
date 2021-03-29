package cn.one2rich.forest.controller.admin;

import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.service.DashboardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/** @author ronger */
@Api(value = "管理员面板信息", tags = "管理员面板信息")
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

  @Resource private DashboardService dashboardService;

  @ApiOperation("获取管理员面板信息")
  @GetMapping
  public Result<?> dashboard() {
    return Result.OK(dashboardService.dashboard());
  }

  @ApiOperation("近30天信息")
  @GetMapping("/last-thirty-days")
  public Result<Map<String, Object>> lastThirtyDaysData() {
    return dashboardService.lastThirtyDaysData();
  }

  @ApiOperation("历史信息")
  @GetMapping("/history")
  public Result<Map<String, Object>> history() {
    return dashboardService.history();
  }
}
