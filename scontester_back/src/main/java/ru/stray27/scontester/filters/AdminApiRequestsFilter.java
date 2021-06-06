package ru.stray27.scontester.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.stray27.scontester.annotations.CustomProperty;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@Order(2)
public class AdminApiRequestsFilter implements Filter {

    @CustomProperty("admin.password")
    private String adminPassword;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        if (req.getMethod().toLowerCase().equals("options")) {
            resp.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
            return;
        }
        if (req.getRequestURI().startsWith("/admin/api/") &&
                !adminPassword.equals(req.getHeader("Authorization"))) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid password");
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
