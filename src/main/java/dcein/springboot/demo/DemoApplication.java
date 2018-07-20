package dcein.springboot.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 此处一定要加上mybatis的扫描包，在容器初始化的时扫描接口
 */
@SpringBootApplication
@ServletComponentScan
@MapperScan(basePackages = "dcein.springboot.demo.mybatis.dao")
public class DemoApplication {

    /**
     * RestTemplate调用模板注入,这个地方需要制定bean,要不引用模板报null
     * @return
     */
    @Bean("serverTemplate")
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

