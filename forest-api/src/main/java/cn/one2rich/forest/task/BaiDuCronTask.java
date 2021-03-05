package cn.one2rich.forest.task;

import cn.one2rich.forest.core.constant.ProjectConstant;
import cn.one2rich.forest.util.BaiDuUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author ronger
 */
@Component
@Slf4j
public class BaiDuCronTask {

    @Value("${resource.domainhttp://10.26.201.17:32240/static}")
    private String domain;
    @Value(("${env:dev}"))
    private String env;

    /**
     *  定时推送首页更新
     * */
    @Scheduled(cron = "0 0 10,14,18 * * ?")
    public void pushHome() {
        if (!ProjectConstant.ENV.equals(env)) {
            BaiDuUtils.sendUpdateSEOData(domain);
        }
    }

}
