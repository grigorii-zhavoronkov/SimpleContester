package ru.stray27.scontester.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@Component
@Order(1)
public class CorsFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        if (req.getMethod().equalsIgnoreCase("options")) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.addHeader("Access-Control-Allow-Credentials", "true");
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {

    }
}
