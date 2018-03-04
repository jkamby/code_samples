package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RmDirTest {
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	    System.setErr(null);
	}

	@Test
	public void typeRmDir() throws Exception {
		assertThat(RmDir.class, notNullValue());
	}

	@Test
	public void instantiationOfRmDir() throws Exception {
		String dir = "test";
		RmDir target = new RmDir(dir);
		assertThat(target, allOf(notNullValue(), instanceOf(RmDir.class)));
	}

	@Test
	public void executeRmDir() throws Exception {
		String dir = "nonExistentDir";
		RmDir target = new RmDir(dir);
		target.execute();
		String expected = "The directory " + dir + " cannot be found.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

	@Test
	public void toStringOfRmDir() throws Exception {
		String dir = "nonExistentDir";
		RmDir target = new RmDir(dir);
		String actual = target.toString();
		String expected = "rmdir";
		assertThat(actual, is(equalTo(expected)));
	}

}
