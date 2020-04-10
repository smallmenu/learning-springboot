package com.niuchaoqun.springboot.security.controller.api;

import com.niuchaoqun.springboot.commons.base.BaseController;
import com.niuchaoqun.springboot.commons.rest.RestResponse;
import com.niuchaoqun.springboot.commons.rest.RestResult;
import com.niuchaoqun.springboot.security.dto.data.Logined;
import com.niuchaoqun.springboot.security.dto.login.LoginForm;
import com.niuchaoqun.springboot.security.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Api(tags = "登录")
@RestController
@RequestMapping("/api")
public class ApiController extends BaseController {
    @Autowired
    private LoginService loginService;

    @ApiOperation("登录接口")
    @PostMapping("/login")
    public RestResult<Map> login(@Valid LoginForm loginForm, BindingResult result) {
        if (result.hasErrors()) {
            return RestResponse.fail(this.resultErrors(result));
        }

        try {
            String token = loginService.login(loginForm);

            if (token != null) {
                HashMap<String, String> data = new HashMap<>();
                data.put("token", token);
                return RestResponse.data(data);
            } else {
                return RestResponse.fail("登录失败");
            }
        } catch (Exception e) {
            return RestResponse.fail(e.getLocalizedMessage());
        }
    }

    @ApiOperation("测试")
    @PostMapping("/ping")
    public RestResult ping(ModelMap modelMap) {
        Logined logined = (Logined) modelMap.get("logined");

        return RestResponse.data(logined);
    }
}
