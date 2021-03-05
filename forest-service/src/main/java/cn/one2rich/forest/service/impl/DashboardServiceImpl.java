package cn.one2rich.forest.service.impl;

import cn.one2rich.forest.dto.admin.Dashboard;
import cn.one2rich.forest.dto.admin.DashboardData;
import cn.one2rich.forest.dto.result.Result;
import cn.one2rich.forest.mapper.DashboardMapper;
import cn.one2rich.forest.service.DashboardService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

/**
 * @author ronger
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    @Resource
    private DashboardMapper dashboardMapper;

    @Override
    public Dashboard dashboard() {
        Dashboard dashboard = new Dashboard();
        dashboard.setCountUserNum(dashboardMapper.selectUserCount());
        dashboard.setNewUserNum(dashboardMapper.selectNewUserCount());
        dashboard.setCountArticleNum(dashboardMapper.selectArticleCount());
        dashboard.setNewArticleNum(dashboardMapper.selectNewArticleCount());
        dashboard.setCountViewNum(dashboardMapper.selectCountViewNum());
        dashboard.setTodayViewNum(dashboardMapper.selectTodayViewNum());
        return dashboard;
    }

    @Override
    public Result<Map<String, Object>> lastThirtyDaysData() {
        Map<String,Object> map = new HashMap<>(4);
        ArrayList<String> dates = new ArrayList<>(30);
        ArrayList<Integer> articleData = new ArrayList<>(30);
        ArrayList<Integer> userData = new ArrayList<>(30);
        ArrayList<Integer> visitData = new ArrayList<>(30);
        List<DashboardData> articles = dashboardMapper.selectLastThirtyDaysArticleData();
        List<DashboardData> users = dashboardMapper.selectLastThirtyDaysUserData();
        List<DashboardData> visits = dashboardMapper.selectLastThirtyDaysVisitData();
        LocalDate now = LocalDate.now().plusDays(1);
        LocalDate localDate = LocalDate.now().plusDays(-29);
        while (now.isAfter(localDate)) {
            String date = localDate.toString();
            dates.add(date);

            articles.forEach(article->{
                if (date.equals(article.getLabel())) {
                    articleData.add(article.getValue());
                    return;
                }
            });
            if (articleData.size() < dates.size()) {
                articleData.add(0);
            }

            users.forEach(user->{
                if (date.equals(user.getLabel())) {
                    userData.add(user.getValue());
                    return;
                }
            });
            if (userData.size() < dates.size()) {
                userData.add(0);
            }

            visits.forEach(visit->{
                if (date.equals(visit.getLabel())) {
                    visitData.add(visit.getValue());
                    return;
                }
            });
            if (visitData.size() < dates.size()) {
                visitData.add(0);
            }

            localDate = localDate.plusDays(1);
        }
        map.put("dates", dates);
        map.put("articles", articleData);
        map.put("users", userData);
        map.put("visits", visitData);
        return Result.OK(map);
    }

    @Override
    public Result<Map<String, Object>> history() {
        Map<String,Object> map = new HashMap<>(4);
        ArrayList<String> dates = new ArrayList<>(30);
        ArrayList<Integer> articleData = new ArrayList<>(30);
        ArrayList<Integer> userData = new ArrayList<>(30);
        ArrayList<Integer> visitData = new ArrayList<>(30);
        List<DashboardData> articles = dashboardMapper.selectHistoryArticleData();
        List<DashboardData> users = dashboardMapper.selectHistoryUserData();
        List<DashboardData> visits = dashboardMapper.selectHistoryVisitData();
        LocalDate now = LocalDate.now().plusMonths(1);
        LocalDate localDate = LocalDate.now().plusYears(-1).plusMonths(1);
        while (now.getYear() >= localDate.getYear()) {
            if (now.getYear() == localDate.getYear()) {
                if (now.getMonthValue() > localDate.getMonthValue()){
                    String date = localDate.getYear() + "-" + (localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue() : localDate.getMonthValue());
                    dates.add(date);

                    articles.forEach(article->{
                        if (date.equals(article.getLabel())) {
                            articleData.add(article.getValue());
                            return;
                        }
                    });
                    if (articleData.size() < dates.size()) {
                        articleData.add(0);
                    }

                    users.forEach(user->{
                        if (date.equals(user.getLabel())) {
                            userData.add(user.getValue());
                            return;
                        }
                    });
                    if (userData.size() < dates.size()) {
                        userData.add(0);
                    }

                    visits.forEach(visit->{
                        if (date.equals(visit.getLabel())) {
                            visitData.add(visit.getValue());
                            return;
                        }
                    });
                    if (visitData.size() < dates.size()) {
                        visitData.add(0);
                    }
                }
            } else {
                String date = localDate.getYear() + "-" + (localDate.getMonthValue() < 10 ? "0" + localDate.getMonthValue() : localDate.getMonthValue());
                dates.add(date);

                articles.forEach(article->{
                    if (date.equals(article.getLabel())) {
                        articleData.add(article.getValue());
                        return;
                    }
                });
                if (articleData.size() < dates.size()) {
                    articleData.add(0);
                }

                users.forEach(user->{
                    if (date.equals(user.getLabel())) {
                        userData.add(user.getValue());
                        return;
                    }
                });
                if (userData.size() < dates.size()) {
                    userData.add(0);
                }

                visits.forEach(visit->{
                    if (date.equals(visit.getLabel())) {
                        visitData.add(visit.getValue());
                        return;
                    }
                });
                if (visitData.size() < dates.size()) {
                    visitData.add(0);
                }
            }

            localDate = localDate.plusMonths(1);
        }
        map.put("dates", dates);
        map.put("articles", articleData);
        map.put("users", userData);
        map.put("visits", visitData);
        return Result.OK(map);
    }
}
