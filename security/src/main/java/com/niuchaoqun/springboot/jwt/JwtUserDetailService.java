package com.niuchaoqun.springboot.jwt;

import com.niuchaoqun.springboot.entity.Admin;
import com.niuchaoqun.springboot.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.getRelationByUsername(username);
        Optional.ofNullable(admin).orElseThrow(()->new UsernameNotFoundException("user not exist"));

        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(admin.getRole().getRole());

        return new JwtUser(admin.getUsername(), admin.getPassword(), admin.getState(), authorities);
    }
}
