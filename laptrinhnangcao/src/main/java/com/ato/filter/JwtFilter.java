package com.ato.filter;


import com.ato.config.spirngConfig.ApplicationContextHolder;
import com.ato.config.spirngConfig.Constants;
import com.ato.config.spirngConfig.LogConfig;
import com.ato.config.springSecurity.CustomUserDetails;
import com.ato.model.baseModel.Logs;
import com.ato.service.businessSerivce.JwtService;
import com.ato.service.businessSerivce.UserDetailsServiceImpl;
import com.ato.utils.Translator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.owasp.encoder.Encode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
//OncePerRequestFilter thằng này sẽ chỉ thực hiện một lần filter trong mỗi request.
public class JwtFilter extends OncePerRequestFilter {
    public static final String AUTHORIZATION_HEADER = "Authorization";

    UserDetailsServiceImpl userDetailsService = ApplicationContextHolder.getContext().getBean(UserDetailsServiceImpl.class);
    JwtService jwtTokenUtil = ApplicationContextHolder.getContext().getBean(JwtService.class);
    LogConfig logConfig = ApplicationContextHolder.getContext().getBean(LogConfig.class);

    @Override
    protected void initFilterBean() throws ServletException {
        super.initFilterBean();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, ServiceException {
        final String requestTokenHeader = request.getHeader( "Authorization" );
        String username = null;
        String jwtToken = null;
        if (StringUtils.isNotEmpty(requestTokenHeader) && requestTokenHeader.startsWith(Constants.BEARER)) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtTokenUtil.getAuthentication(jwtToken);
            } catch (Exception e) {
                logger.info(e.getMessage(), e);
                response.setContentType("application/json;charset=UTF-8");
                response.sendError(401, Encode.forHtml(Translator.toLocale(Constants.URL_PART_FALSE)));
                return;
            }
        }

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                String refreshToken = jwtTokenUtil.generateToken(userDetails);
                CustomUserDetails customUserDetails = (CustomUserDetails) userDetails;
                customUserDetails.setJwt(refreshToken);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                response.setHeader(JwtFilter.AUTHORIZATION_HEADER, Constants.BEARER + refreshToken);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter( request, response );
    }
}

