package org.demo.test.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.knapsack.freemarker.Html;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/** 测试
 * Created by admin on 2017-04-09.
 */
@RestController
public class TestController {

    /**
     * 自动生成HTML 注解功能@Html测试
     * @param request
     * @param response
     * @return
     * @throws NoSuchMethodException
     */
    @RequestMapping(value = "/test/to.do", method = RequestMethod.GET)
    @Html(file = "/test")
    public ModelAndView toMethod(HttpServletRequest request, HttpServletResponse response) throws NoSuchMethodException {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "张三1"+new Date().getTime());
        return new ModelAndView("/test.ftl", map);
    }


    @RequestMapping(value = "/test/abc.do", method = RequestMethod.GET)
    @ModelAttribute("abc")
    public ModelAndView toAbcMethod(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "李四");
        return new ModelAndView("/test2.ftl", map);
    }
}