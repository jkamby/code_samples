package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class ReverseAlphabeticalComparatorTest {

	@Test
	public void typeReverseAlphabeticalComparator() throws Exception {
		assertThat(ReverseAlphabeticalComparator.class, notNullValue());
	}

	@Test
	public void instantiationOfReverseAlphabeticalComparator() throws Exception {
		ReverseAlphabeticalComparator target = new ReverseAlphabeticalComparator();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(ReverseAlphabeticalComparator.class)));
	}

	@Test
	public void compareFileToFileUsingReverseAlphabeticalComparator() throws Exception {
		ReverseAlphabeticalComparator target = new ReverseAlphabeticalComparator();
		File o1 = new File(null, "apple", null);
		File o2 = new File(null, "banana", null);
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}
	
	@Test
	public void compareFileToLinkUsingReverseAlphabeticalComparator() throws Exception {
		ReverseAlphabeticalComparator target = new ReverseAlphabeticalComparator();
		File o1 = new File(null, "cherry", null);
		Link o2 = new Link(new File(null, "dates", null));
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}

	@Test
	public void compareFileToDirectoryUsingReverseAlphabeticalComparator() throws Exception {
		ReverseAlphabeticalComparator target = new ReverseAlphabeticalComparator();
		File o1 = new File(null, "eggplant", null);
		Directory o2 = new Directory(null, null, "guava", null);
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}
	
	@Test
	public void compareLinkToDirectoryUsingReverseAlphabeticalComparator() throws Exception {
		ReverseAlphabeticalComparator target = new ReverseAlphabeticalComparator();
		Link o1 = new Link(new File(null, "honey", null));
		Directory o2 = new Directory(null, null, "iguana", null);
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}
}
