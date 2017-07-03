package com.niuchaoqun.springboot.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;

@Controller
public class SessionController {

    final private static Logger logger = LoggerFactory.getLogger(SessionController.class);

    @RequestMapping("/setsession")
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("session1", "value1");
        session.setAttribute("session2", "value2");
        return "";
    }

    @RequestMapping("/getsession")
    @ResponseBody
    public String getSession(
            HttpServletRequest request,
            HttpSession httpSession,
            @SessionAttribute(value = "session1", required = false) String session1) {
        HttpSession session = request.getSession();
        String session2 = (String)session.getAttribute("session2");
        String http_session1 = (String)httpSession.getAttribute("session1");

        logger.info(http_session1);
        logger.info(session1);
        logger.info(session2);

        HashMap<String, String> sessionMap = new HashMap<>();
        Enumeration<String> sessions = session.getAttributeNames();
        while(sessions.hasMoreElements()) {
            String key = sessions.nextElement();
            sessionMap.put(key, (String)session.getAttribute(key));
        }

        return sessionMap.toString();
    }

    @RequestMapping("/delsession")
    @ResponseBody
    public String delSession(HttpSession httpSession) {
        httpSession.removeAttribute("session1");
        httpSession.removeAttribute("session2");

        return "delete session ok";
    }
}
