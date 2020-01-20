package com.na.backend.interceptor;

import com.na.backend.exception.UnauthorizedException;
import com.na.backend.service.UserService;
import jdk.nashorn.internal.parser.Token;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private static final String HEADER_ID = "id";
    private static final String HEADER_TOKEN = "token";

    private final UserService userService;

    public TokenInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        final String id = request.getHeader(HEADER_ID);
        final String token = request.getHeader(HEADER_TOKEN);

        if(id == null) {
            throw new UnauthorizedException("no id");
        }
        if(token == null) {
            throw new UnauthorizedException("no token");
        }

        if(userService.isUser(id, token)){
            return true;
        }else{
            throw new UnauthorizedException("invalid token");
        }
    }
}