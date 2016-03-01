package Study.Mockito2;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.mockito.ArgumentMatcher;

import static org.mockito.Mockito.*;

public class BehaviorVerifyTest {

	@Test
	public void verifyListBehavior() {
		String sOne = "one";
		String newSone = new String("one");

		// System.out.println(sOne == newSone);

		List<String> mockedList = mock(List.class);
		mockedList.add(sOne);
		mockedList.clear();

		verify(mockedList).add(newSone);
		verify(mockedList).clear();

	}

	@Test
	public void verifyStub() {
		LinkedList<String> mockedList = mock(LinkedList.class);

		when(mockedList.get(0)).thenReturn("first");
		when(mockedList.get(1)).thenThrow(new RuntimeException());

		System.out.println(mockedList.get(0));

		System.out.println(mockedList.get(2));

		verify(mockedList).get(0);
	}

	@Test
	public void verifyArgMatch() {
		LinkedList<String> mockedList = mock(LinkedList.class);

		// stubbing using built-in anyInt() argument matcher
		when(mockedList.get(anyInt())).thenReturn("element");

		// stubbing using custom matcher (let's say isValid() returns your own
		// matcher implementation):
		// when(mockedList.contains(argThat(isValid()))).thenReturn("element");

		// following prints "element"
		System.out.println(mockedList.get(999));

		// you can also verify using an argument matcher
		verify(mockedList).get(anyInt());
	}

	class myArgumentMatcher<T> implements ArgumentMatcher<T> {

		public boolean matches(Object argument) {
			return true;
		}

	}

	@Test
	public void verifyNoOfInvocation() {
		LinkedList<String> mockedList = mock(LinkedList.class);

		// using mock
		mockedList.add("once");

		mockedList.add("twice");
		mockedList.add("twice");

		mockedList.add("three times");
		mockedList.add("three times");
		mockedList.add("three times");

		// following two verifications work exactly the same - times(1) is used
		// by default
		verify(mockedList).add("once");
		verify(mockedList, times(1)).add("once");

		// exact number of invocations verification
		verify(mockedList, times(2)).add("twice");
		verify(mockedList, times(3)).add("three times");

		// verification using never(). never() is an alias to times(0)
		verify(mockedList, never()).add("never happened");

		// verification using atLeast()/atMost()
		verify(mockedList, atLeastOnce()).add("three times");
		verify(mockedList, atLeast(2)).add("three times");
		verify(mockedList, atMost(5)).add("three times");

	}

}
