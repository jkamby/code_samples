package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimeStampComparatorTest {

	@Test
	public void typeTimeStampComparator() throws Exception {
		assertThat(TimeStampComparator.class, notNullValue());
	}

	@Test
	public void instantiationOfTimeStampComparator() throws Exception {
		TimeStampComparator target = new TimeStampComparator();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(TimeStampComparator.class)));
	}

	@Test
	public void compareFileToFileUsingTimeStampComparator() throws Exception {
		TimeStampComparator target = new TimeStampComparator();
		File o1 = new File(null, "apple", null);
		TimeUnit.SECONDS.sleep(1);
		File o2 = new File(null, "banana", null);
		o2.setLastModified();
		TimeUnit.SECONDS.sleep(1);
		o1.setLastModified();
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}
	
	@Test
	public void compareFileToLinkUsingTimeStampComparator() throws Exception {
		TimeStampComparator target = new TimeStampComparator();
		File o1 = new File(null, "cherry", null);
		TimeUnit.SECONDS.sleep(1);
		Link o2 = new Link(new File(null, "dates", null));
		o2.setLastModified(); // Does nothing unless directly called on object linked to
		TimeUnit.SECONDS.sleep(1);
		o1.setLastModified();
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}

	@Test
	public void compareFileToDirectoryUsingTimeStampComparator() throws Exception {
		TimeStampComparator target = new TimeStampComparator();
		File o1 = new File(null, "eggplant", null);
		TimeUnit.SECONDS.sleep(1);
		Directory o2 = new Directory(null, null, "guava", null);
		o2.setLastModified();
		TimeUnit.SECONDS.sleep(1);
		o1.setLastModified();
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual > expected);
	}
	
	@Test
	public void compareLinkToDirectoryUsingTimeStampComparator() throws Exception {
		TimeStampComparator target = new TimeStampComparator();
		Link o1 = new Link(new File(null, "honey", null));
		TimeUnit.SECONDS.sleep(1);
		Directory o2 = new Directory(null, null, "iguana", null);
		o2.setLastModified();
		TimeUnit.SECONDS.sleep(1);
		o1.setLastModified(); // Does nothing unless directly called on object linked to
		int actual = target.compare(o1, o2);
		int expected = 0;
		assertTrue(actual < expected);
	}

}
