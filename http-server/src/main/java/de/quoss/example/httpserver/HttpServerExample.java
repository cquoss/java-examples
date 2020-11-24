package de.quoss.example.httpserver;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Http server example.
 * 
 * @author Clemens Quoss
 */
public class HttpServerExample {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LogManager.getLogger();
    
    private void run() throws Exception {
        
        LOGGER.info("Start");
        InetSocketAddress address = new InetSocketAddress(9001);
        HttpServer server = HttpServer.create(address, 0);
        server.createContext("/", new HttpHandlerExample());
        server.start();
        LOGGER.info("End");
    }
    
    public static void main(final String[] args) throws Exception {
        new HttpServerExample().run();
    }
    
}
