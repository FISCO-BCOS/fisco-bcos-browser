package org.bcos.browser.auth;

import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bcos.browser.base.ConstantCode;
import org.bcos.browser.base.Constants;
import org.bcos.browser.base.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    Constants constants;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
            Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        ConfigAuth configAuth = method.getAnnotation(ConfigAuth.class);
        // check configAuth
        if (configAuth != null && !constants.isConfigAuth()) {
            throw new BaseException(ConstantCode.NOT_HAVE_PERMISSION);
        }
        return true;
    }
}
