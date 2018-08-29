package com.niuchaoqun.springboot.controller;

import com.niuchaoqun.springboot.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {
    private static final Logger logger = LoggerFactory.getLogger(SessionController.class);

    @RequestMapping("/set")
    public Object set(HttpSession session) {
        session.setAttribute("session1", "value1");
        session.setAttribute("session2", "value2");
        return Response.success();
    }

    @RequestMapping("/get")
    public Object get(
            HttpServletRequest request,
            HttpSession httpSession,
            @SessionAttribute(value = "session1", required = false) String session1) {
        HttpSession session = request.getSession();
        String session2 = (String)session.getAttribute("session2");
        String http_session1 = (String)httpSession.getAttribute("session1");

        logger.info(http_session1);
        logger.info(session1);
        logger.info(session2);

        return Response.data(session1);
    }
}
