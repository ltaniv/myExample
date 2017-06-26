package org.demo.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by admin on 2017-04-09.
 */
public class FtlToHtml implements Filter{

    final Log logger = LogFactory.getLog(getClass());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //...
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        logger.debug("请求："+request.getRequestURI());
        long time=new Date().getTime();
        request.setAttribute("time",time);
        filterChain.doFilter(request,response);
        long s = new Date().getTime() - time;
        System.out.printf("总响应时间："+s+"毫秒");
    }

    @Override
    public void destroy() {
        //...
    }
}
