package com.niuchaoqun.springboot.redis.controller;

import com.niuchaoqun.springboot.commons.rest.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
@Slf4j
public class SessionController {
    @RequestMapping("/set")
    public Object set(HttpSession session) {
        session.setAttribute("session1", "value1");
        session.setAttribute("session2", "value2");
        return RestResponse.success();
    }

    @RequestMapping("/get")
    public Object get(
            HttpServletRequest request,
            HttpSession httpSession,
            @SessionAttribute(value = "session1", required = false) String session1) {
        HttpSession session = request.getSession();
        String session2 = (String) session.getAttribute("session2");
        String http_session1 = (String) httpSession.getAttribute("session1");

        log.info(http_session1);
        log.info(session1);
        log.info(session2);

        return RestResponse.data(session1);
    }
}
