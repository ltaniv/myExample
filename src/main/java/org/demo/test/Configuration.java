package org.demo.test;

import java.util.Properties;

/**
 * Created by yxyy on 2017-06-27.
 */
public class Configuration {

    /**
     * 自定义资源文件
     */
    private static Properties properties;


    /** 变量 从会话中取验证码的KEY **/
    public static String SES_CAPTCHA="";


    public void setProperties(Properties properties) {
        Configuration.properties = properties;
    }
}
