package dcein.springboot.demo.schedule;


import dcein.springboot.demo.mybatis.dao.TrainScheduleExceptionMapper;
import dcein.springboot.demo.mybatis.dao.TrainScheduleMapper;
import dcein.springboot.demo.mybatis.entity.TrainSchedule;
import dcein.springboot.demo.mybatis.entity.TrainScheduleException;
import dcein.springboot.demo.vo.monitor.ResolveScheduleCronExp;
import dcein.springboot.demo.vo.monitor.ScheduleAnnotationAttr;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronSequenceGenerator;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: DingCong
 * @Description: 定时任务监控类
 * 思路: step1.通过AOP前置通知,在定时任务触发前获取定时任务实际运行记录,以方法名和运行时间的形式存在DB中;(被监控)
 *       step2.timerTask方法为定期监控,以一定间隔时间去校验被监控记录表,看定时任务是否按照预期的时刻进行。(监控)
 *       方法具体实现为:
 *            1.获取当前系统时间,根据方法名和cron表达式获取该定时任务一天中执行的详细时刻信息;
 *            2.根据当前系统时间和详细时刻获取该定时任务小于且距离系统时间最近的一次时刻;
 *            3.由方法名和最近运行时刻去表中查询记录是否存在,存在则说明该定时任务无异常,反之,定时任务未正常运行。
 *       step3.通知
 * @@Date: Created in 10:50 2018/8/10
 */
@Component
@Aspect
public class ScheduleMonitor {

    protected static final Logger log = LoggerFactory.getLogger(ScheduleMonitor.class);

    @Resource
    private TrainScheduleMapper scheduleMapper;

    @Resource
    private TrainScheduleExceptionMapper scheduleExceptionMapper ;

    /**
     * 切入点:通知的连接点,获取所有带有@Scheduled方法
     */
    @Pointcut("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public void proxyAspect() {
    }

    /**
     * 通知类型:前置通知,在切入点方法执行前执行此方法
     * 功能描述：
     *     负责将定时任务在系统中实际运行的时间记录在数据库中
     */
    @Before("proxyAspect()")
    public void result(JoinPoint point) {

        //step1.获取当前时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        System.out.println("当前切入点所在方法:" + point.getSignature().getName() + ",执行时间:" + format);

        //step2.向数据库中添加实际运行时间
        TrainSchedule ts = new TrainSchedule();
        ts.setScheduleExecuteTime(format);
        ts.setScheduleName(point.getSignature().getName());
        scheduleMapper.insert(ts);
    }

    /**
     * 时间轮询任务：定时轮询查监控表
     * 功能描述：
     *     获取该任务理论上运行的时刻,根据理论时刻和定时任务的方法名去实际运行时刻表查询,数据不为空,说明定时任务正常运行,否则定时任务发生异常
     */
    public void timerTask(Long timeMills,HttpServletRequest request) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                //step1.获取当前系统时间和系统启动传进来的时间
                Long systemStartMills = timeMills;
                System.out.println("当前系统时间:" + systemStartMills);
                Date date = new Date();

                //step2.获取所有定时任务对象
                List<ScheduleAnnotationAttr> scheduleAnnotationValue = getScheduleAnnotationValue();

