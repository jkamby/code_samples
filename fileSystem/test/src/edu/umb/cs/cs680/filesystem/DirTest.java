package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class DirTest {
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
	public void typeDir() throws Exception {
		assertThat(Dir.class, notNullValue());
	}

	@Test
	public void instantiationOfDir() throws Exception {
		String fse = "fse";
		Dir target = new Dir(fse);
		assertThat(target, allOf(notNullValue(), instanceOf(Dir.class)));
	}

	@Test
	public void executeDir() throws Exception {
		String fse = "fse";
		Dir target = new Dir(fse);
		target.execute();
		String expected = "Unrecognized directory.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

	@Test
	public void toStringDir() throws Exception {
		String fse = "fse";
		Dir target = new Dir(fse);
		String actual = target.toString();
		String expected = "dir";
		assertThat(actual, is(equalTo(expected)));
	}

}
