package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.domain.User;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    private final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Spring Boot Index";
    }

    @RequestMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("title", "Spring Boot");
        return "hello";
    }

    /**
     * 通过 HttpServletRequest 或 @RequestHeader 获取header
     *
     * @debug http://localhost:9999/header
     * @param request
     * @return
     */
    @RequestMapping("/header")
    @ResponseBody
    public String header(HttpServletRequest request, @RequestHeader(value = "user-agent") String userAgent) {
        HashMap<String, String> headers = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            String value = request.getHeader(header);
            headers.put(header, value);
        }

        logger.info(headers.toString());
        logger.info(userAgent);
        return headers.toString();
    }

    /**
     * 通过 HttpServletRequest 获取其他请求类参数
     *
     * @debug http://localhost:9999/request?args1=a&args2=b
     * @param request
     * @return
     */
    @RequestMapping("/request")
    @ResponseBody
    public String request(HttpServletRequest request) {
        HashMap<String, String> requests = new HashMap<>();

        requests.put("Method", request.getMethod());
        requests.put("QueryString", request.getQueryString());
        requests.put("RequestURI", request.getRequestURI());
        requests.put("getRequestURL", request.getRequestURL().toString());
        requests.put("RemoteAddr", request.getRemoteAddr());

        logger.info(requests.toString());
        return requests.toString();
    }

    /**
     * GET,POST 参数获取
     *
     * @debug http://localhost:9999/get?text1=a&text2=b
     * @param text1
     * @param text2
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String get(@RequestParam("text1") String text1, @RequestParam(value = "text2") String text2) {
        logger.info(text1);
        logger.info(text2);

        return text1 + "/" + text2;
    }

    /**
     *
     * @param text1
     * @param text2
     * @return
     */
    @RequestMapping(value = "/getsimple", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getSimple(String text1, String text2) {
        return text1 + "/" + text2;
    }

    /**
     * GET 参数获取
     *
     * 不提供参数是 null
     * required=true，无defaultValue，不提供参数会400
     * 多值value必须要提供数组形式，如：checkbox[]
     *
     * @debug http://localhost:9999/gets?text1=a&checkbox[]=1&checkbox[]=2
     * @param text1
     * @param text2
     * @param checkbox
     * @return
     */
    @RequestMapping(value = "/gets", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String gets(String text1,
                      @RequestParam(value = "text2", defaultValue = "text2", required = true) String text2,
                      @RequestParam(value = "checkbox[]", required = false) ArrayList<String> checkbox) {
        logger.info(text1);
        logger.info(text2);
        logger.info(ArrayUtils.toString(checkbox));

        StringBuilder response = new StringBuilder();
        response.append(text1+"/");
        response.append(text2+"/");
        response.append(ArrayUtils.toString(checkbox));

        return response.toString();
    }

    /**
     * 获取参数集合
     * 同时会接受GET，POST参数，重名参数POST不会覆盖GET
     *
     * @debug http://localhost:9999/getmap?args1=a&args2=b
     * @param gets
     * @return
     */
    @RequestMapping("/getmap")
    @ResponseBody
    public String getMap(@RequestParam Map<String, Object> gets) {
        logger.info(gets.toString());

        return gets.toString();
    }

    /**
     *
     * 参数映射 @ModelAttribute
     *
     * @debug http://localhost:9999/getmodel?id=1&name=%E4%B8%AD%E5%9B%BD
     * @param g
     * @return
     */
    @RequestMapping("/getmodel")
    @ResponseBody
    public User getModel(@ModelAttribute User user) {
        return user;
    }

    @RequestMapping("/modelattribute")
    public String modelAttribute(@ModelAttribute(value = "model2") User user, Model model) {
        model.addAttribute("user", user);
        return "model";
    }

    @RequestMapping("/model")
    public String model(Model model, ModelMap modelMap, Map<String, Object> map) {
        model.addAttribute("title1", "model_title");
        modelMap.addAttribute("title2", "modelMap_title");
        map.put("title2", "map_title");

        User user = new User(1, "test");
        model.addAttribute("user", user);

        return "model";
    }

    @RequestMapping("/modelandview")
    public ModelAndView modelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("model");
        modelAndView.addObject("title1", "title1");
        modelAndView.addObject("title2", "title2");

        User user = new User(1, "test");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    @ModelAttribute(value = "model1")
    public String modelSet1() {
        logger.info("ModelAttribute model1");
        return "model1";
    }

    @ModelAttribute(value = "model2")
    public User modelSet2() {
        logger.info("ModelAttribute model2");
        User user = new User(1, "ModelAttribute");
        return user;
    }
}
