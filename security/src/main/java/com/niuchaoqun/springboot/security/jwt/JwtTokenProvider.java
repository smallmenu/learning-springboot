package com.niuchaoqun.springboot.security.jwt;

import com.niuchaoqun.springboot.security.property.JwtProperty;
import io.jsonwebtoken.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Autowired
    private JwtProperty jwtProperty;

    /**
     * 生成字符串令牌
     *
     * @param authentication
     * @return
     */
    public String generateTokenByString(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(jwtUser.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperty.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret())
                .compact();
    }

    /**
     * 生成整型令牌
     *
     * @param authentication
     * @return
     */
    public String generateTokenByLong(Authentication authentication) {
        JwtUser jwtUser = (JwtUser) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(Long.toString(jwtUser.getId()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperty.getExpiration()))
                .signWith(SignatureAlgorithm.HS512, jwtProperty.getSecret())
                .compact();
    }

    /**
     * 从令牌 Subject 中获取整型
     *
     * @param token 令牌
     * @return 用户名
     */
    public Long getLongFromToken(String token) {

        Claims claims = getClaimsFromToken(token);
        String id = claims.getSubject();

        return NumberUtils.toLong(id);
    }

    /**
     * 从令牌 Subject 中获取字符串
     *
     * @param token
     * @return
     */
    public String getStringFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * 验证 Token
     *
     * @param token
     * @return
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtProperty.getSecret()).parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }

    /**
     * 从令牌中获取数据
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtProperty.getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            claims = null;
        }
        return claims;
    }
}
