package com.niuchaoqun.springboot.config;

import com.niuchaoqun.springboot.jwt.JwtAuthenticationEntryPoint;
import com.niuchaoqun.springboot.jwt.JwtTokenAuthFilter;
import com.niuchaoqun.springboot.property.JwtProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Multiple HttpSecurity 配置
 *
 * @author niuchaoqun
 */
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * http.antMatcher() 表示当前实例仅仅对这个路由进行配置
     * <p>
     * 这里是个 Http Basic Auth 认证
     */
    @Configuration
    @Order(1)
    public static class BasicWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher("/basic/**").authorizeRequests().anyRequest().authenticated().and().httpBasic();

        }
    }

    /**
     * 其他所有请求路由的配置
     * <p>
     * 增加了 Jwt 认证
     */
    @Configuration
    public static class JwtWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private JwtProperty jwtProperty;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        /**
         * 默认情况下 @Bean 会被 SpringBoot 自动探测到并且加入到 Security filter chain
         * <p>
         * 在这里手动禁止 Filter 自动注入，另一种方法是不使用 @Bean 而通过手动 new
         *
         * @return
         */
        @Bean
        public FilterRegistrationBean jwtTokenAuthFilterRegistration() {
            FilterRegistrationBean registration = new FilterRegistrationBean(jwtTokenAuthFilter());
            registration.setEnabled(false);
            return registration;
        }

        @Bean
        public JwtTokenAuthFilter jwtTokenAuthFilter() {
            return new JwtTokenAuthFilter();
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    // 禁用 CSRF
                    .csrf().disable()

                    // 不存储Session，使用无状态回话
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    // 处理认证的异常，自定义返回 REST JSON
                    .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                    // 路由规则
                    .and()
                    .addFilterBefore(jwtTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(HttpMethod.POST, jwtProperty.getLoginUrl()).permitAll()
                    .antMatchers(jwtProperty.getUrl())
                    .authenticated()
                    .and()

                    // 禁止页面缓存
                    .headers().cacheControl();
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

        @Bean
        public BCryptPasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }


}
