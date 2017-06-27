package org.knapsack.spring;

import org.apache.commons.lang.StringUtils;
import org.knapsack.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Locale;

/**
 * Created by yxyy on 2017-06-27.
 */
public class ControllerUtils {

    /**
     * 验证验证码
     * @param paramCaptcha 参数中的验证码
     * @param sessionCaptcha 会话中的验证码
     * @param result
     * @return
     */
    public static boolean captchaVerify(String paramCaptcha, String sessionCaptcha, BindingResult result,Locale locale) {
        String code=StringUtils.isBlank(paramCaptcha)?"captcha.NotBlank.message":!paramCaptcha.equals(sessionCaptcha)?"captcha.Error.message":"";
        Assert.notEmpty(code,"不能为空");
        String msg=SpringApplicationContextUtils.getApplicationContext().getMessage(code,null,locale);
        if (StringUtils.isNotBlank(msg)){
            FieldError oe = new FieldError("", "captcha",msg);
            result.addError(oe);
            return false;
        }
        return true;
    }
}
