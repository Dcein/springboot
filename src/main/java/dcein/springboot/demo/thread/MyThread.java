package dcein.springboot.demo.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * @Auther: DingCong
 * @Description:
 * @@Date: Created in 15:38 2018/5/4
 */
@Slf4j
public class MyThread extends Thread{

    public void run(){
        for(int i = 0;i<50;i++) {
            log.info("线程:" + Thread.currentThread().getName() + "正在运行...");
        }
    }
}
