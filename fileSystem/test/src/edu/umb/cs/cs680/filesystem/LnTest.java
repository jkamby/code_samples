package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LnTest {
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
	public void typeLn() throws Exception {
		assertThat(Ln.class, notNullValue());
	}

	@Test
	public void instantiationOfLn() throws Exception {
		String target_ = "target";
		String link = "link";
		Ln target = new Ln(target_, link);
		assertThat(target, allOf(notNullValue(), instanceOf(Ln.class)));
	}

	@Test
	public void executeLn() throws Exception {
		String target_ = "home";
		String link = "link";
		Ln target = new Ln(target_, link);
		target.execute();
		String expected = "The target " + target_ + " cannot be found.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

	@Test
	public void toStringOfLn() throws Exception {
		String target_ = "target";
		String link = "link";
		Ln target = new Ln(target_, link);
		String actual = target.toString();
		String expected = "ln";
		assertThat(actual, is(equalTo(expected)));
	}

}
