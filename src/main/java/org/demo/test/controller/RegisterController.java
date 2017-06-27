package org.demo.test.controller;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.demo.test.Configuration;
import org.knapsack.freemarker.Html;
import org.knapsack.spring.AjaxFormUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.knapsack.spring.ControllerUtils;
import org.knapsack.spring.SpringApplicationContextUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.knapsack.freemarker.FreemarkerUtils;
import org.demo.test.pojo.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.springframework.context.i18n.LocaleContextHolder.getLocale;

/** 注册模拟
 * Created by admin on 2017-04-12.
 */
@RestController
public class RegisterController {



    //注册页
    @RequestMapping(value = "/register.do", method = RequestMethod.GET)
    public ModelAndView toRegister(HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException, ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();

        //////////////////////////////////////////
         /*比如访问次数记录 */
        //////////////////////////////////////////
        map.put("dateTime",new Date().getTime());
        //调用方法生成HTML页
        return FreemarkerUtils.toView(request,response,"/register","register.ftl",map);
    }


    //提交注册
    @RequestMapping(value = "/register.do", method = RequestMethod.POST)
    @ResponseBody
    public Object doRegister(HttpServletRequest request, HttpServletResponse response, @ModelAttribute("user") @Validated(User.RegisterChecks.class) User user, BindingResult result){
        //ModelAndView view = new ModelAndView(new MappingJackson2JsonView());// new ModelAndView("/index.jsp",result.getModel());

        //验证失败
        if(result.hasErrors()){
            Map<String, Object> model = result.getModel();
            return AjaxFormUtils.jsonMessage(false,null,model,"user");
        }

        ///////////////////////////////////////////////////
        //处理数据
        ///////////////////////////////////////////////////

        //响应成功注册
        return AjaxFormUtils.jsonMessage(true);
    }


    //登录页   使用@Html注解，该方法只访问一次
    @RequestMapping(value = "/login.do", method = RequestMethod.GET)
    @Html(file = "/login")
    public ModelAndView toLogin(HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> map = new HashMap<String, Object>();

        return new ModelAndView("/login.ftl",map);
    }


    /**
     *
     * @param request
     * @param response
     * @param captcha
     * @param user
     * @param result
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    //多个实体验证 @Valid @ModelAttribute("a") A a, BindingResult aErrors, @Valid @ModelAttribute("b") B b, BindingResult bErrors
    public Object doLogin(HttpServletRequest request, HttpServletResponse response, @RequestParam String captcha, @ModelAttribute("user") @Validated(User.LoginChecks.class) User user, BindingResult result) throws ServletException, IOException {
        Map<String, Object> map = new HashMap<String, Object>();
        HttpSession session=request.getSession();


        ControllerUtils.captchaVerify(captcha,ObjectUtils.toString(session.getAttribute(Configuration.SES_CAPTCHA)),result,request.getLocale());

        //验证失败
        if(result.hasErrors()){
            Map<String, Object> model = result.getModel();
            return AjaxFormUtils.jsonMessage(false,null,model,"user");
        }

        //响应成功登录
        return AjaxFormUtils.jsonMessage(true);
    }


    //返回实体JSON对象处理

}
