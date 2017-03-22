import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

public class MockTest {

    @BeforeMethod
    public void init()
    {
        MockitoAnnotations.initMocks(MockTest.class);
    }

    @Test
    public void shouldVerifyListElements()
    {
        List mockedList = mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void shouldThrowRuntimeException()
    {
        //You can mock concrete classes, not just interfaces
        LinkedList mockedList = mock(LinkedList.class);

        //stubbing
        when(mockedList.get(0)).thenReturn("firstElement");
        when(mockedList.get(1)).thenThrow(new RuntimeException());

        //following prints "first"
        System.out.println(mockedList.get(0));

        // following verifies mockedList.get(0) method called at least 1 time
        verify(mockedList, atLeast(1)).get(0);

        // following verifies mockedList.get(0) method called exactly 1 time
        verify(mockedList, times(1)).get(0);

        //following throws runtime exception
        System.out.println(mockedList.get(1));

    }

    @Test
    public void shouldVerifyMethodInvocations()
    {
        LinkedList mockedList = mock(LinkedList.class);

        //using mock
        mockedList.add("once");

        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        //following two verifications work exactly the same - times(1) is used by default
        verify(mockedList).add("once");
        verify(mockedList, times(1)).add("once");

        //exact number of invocations verification
        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //verification using never(). never() is an alias to times(0)
        verify(mockedList, never()).add("never happened");

        //verification using atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atMost(5)).add("three times");
    }

    @Test
    public void shouldValidateArgumentCaptor()
    {
        Stack<String> mockedStack = mock(Stack.class);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        mockedStack.add("Java Code Geeks");
        verify(mockedStack).add(argumentCaptor.capture());
        assertEquals("Java Code Geeks", argumentCaptor.getValue());
    }

}
