package org.knapsack.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.knapsack.util.WorkFileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * Created by admin on 2017-04-09.
 */
public class FreemarkerUtils {

    /**
     * Freemarker 配置对象
     */
    private static FreeMarkerConfig freeMarkerConfig;

    /**
     * 自定义资源文件
     */
    private static Properties properties;

    public void setFreeMarkerConfig(FreeMarkerConfig freeMarkerConfig) {
        FreemarkerUtils.freeMarkerConfig = freeMarkerConfig;
    }

    public void setProperties(Properties properties) {
        FreemarkerUtils.properties = properties;
    }

    /**
     * 是否更新HTML文件
     * 该条件是由config.properties 配置文件key为staticHtmlToDynKy的值参数判断
     * @param request
     * @return
     */
    public  static boolean isUpdateHtml(HttpServletRequest request){
        //是否新生成页面的参数认证
        String[] kv=null;
        String staticHtmlToDynKy=properties.getProperty("staticHtmlToDynKy");//是否新生成页面的参数(k=v)
        if(staticHtmlToDynKy!=null&&staticHtmlToDynKy.matches("^\\w+=\\w+$")){
            kv=staticHtmlToDynKy.split("=");
        }
       return kv!=null?kv[1].equals(request.getParameter(kv[0])):false;
    }

    /**
     * 创建HTML文件
     * @param data 模版的填充数据
     * @param templatePath 模板文件
     * @param targetHtmlPath 生成HTML绝对文件路径
     */
    public static void crateHTML(Map<String, Object> data,String templatePath, String targetHtmlPath) {
        try {
            //创建一个合适的Configration对象
            Configuration configuration = freeMarkerConfig.getConfiguration();// new Configuration(Configuration.VERSION_2_3_23);
            //设置模版根文件夹
            //configuration.setDirectoryForTemplateLoading(new File(templateRootPath));
            //设置默认编码
            //configuration.setDefaultEncoding("UTF-8");
            //获取一个模版文件
            Template template = configuration.getTemplate(templatePath);

            //静态页面输出流
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(targetHtmlPath)), "UTF-8"));
            // 处理模版 map数据 ,输出流
            template.process(data, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 把当前的请求目标文件转换成HTML文件并响应，如果存在直接响应
     * @param request
     * @param response
     * @param htmlName 项目绝对根目录 + 配置目录 + htmlName + .后缀
     * @param viewName 视图名称
     * @param map 页面填充数据
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public static ModelAndView toView(HttpServletRequest request, HttpServletResponse response, String htmlName, String viewName, Map<String, Object> map) throws ServletException, IOException {
        String staticHtmlFolder= properties.getProperty("staticHtmlFolder");
        String staticHtmlSuffix= properties.getProperty("staticHtmlSuffix");
        //项目根目录
        String rootPath= WorkFileUtils.getRootFile().toString();
        //相对项目目录的 html 文件路径
        String targetHtmlPath =  staticHtmlFolder + htmlName+"." + staticHtmlSuffix;
        //是否新生成页面的参数认证
        boolean isUpdateHtml=FreemarkerUtils.isUpdateHtml(request);
        if(!new File(rootPath+targetHtmlPath).exists()||isUpdateHtml){
            FreemarkerUtils.crateHTML(map,viewName,rootPath+targetHtmlPath);
        }
        request.getRequestDispatcher(targetHtmlPath).forward(request,response);
        return null;
    }
}
