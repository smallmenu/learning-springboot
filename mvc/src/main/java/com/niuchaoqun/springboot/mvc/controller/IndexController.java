package com.niuchaoqun.springboot.mvc.controller;

import com.niuchaoqun.springboot.mvc.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@Slf4j
public class IndexController {
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

    @RequestMapping("/test")
    public String test() {
        return "sub/test";
    }

    /**
     * 通过 HttpServletResponse 输出图片
     *
     * @param response
     * @throws IOException
     */
    @RequestMapping("/image1")
    public void image1(HttpServletResponse response) throws IOException {
        String image = "";

        response.setContentType("image/jpeg");
        ServletOutputStream os = response.getOutputStream();
        os.write(image.getBytes());
        os.flush();
        os.close();
    }

    @RequestMapping(value = "/image1", produces = {MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public byte[] image2() {
        String image = "";
        return image.getBytes();
    }

    /**
     * 通过 HttpServletRequest 或 @RequestHeader 获取header
     *
     * @param request
     * @param userAgent
     * @return
     */
    @RequestMapping("/header")
    @ResponseBody
    public HashMap<String, String> header(HttpServletRequest request, @RequestHeader(value = "user-agent") String userAgent) {
        HashMap<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String value = request.getHeader(headerName);
            headers.put(headerName, value);
        }

        log.info(headers.toString());
        log.info(userAgent);
        return headers;
    }

    /**
     * 通过 HttpServletRequest 获取其他请求类参数，如IP
     *
     * @param request
     * @return
     */
    @RequestMapping("/request")
    @ResponseBody
    public HashMap<String, String> request(HttpServletRequest request) {
        HashMap<String, String> requests = new HashMap<>();

        requests.put("Method", request.getMethod());
        requests.put("QueryString", request.getQueryString());
        requests.put("RequestURI", request.getRequestURI());
        requests.put("getRequestURL", request.getRequestURL().toString());
        requests.put("RemoteAddr", request.getRemoteAddr());

        log.info(requests.toString());
        return requests;
    }

    /**
     * 普通方式获取 GET,POST 参数，不提供参数是null，提供参数无值为空字符串
     *
     * @param text1
     * @param text2
     * @return
     */
    @RequestMapping(value = "/getsimple", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String getSimple(String text1, String text2) {
        log.info(text1);
        log.info(text2);
        return text1 + "/" + text2;
    }

    /**
     * 通过注解获取 GET,POST 参数
     * <p>
     * 和普通方式区别的意义在于：可以进行映射、默认值、参数检查等操作
     * RequestParam 注解默认 required=true
     *
     * @param text1
     * @param text2
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String get(@RequestParam("text1") String text1, @RequestParam(value = "text2") String text2) {
        log.info(text1);
        log.info(text2);

        return text1 + "/" + text2;
    }

    /**
     * GET 参数获取
     * <p>
     * 不提供参数是 null
     * required=true，无defaultValue，不提供参数会400
     * 多值value必须要提供数组形式，如：checkbox[]
     *
     * @param text1
     * @param text2
     * @param checkbox
     * @return
     */
    @RequestMapping(value = "/gets", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public String gets(String text1,
                       @RequestParam(value = "text2", defaultValue = "value2", required = true) String text2,
                       @RequestParam(value = "checkbox[]", required = false) ArrayList<String> checkbox) {
        log.info(text1);
        log.info(text2);
        log.info(ArrayUtils.toString(checkbox));

        StringBuilder response = new StringBuilder();
        response.append(text1 + "/");
        response.append(text2 + "/");
        response.append(ArrayUtils.toString(checkbox));

        return response.toString();
    }

    /**
     * 获取参数集合
     * 同时会接受GET，POST参数，重名参数POST不会覆盖GET
     *
     * @param gets
     * @return
     */
    @RequestMapping("/getmap")
    @ResponseBody
    public Map<String, Object> getMap(@RequestParam Map<String, Object> gets) {
        log.info(gets.toString());

        return gets;
    }

    /**
     * 参数映射 @ModelAttribute
     *
     * @param user
     * @return
     */
    @RequestMapping("/getmodel")
    @ResponseBody
    public User getModel(@Valid User user, String address) {
        log.info(user.toString());
        log.info(address);
        return user;
    }

    @RequestMapping("/modelattribute")
    public String modelAttribute(@ModelAttribute(value = "model2") User user, Model model) {
        model.addAttribute("user", user);
        return "model";
    }

    /**
     * 模版引擎数据传入，返回是模版文件名
     *
     * @param model    这是个接口
     * @param modelMap 这是个实现
     * @param map      这是Java原生的 Map 类
     * @return
     */
    @RequestMapping("/model")
    public String model(Model model, ModelMap modelMap, Map<String, Object> map) {
        model.addAttribute("title1", "model_title");
        modelMap.addAttribute("title2", "modelMap_title");
        map.put("title2", "map_title");

        User user = new User(1L, "zhangsan");
        model.addAttribute("user", user);

        return "model";
    }

    /**
     * 手动渲染模版，返回 ModelAndView 对象
     *
     * @return
     */
    @RequestMapping("/modelandview")
    public ModelAndView modelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("model");
        modelAndView.addObject("title1", "title1");
        modelAndView.addObject("title2", "title2");

        User user = new User();
        user.setId(1L);
        user.setName("test");
        modelAndView.addObject("user", user);

        return modelAndView;
    }

    /**
     * ModelAttribute 注解会优先于控制器方法执行
     *
     * @return String
     */
    @ModelAttribute(value = "model1")
    public String modelSet1() {
        log.info("ModelAttribute model1");
        return "model1";
    }

    @ModelAttribute(value = "model2")
    public User modelSet2() {
        log.info("ModelAttribute model2");
        User user = new User();
        user.setId(1L);
        user.setName("ModelAttribute User");
        return user;
    }
}
