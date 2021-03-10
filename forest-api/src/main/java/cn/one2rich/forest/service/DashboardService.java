package cn.one2rich.forest.service;

import cn.one2rich.forest.dto.admin.Dashboard;
import cn.one2rich.forest.dto.result.Result;

import java.util.Map;

/** @author ronger */
public interface DashboardService {

  /**
   * 统计系统数据
   *
   * @return
   */
  Dashboard dashboard();

  /**
   * 统计最近三十天数据
   *
   * @return
   */
  Result<Map<String, Object>> lastThirtyDaysData();

  /**
   * 获取历史数据
   *
   * @return
   */
  Result<Map<String,Object>> history();
}