                //step3.遍历所有定时器对象,获取方法名和cron
                for (ScheduleAnnotationAttr resolve : scheduleAnnotationValue){

                    //获取最近的一次执行时间
                    ResolveScheduleCronExp resolveScheduleCronExp = resolveDayScheduleByCronExp(resolve.getExpression());
                    Date latestTime = getLatestTime(resolveScheduleCronExp.getDateList(), date);

                    //获取方法名
                    String methodName = resolve.getMethodName();

                    //封装查询entity对象
                    TrainSchedule trainSchedule = new TrainSchedule();
                    trainSchedule.setScheduleName(methodName);
                    trainSchedule.setScheduleExecuteTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestTime));

                    //查询DB
                    TrainSchedule scheduleResult = scheduleMapper.selectOne(trainSchedule);

                    //判断项目启动时间与当前时间的间隔是否大于定时任务出发间隔
                    if ((date.getTime() - systemStartMills) < resolveScheduleCronExp.getTimeGaps()){
                        log.info(resolve.getMethodName() + "定时任务,在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestTime) + "尚未达到触发运行时刻");
                        continue;
                    }

                    //判断查询结果,结果为空则该定时器未在正确时间内执行,不为空,为正常执行
                    if (scheduleResult == null){
                        log.info(resolve.getMethodName() + "定时任务,在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestTime) + "运行异常");
                        //将异常信息写入数据库
                        TrainScheduleException trainScheduleException = new TrainScheduleException();
                        trainScheduleException.setScheduleName(resolve.getMethodName());
                        trainScheduleException.setScheduleExceptionTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestTime));
                        scheduleExceptionMapper.insert(trainScheduleException);
                    }else{
                        log.info(resolve.getMethodName() + "定时任务,在" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(latestTime) + "运行正常");
                    }
                }
            }
        }, 5000, 10000);
    }

    /**
     * 获取所有带Schedule注解轮询任务方法,获取方法名和属性内容
     */
    public List<ScheduleAnnotationAttr> getScheduleAnnotationValue() {
        log.info("获取定时任务的所有方法,获取方法名和表达式属性内容");

        //step1.创建解析对象和相应对象集合
        List<ScheduleAnnotationAttr> scheduleList = new  ArrayList<ScheduleAnnotationAttr>();

        //step2.获取带有@Schedule的方法
        Class<SpringSchedule> clazz = SpringSchedule.class;
        Method[] declaredMethods = clazz.getDeclaredMethods();

        //step3.遍历所有方法,获取方法名和注解属性
        int i = 0;
        for (Method method : declaredMethods) {
            boolean isHasAnnotation = method.isAnnotationPresent(Scheduled.class);
            if (isHasAnnotation) {
                i++;
                Scheduled annotation = method.getAnnotation(Scheduled.class);
                ScheduleAnnotationAttr scheduleAnnotationAttr = new ScheduleAnnotationAttr();
                scheduleAnnotationAttr.setMethodName(method.getName());
                scheduleAnnotationAttr.setExpression(annotation.cron());
                scheduleList.add(scheduleAnnotationAttr);
            }
        }

        //step4.响应对象信息
        log.info("定时任务数量为:" + i);
        return scheduleList;
    }

    /**
     * 根据cron表达式获取一天执行的详细时刻表
     * @throws Exception
     */
    public  ResolveScheduleCronExp resolveDayScheduleByCronExp(String cronExpression){
        log.info("[根据cron表达式获取一天执行的详细时刻表]");

        //step1.设置解析日期时间格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");

        //step2.初始化日期时间对象
        Date nextTime = null;
        try {
            nextTime = df.parse(df2.format(new Date()) + " 00:00:00");
        } catch (ParseException e) {
            log.info("日期解析异常" , e);
        }
        Date date = new Date(nextTime.getTime() + 24 * 3600 * 1000);

        //step3.创建cron表达式解析器
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cronExpression);

        //step4.创建日期解析集合对象
        List<Date> resolvedDate = new ArrayList<>();

        //step5.从解析器获取一天中时间对象
        for (; nextTime.getTime() <= date.getTime() ;) {
            nextTime = cronSequenceGenerator.next(nextTime);
            if (nextTime.getTime() >= date.getTime()) break;
            resolvedDate.add(nextTime);
        }

        //step6.计算执行时间间隔
        long timeGaps = resolvedDate.get(1).getTime() - resolvedDate.get(0).getTime();

        //step7.返回时间对象序列
        ResolveScheduleCronExp resolveScheduleCronExp = new ResolveScheduleCronExp();
        resolveScheduleCronExp.setDateList(resolvedDate);
        resolveScheduleCronExp.setTimeGaps(timeGaps);
        return resolveScheduleCronExp;
    }

    /**
     * 根据入参时间集合和当前系统时间,从时间集合中求出最近任务运行的一次时间
     * @param currentDate
     * @param dateList
     * @return
     */
    public  Date getLatestTime(List<Date> dateList, Date currentDate) {
        //step1.判断参数的合法性
        if (dateList == null || dateList.size() <= 0) {
            return null;
        }

        //step2.判断参数的特殊性
        if (dateList.size() == 1) {
            return dateList.get(0);
        }

        //step3.对传入集合进行升序排序
        dateList = new ArrayList<Date>(dateList);
        Collections.sort(dateList);

        //step4.计算集合时间大于系统时间时,获取集合前一个时间即为最为接近且小于当前系统的时间
        Date res = new Date();
        for (int i = 0; i < dateList.size(); i++) {
            if ((currentDate.getTime() - dateList.get(i).getTime()) < 0) {
                res = dateList.get(i - 1);
                break;
            }
        }

        //step5.返回所求结果
        return res;
    }

    /**
     * 测试
     */
    public static void main(String args[]) throws Exception {


//        getScheduleAnnotationValue();
    }


}

