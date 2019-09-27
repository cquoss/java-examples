package de.quoss.example.serviceloaderexample;

public class ServiceLoaderExampleImplementation implements ServiceLoaderExampleInterface {

    @Override
    public String sayHello() {
        return "Hello, ServiceLoader!";
    }

}
