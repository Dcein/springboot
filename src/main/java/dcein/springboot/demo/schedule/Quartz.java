package dcein.springboot.demo.schedule;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 18:11 2018/8/14
 */
public class Quartz {

    public static void main(String[] args) throws Exception {
        Quartz example = new Quartz();
        example.run();
    }

    public void run() throws Exception {
        //日期格式化
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");

        //从调度程序工厂获取一个调度程序的实例
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();

        System.out.println();
        System.out.println("--------------- 初始化 -------------------");


        /** 重要:
         *  定义一个job1，并绑定到我们自定义的Task的class对象
         *  这里并不会马上创建一个Task实例，实例创建是在scheduler安排任务触发执行时创建的
         *  这种机制也为后面使用Spring集成提供了便利
         */
        JobDetail job = newJob(Task.class).withIdentity("job1", "group1").build();

        //声明一个触发器，现在就执行(schedule.start()方法开始调用的时候执行)；并且每间隔2秒就执行一次
        CronTrigger trigger = newTrigger().withIdentity("trigger1", "group1").withSchedule(cronSchedule("0/2 * * * * ?")).build();

        //告诉quartz使用定义的触发器trigger安排执行任务job
        Date ft = sched.scheduleJob(job, trigger);

        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义:每20s运行一次)");


        sched.start();


      /*  // job2 偶数分钟的第15秒运行
        job = newJob(Task.class).withIdentity("job2", "group1").build();
        trigger = newTrigger().withIdentity("trigger2", "group1").withSchedule(cronSchedule("15 0/2 * * * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义:偶数分钟的第15秒运行)");

        // job3 从8时到17时 ,每个 偶数分钟执行一次
        job = newJob(Task.class).withIdentity("job3", "group1").build();

        trigger = newTrigger().withIdentity("trigger3", "group1")
                .withSchedule(cronSchedule("0 0/2 8-17 * * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义:从8时到17时 ,每个 偶数分钟执行一次)");

        // job4 从17时到23时,每3分钟运行一次
        job = newJob(Task.class).withIdentity("job4", "group1").build();

        trigger = newTrigger().withIdentity("trigger4", "group1")
                .withSchedule(cronSchedule("0 0/3 17-23 * * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义: 从17时到23时,每3分钟运行一次)");

        // job5 每个月的1号和15号的上午10点 运行
        job = newJob(Task.class).withIdentity("job5", "group1").build();

        trigger = newTrigger().withIdentity("trigger5", "group1")
                .withSchedule(cronSchedule("0 0 10am 1,15 * ?")).build();

        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义:每个月的1号和15号的上午10点 运行)");

        // job6 周一至周五,每30秒运行一次
        job = newJob(Task.class).withIdentity("job6", "group1").build();

        trigger = newTrigger().withIdentity("trigger6", "group1")
                .withSchedule(cronSchedule("0,30 * * ? * MON-FRI")).build();

        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义:周一至周五,每30秒运行一次 )");

        // job7 周六,周日 每30秒运行
        job = newJob(Task.class).withIdentity("job7", "group1").build();

        trigger = newTrigger().withIdentity("trigger7", "group1")
                .withSchedule(cronSchedule("0,30 * * ? * SAT,SUN")).build();

        ft = sched.scheduleJob(job, trigger);
        System.out.println(job.getKey().getName() + " 将在: "
                + dateFormat.format(ft) + " 运行 . 并且基于Cron表达式 : "
                + trigger.getCronExpression() + "  (含义:周六,周日  每30秒运行 )");

        sched.start();

        System.out.println("------- 开始调度 (调用.start()方法) ----------------");
        System.out.println("------- 等待5分钟,给任务的调度留出时间  ... ------------");
        try {
            Thread.sleep(300L * 1000L);
        } catch (Exception e) {
        }

        sched.shutdown(true);
        System.out.println("------- 调度已关闭 ---------------------");

        // 显示一下 已经执行的任务信息
        SchedulerMetaData metaData = sched.getMetaData();
        System.out.println("~~~~~~~~~~  执行了 "
                + metaData.getNumberOfJobsExecuted() + " 个 jobs.");*/
    }
}

