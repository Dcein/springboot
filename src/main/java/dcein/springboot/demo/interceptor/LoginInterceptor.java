package dcein.springboot.demo.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.omg.PortableInterceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Auther: DingCong
 * @Description: 登陆拦截器
 * @@Date: Created in 10:22 2018/7/19
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("会话token:" + request.getSession().getAttribute("userToken"));

        //step1.获取方法上的登陆注解
        HandlerMethod handlerMethod = (HandlerMethod)handler ;
        Method method = handlerMethod.getMethod();
        LoginRequired annotation = method.getAnnotation(LoginRequired.class);

        //step2.判断登陆注解是否为空
        if (null == annotation){
            log.info("[the method annotation is null , return true]");
            return true ;
        }

        //step3.注解不为空,获取登陆地址
        log.info("[the method annotation is not null , begin interceptor ...]");
        String token = (String)request.getSession().getAttribute("token");
        if (StringUtils.isBlank(token)){
            response.sendRedirect("/login");
            return false ;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }




}
