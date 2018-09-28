package dcein.springboot.demo.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 15:34 2018/5/4
 */
@Slf4j
public class TestThread {
    
    public ThreadPool threadPool = new ThreadPool();

    @Test
    public void test(){
        log.info("测试单线程");
        //step1.创建线程池
        ExecutorService singleThreadPool = threadPool.getSingleThreadPool();

        //step2.创建线程
        /*Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();*/

        //step3.将线程扔入线程池
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<200;i++) {
                    log.info("订单通知" + i);
                }
            }
        });
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    log.info("机构通知"+i);
                }
            }
        });
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    log.info("商户通知"+i);
                }
            }
        });
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    log.info("平台通知"+i);
                }
            }
        });
    }

    @Test
    public void test2(){
        log.info("测试固定大小的线程");
        //step1.创建线程池
        ExecutorService singleThreadPool = threadPool.getFixedThreadPool(3);

        //step2.创建线程
        /*Thread t1 = new MyThread();
        Thread t2 = new MyThread();
        Thread t3 = new MyThread();
        Thread t4 = new MyThread();*/

        //step3.将线程扔入线程池
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<200;i++) {
                    log.info("订单通知" + i);
                }
            }
        });
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    log.info("机构通知"+i);
                }
            }
        });
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    log.info("商户通知"+i);
                }
            }
        });
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    log.info("平台通知"+i);
                }
            }
        });
    }


    @Test
    public void  getTest() {
       Map<String,String> map = new HashMap<String,String>();
       map.put("a","a");
       map.put("a","b");
        System.out.println(map.get("a"));
    }
}
