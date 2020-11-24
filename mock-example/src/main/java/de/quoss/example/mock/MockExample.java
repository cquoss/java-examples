package de.quoss.example.mock;

public class MockExample {
    
    private MockExampleUtil util;

    public MockExample(final MockExampleUtil util) {

        super();

        this.util = util; 

    }

    public String getUtilMethodOutputs() {

        return String.format("%s/%s", util.methodOne(), util.methodTwo());

    }


}
