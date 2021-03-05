package cn.one2rich.forest.util.log;

import cn.one2rich.forest.util.log.constant.LoggerConstant;
import cn.one2rich.forest.dto.TokenUser;
import cn.one2rich.forest.entity.Visit;
import cn.one2rich.forest.jwt.def.JwtConstants;
import cn.one2rich.forest.service.ArticleService;
import cn.one2rich.forest.service.VisitService;
import cn.one2rich.forest.util.UserUtils;
import cn.one2rich.forest.util.Utils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 浏览
 *
 * @author ronger
 */
@Aspect
@Component
public class VisitAspect {

  Logger logger = LoggerFactory.getLogger(VisitAspect.class);

  @Resource private ArticleService articleService;
  @Resource private VisitService visitService;

  @Pointcut("@annotation(cn.one2rich.forest.util.log.annotation.VisitLogger)")
  public void pointCut() {}

  /**
   * 保存系统操作日志
   *
   * @param joinPoint 连接点
   * @return 方法执行结果
   * @throws Throwable 调用出错
   */
  @AfterReturning(value = "pointCut()", returning = "obj")
  public void save(JoinPoint joinPoint, Object obj) {
    try {
      logger.info("保存访问记录 start ...");
      HttpServletRequest request =
          ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
      String ip = Utils.getIpAddress(request);
      String url = request.getRequestURL().toString();
      String ua = request.getHeader("user-agent");
      String referer = request.getHeader("Referer");
      referer = referer == null ? "null" : referer;
      Visit visit = new Visit();
      visit.setVisitUrl(url);
      visit.setVisitIp(ip);
      visit.setVisitUa(ua);
      visit.setVisitCity("");
      visit.setVisitDeviceId("");
      visit.setVisitRefererUrl(referer.split("token")[0]);
      visit.setCreatedTime(new Date());
      String authHeader = request.getHeader(JwtConstants.AUTHORIZATION);
      if (StringUtils.isNotBlank(authHeader)) {
        TokenUser tokenUser = UserUtils.getTokenUser(authHeader);
        visit.setVisitUserId(tokenUser.getIdUser());
      }
      visitService.save(visit);

      String methodName = joinPoint.getSignature().getName();
      Map params = getParams(request);
      if (params.isEmpty()) {
        params = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
      } else {
        params.putAll((Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
      }
      switch (methodName) {
        case LoggerConstant.ARTICLE:
          String param = String.valueOf(params.get("id"));
          if (StringUtils.isBlank(param) || "undefined".equals(param) || "null".equals(param)) {
            break;
          }
          Integer id = Integer.parseInt(param);
          articleService.incrementArticleViewCount(id);
          break;
        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    logger.info("保存访问记录 end ...");
  }

  private Map<String, String> getParams(HttpServletRequest request) {
    Map<String, String> paramsMap = new HashMap<>(10);
    Enumeration<String> paraNames = request.getParameterNames();
    while (paraNames.hasMoreElements()) {
      String key = paraNames.nextElement();
      if ("password".equals(key)) {
        continue;
      }
      paramsMap.put(key, request.getParameter(key));
    }
    return paramsMap;
  }
}
