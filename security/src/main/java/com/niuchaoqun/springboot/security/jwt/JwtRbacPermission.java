package com.niuchaoqun.springboot.security.jwt;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class JwtRbacPermission {

    @Autowired
    private RequestMappingHandlerMapping mapping;

    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        validRequest(request);

        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        boolean hasPermission = false;

        if (jwtUser instanceof UserDetails) {


        }

        return hasPermission;
    }

    /**
     * 验证当前请求是否存在
     *
     * @param request
     */
    private void validRequest(HttpServletRequest request) {
        String thisMethod = request.getMethod();
        Multimap<String, String> urlMappings = urlMappings();

        for (String url : urlMappings.keySet()) {
            AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(url);
            if (antPathRequestMatcher.matches(request)) {
                if (!urlMappings.get(url).contains(thisMethod)) {
                    throw new SecurityException("bad request");
                } else {
                    return;
                }
            }
        }

        throw new SecurityException("not found");

    }

    /**
     * 返回当前所有的 URL Mapping, 格式为 {"/test": ["GET", "POST"]}
     *
     * @return Multimap
     */
    private Multimap<String, String> urlMappings() {
        ArrayListMultimap<String, String> urlMappings = ArrayListMultimap.create();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            Set<String> urls = k.getPatternsCondition().getPatterns();

            RequestMethodsRequestCondition method = k.getMethodsCondition();

            urls.forEach(s -> urlMappings.putAll(s, method.getMethods().stream().map(Enum::toString).collect(Collectors.toList())));
        });

        return urlMappings;
    }

}
