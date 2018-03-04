package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;

import java.util.ArrayList;
public class MkDirTest {

	@Test
	public void typeMkDir() throws Exception {
		assertThat(MkDir.class, notNullValue());
	}

	@Test
	public void instantiationOfMkDir() throws Exception {
		String dir = "test";
		MkDir target = new MkDir(dir);
		assertThat(target, allOf(notNullValue(),(instanceOf(MkDir.class))));
	}

	@Test
	public void executeMkDir() throws Exception {
		String dir = "test";
		MkDir target = new MkDir(dir);
		target.execute();
		FileSystem fileSystem = FileSystem.getInstance();
		Directory pwd = fileSystem.getCurrent();
		ArrayList<FSElement> actual = pwd.getChildren();
		assertThat(actual, hasItem(fileSystem.findDirectory(dir)));
	}

	@Test
	public void toStringOfMkDir() throws Exception {
		String dir = "test";
		MkDir target = new MkDir(dir);
		String actual = target.toString();
		String expected = "mkdir";
		assertThat(actual, is(equalTo(expected)));
	}

}
