package dcein.springboot.demo.listener;

import dcein.springboot.demo.schedule.ScheduleMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: DingCong
 * @Description: spring容器初始化监听器
 * @@Date: Created in 9:28 2018/8/14
 */
@Component
@Slf4j
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ScheduleMonitor bean = event.getApplicationContext().getBean(ScheduleMonitor.class);
        HttpServletRequest httpServletRequest = (HttpServletRequest)event.getApplicationContext().getBean("HttpServletRequest");
        Long timeMills = new Long(System.currentTimeMillis());
        bean.timerTask(timeMills,httpServletRequest);
        log.info("[==========SpringBoot容器已经启动,开启定时任务监控,10s/one times======]");
    }
}
