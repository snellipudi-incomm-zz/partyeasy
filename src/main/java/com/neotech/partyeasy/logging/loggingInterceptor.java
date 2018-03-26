package com.neotech.partyeasy.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class LoggingInterceptor implements ClientHttpRequestInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());
    private static final AtomicLong atomicLong = new AtomicLong(1);
    private static final ThreadLocal<Long> tlRequestId = new ThreadLocal<>();

    public ClientHttpResponse intercept(HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution) throws IOException {
        tlRequestId.set(atomicLong.getAndIncrement());

        traceRequest(httpRequest, body);
        ClientHttpResponse clientHttpResponse = clientHttpRequestExecution.execute(httpRequest, body);
        if (!(clientHttpResponse instanceof MockClientHttpResponse)){
            traceResponse(clientHttpResponse);
        }
        return clientHttpResponse;
    }

    private void traceRequest(HttpRequest request, byte[] body) throws IOException {
        logger.info("\n" +
                "ID: {}\nURI : {}\nMethod : {}\nRequest Headers: {}\nRequest body: {}"
                ,tlRequestId.get(), request.getURI(), request.getMethod(), request.getHeaders(), new String(body));
    }

    private void traceResponse(ClientHttpResponse response) throws IOException {
        if (response.getBody() != null) {
            StringBuilder inputStringBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), "UTF-8"));
            String line = bufferedReader.readLine();
            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
            logger.info("\n" +
                    "ID: {}\nStatus code : {}\nStatus text : {}\nResponse Headers: {}\nResponse body: {}"
                    , tlRequestId.get(), response.getStatusCode(), response.getStatusText(), response.getHeaders(), inputStringBuilder.toString());
        } else {
            logger.info("no response from outbound");
        }
        tlRequestId.remove();
    }
}
