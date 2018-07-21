package dcein.springboot.demo.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Auther: DingCong
 * @Description: 配置拦截器
 * @@Date: Created in 16:11 2018/7/19
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    /**
     * 日志
     */
    private final Logger logger = LoggerFactory.getLogger(SpringMvcConfig.class);

    @Autowired
    private LoginInterceptor loginInterceptor;

    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("[InterceptorRegistry already fell into]");
        registry.addInterceptor(loginInterceptor).excludePathPatterns(Arrays.asList("/css/**","/fonts/**","/img/**","/js/**","/layui/**"));
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
