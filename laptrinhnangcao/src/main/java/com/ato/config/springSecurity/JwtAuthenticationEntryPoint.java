package com.ato.config.springSecurity;

import com.ato.utils.Translator;
import org.owasp.encoder.Encode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Component
//Tất cả các request mà không được xác thực sẽ trả về lỗi 401
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setContentType( "application/json;charset=UTF-8" );
        response.sendError( 401, Encode.forHtml( Translator.toLocale( "url.part.false" ) ));
    }
}