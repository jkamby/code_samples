package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class FileSystemTest {

	@Test
	public void typeFileSystem() throws Exception {
		assertThat(FileSystem.class, notNullValue());
	}
	
	@Test
	public void getFileSystem() throws Exception {
		FileSystem target = FileSystem.getInstance();
		FileSystem actual = target.getFileSystem();
		Object expected = target;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void getInstanceOfFileSystem() throws Exception {
		FileSystem actual = FileSystem.getInstance();
		assertThat(actual, is(instanceOf(FileSystem.class)));
	}
	
	@Test
	public void getRootAndsetRootOfFileSystem() throws Exception {
		Directory dir = new Directory(null, null, null, null);
		FileSystem target = FileSystem.getInstance();
		target.setRoot(dir);
		Directory actual = target.getRoot();
		Directory expected = dir;
		assertThat(actual, is(equalTo(expected)));
	}
	
	@Test
	public void showAllElementsFileSystem() throws Exception {
	}

}
