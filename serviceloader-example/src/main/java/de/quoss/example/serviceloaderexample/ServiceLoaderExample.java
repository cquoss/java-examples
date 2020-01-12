package de.quoss.example.serviceloaderexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

/**
 * Example to check whether a ServiceLoader Factory returns different objects on subsequent calls or not.
 */
public class ServiceLoaderExample {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLoaderExample.class);

    private void run() throws ServiceLoaderExampleException {
        // run the factory two times
        LOGGER.info(ServiceLoaderExampleFactory.getImplementation().sayHello());
        LOGGER.info(ServiceLoaderExampleFactory.getImplementation().sayHello());
    }

    public static void main(String[] args) {
        try {
            new ServiceLoaderExample().run();
        } catch (ServiceLoaderExampleException e) {
            LOGGER.error("", e);
            System.exit(1);
        }
    }

}
