package org.bcos.browser.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class AuthConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(AuthInterceptor()).addPathPatterns("/group/*", "/node/**",
                "/contract/**");
    }

    @Bean
    public AuthInterceptor AuthInterceptor() {
        return new AuthInterceptor();
    }
}
