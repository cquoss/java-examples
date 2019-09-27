package de.quoss.example.serviceloaderexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ServiceLoaderExampleFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceLoaderExampleFactory.class);

    public ServiceLoaderExampleInterface getInstance() {
        ServiceLoader<ServiceLoaderExampleInterface> serviceLoader = ServiceLoader.load(ServiceLoaderExampleInterface.class);
        Iterator<ServiceLoaderExampleInterface> iterator = serviceLoader.iterator();
        ServiceLoaderExampleInterface serviceLoaderExampleInterface = null;
        while (iterator.hasNext()) {
            serviceLoaderExampleInterface = iterator.next();
            LOGGER.info("Found implementation {}.", serviceLoaderExampleInterface);
        }
        // the last one found wins
        return serviceLoaderExampleInterface;
    }

}
