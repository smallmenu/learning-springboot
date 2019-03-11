package com.niuchaoqun.springboot.jwt;

import com.niuchaoqun.springboot.entity.Admin;
import com.niuchaoqun.springboot.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin adminQuery = Admin.builder().username(username).build();
        Admin admin = adminMapper.selectOne(adminQuery);

        Optional.ofNullable(admin).orElseThrow(()->new UsernameNotFoundException("user not exist"));

        return JwtUser.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .password(admin.getPassword())
                .authorities(Collections.emptyList())
                .build();
    }
}
