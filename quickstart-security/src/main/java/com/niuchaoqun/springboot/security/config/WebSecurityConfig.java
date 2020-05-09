package com.niuchaoqun.springboot.security.config;

import com.niuchaoqun.springboot.security.jwt.JwtAccessDeniedHander;
import com.niuchaoqun.springboot.security.jwt.JwtAuthenticationEntryPoint;
import com.niuchaoqun.springboot.security.jwt.JwtTokenAuthFilter;
import com.niuchaoqun.springboot.security.property.BasicProperty;
import com.niuchaoqun.springboot.security.property.JwtIgnoreProperty;
import com.niuchaoqun.springboot.security.property.JwtProperty;
import com.niuchaoqun.springboot.security.property.OpenapiProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Optional;

/**
 * Multiple HttpSecurity 配置
 */
@EnableWebSecurity
public class WebSecurityConfig {
    /**
     * http.antMatcher() 表示当前实例仅仅对这个路由进行配置
     * <p>
     * 这里是个 Http Basic Auth 认证
     * <p>
     * 因为抛弃了自动化配置，所以 spring.security.user 配置无效，需要手动指定用户名密码
     */
    @Configuration
    @Order(1)
    public static class BasicWebSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private BasicProperty basicProperty;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.inMemoryAuthentication()
                    .withUser(basicProperty.getUser())
                    .password(bCryptPasswordEncoder.encode(basicProperty.getPassword()))
                    .roles("ADMIN");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.antMatcher(basicProperty.getUrl())
                    .authorizeRequests()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
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
        private JwtIgnoreProperty jwtIgnoreProperty;

        @Autowired
        private OpenapiProperty openapiProperty;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

        @Autowired
        private JwtAccessDeniedHander jwtAccessDeniedHander;

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
                    .formLogin().disable()
                    .httpBasic().disable()

                    // 不存储Session，使用无状态回话
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                    // 处理异常，自定义返回 REST JSON
                    .and()
                    .exceptionHandling()
                    // 认证授权
                    .accessDeniedHandler(jwtAccessDeniedHander)
                    // 无认证
                    .authenticationEntryPoint(jwtAuthenticationEntryPoint)

                    // 自定义 Jwt 拦截器
                    .and()
                    .addFilterBefore(jwtTokenAuthFilter(), UsernamePasswordAuthenticationFilter.class)

                    // 路由配置
                    .authorizeRequests()
                    .antMatchers("/").permitAll()
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers(openapiProperty.getUrl()).permitAll()
                    .antMatchers(jwtProperty.getUrl()).access("@jwtRbacPermission.hasPermission(request, authentication)")
                    // 认证其他请求
                    // .anyRequest()
                    // .authenticated()
                    .and()
                    .logout().disable()

                    // 禁止页面缓存
                    .headers().cacheControl();
        }

        @Override
        public void configure(WebSecurity webSecurity) {
            WebSecurity and = webSecurity.ignoring().and();

            Optional.ofNullable(jwtIgnoreProperty.getGets()).ifPresent(urls -> urls.forEach(url -> and.ignoring().antMatchers(HttpMethod.GET, url)));
            Optional.ofNullable(jwtIgnoreProperty.getPosts()).ifPresent(urls -> urls.forEach(url -> and.ignoring().antMatchers(HttpMethod.POST, url)));
            Optional.ofNullable(jwtIgnoreProperty.getPuts()).ifPresent(urls -> urls.forEach(url -> and.ignoring().antMatchers(HttpMethod.PUT, url)));
            Optional.ofNullable(jwtIgnoreProperty.getDeletes()).ifPresent(urls -> urls.forEach(url -> and.ignoring().antMatchers(HttpMethod.DELETE, url)));
            Optional.ofNullable(jwtIgnoreProperty.getHeads()).ifPresent(urls -> urls.forEach(url -> and.ignoring().antMatchers(HttpMethod.HEAD, url)));
            Optional.ofNullable(jwtIgnoreProperty.getPatterns()).ifPresent(urls -> urls.forEach(url -> and.ignoring().antMatchers(url)));
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
