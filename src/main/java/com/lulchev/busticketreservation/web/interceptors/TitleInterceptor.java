package com.lulchev.busticketreservation.web.interceptors;

import com.lulchev.busticketreservation.web.annotations.Title;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class TitleInterceptor extends HandlerInterceptorAdapter {
    private static final String BUS_TICKET_TITLE_PREFIX = "Bus Ticket - ";
    private static final String TITLE_ATTRIBUTE = "title";

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        if (modelAndView == null) {
            modelAndView = new ModelAndView();
        } else {
            if (handler instanceof HandlerMethod) {
                final Title pageTitle = ((HandlerMethod) handler).getMethodAnnotation(Title.class);

                if (pageTitle != null) {
                    modelAndView.addObject(TITLE_ATTRIBUTE, BUS_TICKET_TITLE_PREFIX + pageTitle.value());
                }
            }
        }
    }
}
