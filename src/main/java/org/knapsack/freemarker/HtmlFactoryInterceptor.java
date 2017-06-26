package org.knapsack.freemarker;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.knapsack.util.WorkFileUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.util.Properties;

/** 该拦截器 仅拦截 @Html(use=true,file="")
 *      use 是否使用该功能，默认true
 *      file 指定生成页面和路径，不可包含后缀名
 *  config.properties 配置文件
 *      staticHtmlFolder - 前缀
 *      staticHtmlSuffix - 后缀
 *
 * Created by admin on 2017-04-10.
 */
public class HtmlFactoryInterceptor implements HandlerInterceptor {

    private static Logger logger= Logger.getLogger(HtmlFactoryInterceptor.class);

    private static final String TEMP_SIGN="my_freemarker_sign_temp";

    @Resource(name = "properties")
    private Properties properties;

    /**
     * 控件层方法调用之前调用该方法
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //handler
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //handler对象中获取自定义注解
            Html html = ((HandlerMethod) handler).getMethodAnnotation(Html.class);
            //处理自定义注解值正确性
            boolean isHtml = html == null ? false : html.use();
            //是否需要创建HTML页面
            if(isHtml){
                String staticHtmlFolder= properties.getProperty("staticHtmlFolder");//前缀
                String staticHtmlSuffix= properties.getProperty("staticHtmlSuffix");//后缀

                //是否新生成页面的参数认证
                boolean isUpdateHtml=FreemarkerUtils.isUpdateHtml(request);
                if(isUpdateHtml)logger.info("参数指定重新生成新页面");

                //目标HTML页的绝对路径 前部分
                String beforeHtmlPath=WorkFileUtils.getRootFile()+staticHtmlFolder;
                //目标HTML页的绝对路径 后部分
                String afterHtmlPath=html.file()+"."+staticHtmlSuffix;
                //目标HTML是否存在
                if(new File(beforeHtmlPath+afterHtmlPath).exists()&&!isUpdateHtml){
                    logger.info("直接响应页面");
                    //页面存在，并且参数不包括指定值时， 直接响应
                    request.getRequestDispatcher(staticHtmlFolder+afterHtmlPath).forward(request, response);
                    return false;
                }else{
                    //页面不存在，在Request中保存标识不存在当前请求的HTML页
                    request.setAttribute(TEMP_SIGN, true);
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * 控件层方法调用之后调用该方法
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //handler
        if(handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            //handler对象中获取自定义注解
            Html html = ((HandlerMethod) handler).getMethodAnnotation(Html.class);
            //处理自定义注解值正确性
            boolean isHtml = html == null ? false : html.use();
            //是否需要创建HTML页面
            if (isHtml) {
                String staticHtmlFolder= properties.getProperty("staticHtmlFolder");
                String staticHtmlSuffix= properties.getProperty("staticHtmlSuffix");
                //从Request中获取相应标识，判断是否为TRUE,为true时，说明需要创建请求的相关HTML页
                if (BooleanUtils.isTrue((Boolean) request.getAttribute(TEMP_SIGN))) {
                    request.removeAttribute(TEMP_SIGN);
                    //目标HTML页的绝对路径 前部分
                    String beforeHtmlPath = WorkFileUtils.getRootFile() + staticHtmlFolder;
                    //目标HTML页的绝对路径 后部分
                    String afterHtmlPath = html.file() + "." + staticHtmlSuffix;
                    //创建请求的HTML文件
                    FreemarkerUtils.crateHTML(modelAndView.getModel(),modelAndView.getViewName(), beforeHtmlPath + afterHtmlPath);
                }
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
