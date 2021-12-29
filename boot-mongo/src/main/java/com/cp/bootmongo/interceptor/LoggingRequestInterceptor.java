package com.cp.bootmongo.interceptor;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@CommonsLog
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        if (log.isDebugEnabled()) {
            StringBuilder builder = new StringBuilder();
            builder.append("\nRequest :");
            builder.append("\nURI : " + request.getURI());
            builder.append("\nMethod : " + request.getMethod());
            builder.append("\nMethod value : " + request.getMethodValue());
            builder.append("\nHeaders : \n");
            for (String header : request.getHeaders().keySet()) {
                builder.append(header + " : " + request.getHeaders().get(header) + "\n");
            }
            builder.append("Body : " + new String(body, "UTF-8"));
            log.debug(builder);
        }
        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }

}
