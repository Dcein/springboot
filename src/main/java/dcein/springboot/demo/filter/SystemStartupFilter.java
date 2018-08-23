package dcein.springboot.demo.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: DingCong
 * @Description: 系统启动监听器
 * @@Date: Created in 17:06 2018/7/20
 */
@Slf4j
@WebFilter(filterName = "SystemStartupFilter" , urlPatterns = "/*")
public class SystemStartupFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("[系统初始化...]");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        log.info("[过滤器工作中...]");
        filterChain.doFilter((HttpServletRequest)req,(HttpServletResponse)resp);

    }

    @Override
    public void destroy() {
        log.info("[过滤器注销...]");

    }
}
