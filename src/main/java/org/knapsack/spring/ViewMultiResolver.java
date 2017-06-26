package org.knapsack.spring;


import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import java.util.Map;


/** 多视图分配
 * @author yangkun
 *
 */
public class ViewMultiResolver implements ViewResolver {

    private Map<String, ViewResolver> resolvers;

    /* (non-Javadoc)
     * @see org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang.String, java.util.Locale)
     */
    @Override
    public View resolveViewName(String viewName, Locale locale)throws Exception {
        int sub = viewName.lastIndexOf(".");//获取分隔线索引
        ViewResolver resolver =null;
        if (sub>0){//找到配置视图
            String suffix = viewName.substring(sub + 1);// 有的话截取下划线后面的字符串 这里一般是jsp,ftl,vm与配置文件中的<entry key="ftl">的key匹配
            viewName = viewName.substring(0, sub);// 取下划线前面的部分 那时真正的资源名.比如我们要使用hello.jsp 那viewName就应该是hello_jsp
            resolver = resolvers.get(suffix);// 根据下划线后面的字符串去获取托管的视图解析类对象
        }else{
            throw new Exception("返回视图名不合法，应以.（下划线）加上配置的视图简称");
        }
        if (resolver != null)return resolver.resolveViewName(viewName, locale);return null;
    }


    /**
     * @return
     */
    public Map<String, ViewResolver> getResolvers() {
        return resolvers;
    }


    /**
     * @param resolvers
     */
    public void setResolvers(Map<String, ViewResolver> resolvers) {
        this.resolvers = resolvers;
    }
}
