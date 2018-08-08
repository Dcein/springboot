package dcein.springboot.demo.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;

/**
 * @Auther: DingCong
 * @Description: 自定义starter启动项
 * @@Date: Created in 17:50 2018/7/26
 */
@Configuration
@Slf4j
@ConditionalOnClass({Slf4j.class})
public class SystemStartupLoad {

    public void getMessage(){
        System.out.println("========启动=======");
    }

}
