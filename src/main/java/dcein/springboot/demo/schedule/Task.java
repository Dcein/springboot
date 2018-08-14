package dcein.springboot.demo.schedule;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 18:11 2018/8/14
 */
public class Task implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("任务...");
    }


}
