package com.na.backend.config;

import com.na.backend.interceptor.TokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String[] EXCLUDE_PATHS = {
            "/*",
            "/user/email",
            "/user/social",
            "/user/password"
    };

    private static final String[] ADD_PATHS = {
            "/questionnaire/*",
            "/question/**",
            "/user/name",
            "/friend/*"
    };

    private final TokenInterceptor tokenInterceptor;

    public WebConfig(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns(ADD_PATHS)
                .excludePathPatterns(EXCLUDE_PATHS);
    }
}