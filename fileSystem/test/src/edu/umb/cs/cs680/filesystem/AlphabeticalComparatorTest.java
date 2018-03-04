package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class AlphabeticalComparatorTest {

	@Test
	public void typeAlphabeticalComparator() throws Exception {
		assertThat(AlphabeticalComparator.class, notNullValue());
	}

	@Test
	public void instantiationOfAlphabeticalComparator() throws Exception {
		AlphabeticalComparator target = new AlphabeticalComparator();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(AlphabeticalComparator.class)));
	}

	@Test
	public void compareFileToFileUsingAlphabeticalComparator() throws Exception {
		AlphabeticalComparator target = new AlphabeticalComparator();
		File o1 = new File(null, "apple", null);
		File o2 = new File(null, "banana", null);
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual < expected);
	}
	
	@Test
	public void compareFileToLinkUsingAlphabeticalComparator() throws Exception {
		AlphabeticalComparator target = new AlphabeticalComparator();
		File o1 = new File(null, "cherry", null);
		Link o2 = new Link(new File(null, "dates", null));
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual < expected);
	}

	@Test
	public void compareFileToDirectoryUsingAlphabeticalComparator() throws Exception {
		AlphabeticalComparator target = new AlphabeticalComparator();
		File o1 = new File(null, "eggplant", null);
		Directory o2 = new Directory(null, null, "guava", null);
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual < expected);
	}
	
	@Test
	public void compareLinkToDirectoryUsingAlphabeticalComparator() throws Exception {
		AlphabeticalComparator target = new AlphabeticalComparator();
		Link o1 = new Link(new File(null, "honey", null));
		Directory o2 = new Directory(null, null, "iguana", null);
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual < expected);
	}
}
