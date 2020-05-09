package com.niuchaoqun.springboot.security.jwt;

import com.niuchaoqun.springboot.security.entity.Admin;
import com.niuchaoqun.springboot.security.entity.Role;
import com.niuchaoqun.springboot.security.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class JwtUserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private AdminService adminService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.getRelationByUsername(username);

        Optional.ofNullable(admin).orElseThrow(() -> new UsernameNotFoundException("user not exist"));

        // 用户角色
        List<Role> roles = admin.getRoles();
        String[] authorities = roles.stream().map(Role::getRole).toArray(String[]::new);

        return JwtUser.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .password(admin.getPassword())
                .state(admin.getState())
                .locked(admin.getLocked())
                .authorities(AuthorityUtils.createAuthorityList(authorities))
                .build();
    }
}
