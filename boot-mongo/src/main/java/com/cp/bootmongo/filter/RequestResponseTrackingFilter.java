package com.cp.bootmongo.filter;

import lombok.extern.apachecommons.CommonsLog;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@CommonsLog
@Order(-300)
public class RequestResponseTrackingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        if (StringUtils.isEmpty(uri)) {
            filterChain.doFilter(request, response);
            return;
        }
        long startMills = System.currentTimeMillis();
        log.info("Request with URI {" + uri + "} Started");
        filterChain.doFilter(request, response);
        long endMillis = System.currentTimeMillis();
        long result = endMillis - startMills;
        log.info("Request with URI {" + uri + "} Finished in (ms.) : " + result);
    }

}
