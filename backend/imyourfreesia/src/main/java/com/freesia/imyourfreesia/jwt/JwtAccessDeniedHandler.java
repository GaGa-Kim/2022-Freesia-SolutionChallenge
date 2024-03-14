package com.freesia.imyourfreesia.jwt;

import com.freesia.imyourfreesia.handler.ErrorCode;
import com.freesia.imyourfreesia.handler.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_FORBIDDEN);
        response.getWriter().write(errorResponse.convertToJson());
        response.setStatus(errorResponse.getStatus());
    }
}
