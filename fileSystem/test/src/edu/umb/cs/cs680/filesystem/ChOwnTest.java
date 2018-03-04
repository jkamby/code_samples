package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ChOwnTest {
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
	public void typeChOwn() throws Exception {
		assertThat(ChOwn.class, notNullValue());
	}

	@Test
	public void instantiationOfChOwn() throws Exception {
		String target_ = "target";
		String destDir = "destDir";
		ChOwn target = new ChOwn(target_, destDir);
		assertThat(target, allOf(notNullValue(), instanceOf(ChOwn.class)));
	}

	@Test
	public void executeChOwn() throws Exception {
		String target_ = "target";
		String destDir = "destDir";
		ChOwn target = new ChOwn(target_, destDir);
		target.execute();
		String expected = "The target " + target_ + " cannot be found.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

	@Test
	public void toStringOfChOwn() throws Exception {
		String target_ = "target";
		String destDir = "destDir";
		ChOwn target = new ChOwn(target_, destDir);
		String actual = target.toString();
		String expected = "chown";
		assertThat(actual, is(equalTo(expected)));
	}

}
