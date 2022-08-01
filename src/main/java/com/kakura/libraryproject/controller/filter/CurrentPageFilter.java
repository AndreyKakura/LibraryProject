package com.kakura.libraryproject.controller.filter;

import com.kakura.libraryproject.controller.command.CommandType;
import com.kakura.libraryproject.controller.command.RequestParameter;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.kakura.libraryproject.controller.command.SessionAttribute.CURRENT_PAGE;

@WebFilter(urlPatterns = {"*.jsp"}, dispatcherTypes = {DispatcherType.FORWARD}
        , initParams = {@WebInitParam(name = "PAGES_ROOT_DIRECTORY", value = "/pages", description = "Pages Param")
        , @WebInitParam(name = "INDEX_PAGE", value = "/index.jsp", description = "Pages Param")})
public class CurrentPageFilter implements Filter {
    private String root;
    private String indexPage;

    @Override
    public void init(FilterConfig filterConfig) {
        root = filterConfig.getInitParameter("PAGES_ROOT_DIRECTORY");
        indexPage = filterConfig.getInitParameter("INDEX_PAGE");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String currentPage = indexPage;
        int rootFirstIndex = requestURI.indexOf(root);
        if (rootFirstIndex != -1) {
            currentPage = requestURI.substring(rootFirstIndex);
        }
        httpRequest.getSession().setAttribute(CURRENT_PAGE, currentPage);
        chain.doFilter(request, response);
    }
}
