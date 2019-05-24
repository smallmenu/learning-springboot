package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.domain.User;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 控制器 RequestMaping 注解URL前缀: /rest
 */
@RestController
@RequestMapping("/rest")
public class RestfulController {

    private final static Logger logger = LoggerFactory.getLogger(RestfulController.class);

    private final AtomicLong counter = new AtomicLong();

    /**
     * 这种情况下，如果为空，则支持 /rest 与 /rest/ 访问
     *
     * @return
     */
    @RequestMapping("/")
    public String index() {
        return "Hello Rest";
    }

    @RequestMapping("/greeing")
    public User index(@RequestParam(value = "name", defaultValue = "World") String name) {
        User user = new User();
        user.setId(counter.incrementAndGet());
        user.setName(name);

        return user;
    }

    @RequestMapping("/url")
    public String index(@RequestParam(value = "url") String url, HttpServletResponse response) {
        OkHttpClient okhttpClient = new OkHttpClient().newBuilder().build();

        try (Response res = okhttpClient.newCall(new Request.Builder()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .url(url).build()).execute()) {
            if (res.isSuccessful() && res.body() != null) {
                ResponseBody body = res.body();
                String string = body.string();
                return string;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }


}
