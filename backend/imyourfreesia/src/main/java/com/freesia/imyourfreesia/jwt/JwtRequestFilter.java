package com.freesia.imyourfreesia.jwt;

import com.freesia.imyourfreesia.handler.ErrorCode;
import com.freesia.imyourfreesia.handler.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtTokenValidator jwtTokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String path = request.getServletPath();
            if (!path.startsWith("/api")) {
                filterChain.doFilter(request, response);
            } else {
                String token = jwtTokenValidator.resolveAccessToken(request);
                boolean isTokenValid = jwtTokenValidator.validateToken(token);
                if (StringUtils.hasText(token) && isTokenValid) {
                    setAuthentication(token);
                }
                filterChain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            response.setContentType("application/json; charset=UTF-8");
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
            response.getWriter().write(errorResponse.convertToJson());
            response.setStatus(errorResponse.getStatus());
        }
    }

    private void setAuthentication(String token) {
        Authentication authentication = jwtTokenValidator.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}