package de.quoss.example.mock;

public class MockExampleTwo {
    
    public String getUtilMethodOutputs() {

        MockExampleUtil util = new MockExampleUtil();

        return String.format("%s/%s", util.methodOne(), util.methodTwo());

    }


}
