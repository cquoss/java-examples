package de.quoss.example.mock;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class MockExampleTest {
    
    @Test
    public void testGetUtilMethosOutput() {

        MockExampleUtil mockedUtil = Mockito.mock(MockExampleUtil.class);

        Mockito.when(mockedUtil.methodOne()).thenReturn("MockOne");

        Assert.assertEquals("MockOne/Two", new MockExample(mockedUtil).getUtilMethodOutputs());

    }

}
