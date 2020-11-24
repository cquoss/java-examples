package de.quoss.example.mock;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(MockExampleTwo.class)
public class MockExampleTwoTest {
    
    @Test
    public void testGetUtilMethodsOutput() throws Exception {

        MockExampleUtil mockedUtil = Mockito.mock(MockExampleUtil.class);

        PowerMockito.whenNew(MockExampleUtil.class).withAnyArguments().thenReturn(mockedUtil);

        Mockito.when(mockedUtil.methodOne()).thenReturn("MockOne");

        Mockito.when(mockedUtil.methodTwo()).thenCallRealMethod();

        Assert.assertEquals("MockOne/Two", new MockExampleTwo().getUtilMethodOutputs());

    }

}
