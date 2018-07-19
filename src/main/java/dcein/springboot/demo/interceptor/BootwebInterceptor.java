package dcein.springboot.demo.interceptor;


import org.springframework.web.servlet.ModelAndView;
import dcein.springboot.demo.constants.BootConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: DingCong
 * @Description: 定义拦截器
 * @@Date: Created in 13:42 2018/5/16
 */
@Slf4j
public class BootwebInterceptor extends HandlerInterceptorAdapter{

    /**
     * 执行方法之前进行拦截
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进入拦截...");
        String username= request.getParameter("username");
        String pwd = request.getParameter("password");
        log.info("username:"+username+",pwd:"+pwd);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(pwd)) {
            log.info("密码或用户名为空,跳转至登陆页面");
            /**
             * 将错误信息打印页面
             */
            response.getWriter().print("error");
            return false;
        }
        if(!username.equals(BootConstants.USERNAME) || !pwd.equals(BootConstants.PASSWORD)){
            log.info("密码或用户名错误,跳转至登陆页面");
            response.getWriter().print("password id error");
            return false;
        }
        log.info("登陆成功");
        return true;
    }

    /**
     * 在执行方法之后执行拦截器
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                            ModelAndView modelAndView) throws Exception {
        log.info("执行完方法之后");
    }
}
