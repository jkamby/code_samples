package edu.umb.cs.cs680.filesystem;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class FileTest {

	@Test
	public void typeFile() throws Exception {
		assertThat(File.class, notNullValue());
	}

	@Test
	public void instantiationOfFile() throws Exception {
		Directory parent = null;
		String name = null;
		String owner = null;
		File target = new File(parent, name, owner);
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(File.class)));
	}

	@Test
	public void getNameOfFile() throws Exception {
		Directory parent = null;
		String name = "Hot Stuff";
		String owner = null;
		File target = new File(parent, name, owner);
		String actual = target.getName();
		String expected = "Hot Stuff";
		assertTrue(actual.equals(expected));
	}

	@Test
	public void setNameOfFile() throws Exception {
		Directory parent = null;
		String name = "Hot Stuff";
		String owner = null;
		File target = new File(parent, name, owner);
		target.setName("Cold Stuff");
		String actual = target.getName();
		String expected = "Cold Stuff";
		assertThat(actual, is(equalTo(expected)));
	}
	
	@Test
	public void getOwnerOfFile() throws Exception {
		Directory parent = null;
		String name = "Hot Stuff";
		String owner = "Mr. Fresh";
		File target = new File(parent, name, owner);
		String actual = target.getOwner();
		String expected = owner;
		assertThat(actual, is(expected));
	}

	@Test
	public void setOwnerOfFile() throws Exception {
		Directory parent = null;
		String name = "Hot Stuff";
		String owner = "Mr. Fresh";
		File target = new File(parent, name, owner);
		target.setOwner("Mrs. Rotten");
		String actual = target.getOwner();
		String expected = "Mrs. Rotten";
		assertThat(actual, is(equalTo(expected)));
	}
	
	@Test
	public void getParentOfFile() throws Exception {
		Directory parent = new Directory(null, null, null, null);
		String name = "Hot Stuff";
		String owner = null;
		File target = new File(parent, name, owner);
		Object actual = target.getParent();
		Object expected = parent;
		assertThat(actual, is(expected));
	}

	@Test
	public void setParentOfFile() throws Exception {
		Directory parent = new Directory(null, null, null, null);
		String name = null;
		String owner = null;
		File target = new File(parent, name, owner);
		Directory newParent = new Directory(null, null, null, null);
		target.setParent(newParent);
		Object actual = target.getParent();
		Object expected = newParent;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void isFileOnFile() throws Exception {
		Directory parent = null;
		String name = null;
		String owner = null;
		File target = new File(parent, name, owner);
		boolean actual = target.isFile();
		boolean expected = true;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void setLastModifiedOfFile() throws Exception {
		Directory parent = null;
		String name = null;
		String owner = null;
		File target = new File(parent, name, owner);
		Date aLittleBefore = new Date();
		TimeUnit.SECONDS.sleep(1);// difference between success and failure of test
		target.setLastModified();
		TimeUnit.SECONDS.sleep(1);
		Date aLittleAfter = new Date();
		Date actual = target.getLastModified();
		assertTrue(actual.after(aLittleBefore));
		assertTrue(actual.before(aLittleAfter));
	}

}
