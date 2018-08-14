package dcein.springboot.demo.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 16:25 2018/8/13
 */
@Component
@Slf4j
public class SpringSchedule {


    @Scheduled(cron = "0 * * * * ? ")
    public void testSchedule(){
        System.out.println("test任务正在执行...");
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void schedulelLive(){
        System.out.println("Live任务正在执行...");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void schedulelTim(){
        System.out.println("Tim任务正在执行...");
    }

    @Scheduled(cron = "0/7 * * * * ?")
    public void schedulelError(){
        System.out.println("Error任务正在执行...");
    }

    @Scheduled(cron = "0 * * * * ? ")
    public void ScheduleJim(){
        System.out.println("Jim任务正在执行...");
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void schedulelFang(){
        System.out.println("Fang任务正在执行...");
    }

    @Scheduled(cron = "0 0/5 * * * ?")
    public void schedulelJerry(){
        System.out.println("Jerry任务正在执行...");
    }

    @Scheduled(cron = "0/7 * * * * ?")
    public void schedulelBoss(){
        System.out.println("Boss任务正在执行...");
    }



}
