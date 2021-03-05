package cn.one2rich.forest.controller.admin;

import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/** @author ronger */
@RestController
@RequestMapping("/api/admin/dashboard")
public class DashboardController {

  @Resource private DashboardService dashboardService;

  @GetMapping
  public Result<?> dashboard() {
    return Result.OK(dashboardService.dashboard());
  }

  @GetMapping("/last-thirty-days")
  public Result<Map<String, Object>> LastThirtyDaysData() {
    return dashboardService.lastThirtyDaysData();
  }

  @GetMapping("/history")
  public Result<Map<String, Object>> history() {
    return dashboardService.history();
  }
}
