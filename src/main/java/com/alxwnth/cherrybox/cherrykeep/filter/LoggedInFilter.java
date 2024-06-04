package com.alxwnth.cherrybox.cherrykeep.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class LoggedInFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        System.out.println("LoggedInFilter");

        // TODO: Refactor this
        if (isAuthenticated() && ("/login".equals(servletRequest.getRequestURI()) || "/signup".equals(servletRequest.getRequestURI()))) {
            String encodedRedirectURL = ((HttpServletResponse) response).encodeRedirectURL(
                    servletRequest.getContextPath() + "/notes"
            );
            servletResponse.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
            servletResponse.setHeader("Location", encodedRedirectURL);
        }

        chain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated();
    }
}
