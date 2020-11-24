package de.quoss.example.httpserver;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpPrincipal;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Http handler example.
 * 
 * @author Clemens Quoß
 */
public class HttpHandlerExample implements HttpHandler {
    
    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    
    @Override
    public void handle(final HttpExchange exchange) throws IOException {
        
        String methodName = "handle(HttpExchange)";
        LOGGER.debug("{} start [exchange={}]", methodName, exchange);
        
        // context
        HttpContext context = exchange.getHttpContext();
        LOGGER.debug("{} [context={}]", methodName, context);
        Map<String, Object> contextAttributes = context.getAttributes();
        LOGGER.debug("{} [contextAttributes={}]", methodName, contextAttributes);
        Authenticator contextAuthenticator = context.getAuthenticator();
        LOGGER.debug("{} [contextAuthenticator={}]", methodName, contextAuthenticator);
        List<Filter> contextFilters = context.getFilters();
        LOGGER.debug("{} [contextFilters={}]", methodName, contextFilters);
        HttpHandler contextHandler = context.getHandler();
        LOGGER.debug("{} [contextHandler={}]", methodName, contextHandler);
        String contextPath = context.getPath();
        LOGGER.debug("{} [contextPath={}]", methodName, contextPath);
        HttpServer contextServer = context.getServer();
        LOGGER.debug("{} [contextServer={}]", methodName, contextServer);
        
        InetSocketAddress localAddress = exchange.getLocalAddress();
        LOGGER.debug("{} [localAddress={}]", methodName, localAddress);
        
        HttpPrincipal principal = exchange.getPrincipal();
        LOGGER.debug("{} [principal={}]", methodName, principal);
        
        String protocol = exchange.getProtocol();
        LOGGER.debug("{} [protocol={}]", methodName, protocol);
        
        InetSocketAddress remoteAddress = exchange.getRemoteAddress();
        LOGGER.debug("{} [remoteAddress={}]", methodName, remoteAddress);
        
        // request body
        InputStream requestBody = exchange.getRequestBody();
        if (requestBody != null) {
            byte[] buf = new byte[1024];
            int bytesRead;
            StringBuilder builder = new StringBuilder();
            while ((bytesRead = requestBody.read(buf)) != -1) {
                byte[] buf2 = new byte[bytesRead];
                System.arraycopy(buf, 0, buf2, 0, bytesRead);
                builder.append(new String(buf2));
            }
            LOGGER.info("Request body: {}", builder.toString());
        }
        
        Headers requestHeaders = exchange.getRequestHeaders();
        LOGGER.debug("{} [requestHeaders={}]", methodName, requestHeaders);
        
        String requestMethod = exchange.getRequestMethod();
        LOGGER.debug("{} [requestMethod={}]", methodName, requestMethod);
        
        URI requestURI = exchange.getRequestURI();
        LOGGER.debug("{} [requestURI={}]", methodName, requestURI);

        String response = "Hello!";
        exchange.sendResponseHeaders(200, response.getBytes().length);

        OutputStream responseBody = exchange.getResponseBody();
        if (responseBody != null) {
            LOGGER.info("Writing response ...");
            responseBody.write(response.getBytes());
            responseBody.close();
            LOGGER.info("... done.");
        }
        
        LOGGER.debug("{} end", methodName);
        
    }
    
}
