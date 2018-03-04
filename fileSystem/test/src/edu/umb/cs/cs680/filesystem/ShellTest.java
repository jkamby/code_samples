package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ShellTest {
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
	public void typeShell() throws Exception {
		assertThat(Shell.class, notNullValue());
	}

	@Test
	public void instantiationOfShell() throws Exception {
		String prompt = null;
		Shell target = new Shell(prompt);
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(Shell.class)));
	}

	@Test
	public void getFileSystemFromShell() throws Exception {
		String prompt = "JunitTest->";
		Shell target = new Shell(prompt);
		FileSystem actual = target.getFileSystem();
		assertThat(actual, is(instanceOf(FileSystem.class)));
	}

	@Test
	public void processInputOfShell() throws Exception {
		String prompt = "JunitTest->";
		String cmd = "pwd";
		Shell target = new Shell(prompt);
		System.setIn(new ByteArrayInputStream(cmd.getBytes()));
		// target.processInput(); - Not sure how to proceed!!!
		String expected = "Your present working directory is root";
		assertTrue(expected.equals(outContent.toString())); 
	}

}
