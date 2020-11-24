package de.quoss.example.spring;

import javax.xml.namespace.QName;

public class HelloQName {

    private QName node;

    void printHello() {
        System.out.format("Hello, QName: %s!", node);
    }

    public void setNode(final QName node) {
        this.node = node;
    }

}
