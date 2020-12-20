package com.ato.service.businessSerivce;

import com.ato.dao.TokenDAO;
import com.ato.utils.Translator;
import com.google.common.base.Function;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Configurable
public class JwtService implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 30 * 60;
    private static final String AUTHORITIES_KEY = "auth";
    private Key key;

    @Autowired
    TokenDAO tokenDAO;


    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = System.currentTimeMillis();
        Date validity;
        if (rememberMe) {
            validity = new Date(now + JWT_TOKEN_VALIDITY * 1000);
        } else {
            validity = new Date(now + JWT_TOKEN_VALIDITY * 1000);
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, Translator.toLocale("jwt.secret"))
                .setExpiration(validity)
                .compact();
    }


    // Tạo token cho từng user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    // Lấy tên người dùng từ mã token
    public String getAuthentication(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // Lấy ngày hết hạn từ mã token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // Lấy thông tin từ token phải dùng khoá bí mật
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(Translator.toLocale("jwt.secret")).parseClaimsJws(token).getBody();
    }

    // kiểm tra token đã hết hạn chưa ?
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        String token = Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, Translator.toLocale("jwt.secret")).compact();
        return token;
    }

    // Xác nhận token
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getAuthentication(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
