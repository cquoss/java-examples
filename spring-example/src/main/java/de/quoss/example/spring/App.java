package de.quoss.example.spring;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    private void run() throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        HelloQName helloQName = (HelloQName) context.getBean("helloQName");
        helloQName.printHello();
    }

    public static void main(final String[] args) throws Exception {
        new App().run();
    }

}
