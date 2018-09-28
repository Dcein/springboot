package dcein.springboot.demo.thread;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @Auther: DingCong
 * @Description: 多线程
 * @@Date: Created in 15:11 2018/5/4
 */
@Slf4j
public class ThreadPool {

    /**
     * 创建一个单线程的线程池。
     * 这个线程池只有一个线程在工作，也就是相当于单线程串行执行所有任务。
     * 如果这个唯一的线程因为异常结束，那么会有一个新的线程来替代它。此线程池保证所有任务的执行顺序按照任务的提交顺序执行。
     */
    public ExecutorService getSingleThreadPool(){
        log.info("创建一个单线程池");
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        return executorService;
    }

    /**
     * 创建固定大小的线程池。
     * 每次提交一个任务就创建一个线程，直到线程达到线程池的最大限量。
     * 线程池的大小一旦达到最大值就会保持不变，如果某个线程因为执行异常而结束，那么线程池会补充一个新线程。
     */
    public ExecutorService getFixedThreadPool(int nThreads){
        log.info("创建一个固定大小的线程池");
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        return executorService;
    }

    /**
     * 创建一个可缓存的线程池。
     * 如果线程池的大小超过了处理任务所需要的线程，那么就会回收部分空闲（60秒不执行任务）的线程，当任务数增加时，此线程池又可以智能的添加新线程来处理任务。
     * 此线程池不会对线程池大小做限制，线程池大小完全依赖于操作系统（或者说JVM）能够创建的最大线程大小。
     */
    public ExecutorService getCacheTreadPool(){
        log.info("创建一个可缓存的线程池");
        ExecutorService executorService = Executors.newCachedThreadPool();
        return executorService;
    }

    /**
     * 创建一个大小无限的线程池。
     * 此线程池支持定时以及周期性执行任务的需求。
     */
    public ScheduledExecutorService getScheduledThreadPool(int corePoolSize){
        log.info("创建一个大小无限的线程池");
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(corePoolSize);
        return scheduledExecutorService;
    }

}
