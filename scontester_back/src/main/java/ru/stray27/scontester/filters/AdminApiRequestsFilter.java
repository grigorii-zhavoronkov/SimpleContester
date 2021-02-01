package ru.stray27.scontester.filters;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class AdminApiRequestsFilter implements Filter {

    @Value("${admin.password}")
    private String adminPassword;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getRequestURI().startsWith("/admin/api/") &&
                !adminPassword.equals(req.getHeader("Authorization"))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid password");
            return;
        }
        chain.doFilter(request, response);
    }
}
