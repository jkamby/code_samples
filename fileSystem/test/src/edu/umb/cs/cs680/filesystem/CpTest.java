package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CpTest {
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
	public void typeCp() throws Exception {
		assertThat(Cp.class, notNullValue());
	}

	@Test
	public void instantiationOfCp() throws Exception {
		String target_ = "target";
		String destDir = "destDir";
		Cp target = new Cp(target_, destDir);
		assertThat(target, allOf(notNullValue(), instanceOf(Cp.class)));
	}

	@Test
	public void executeCp() throws Exception {
		String target_ = "target";
		String destDir = "destDir";
		Cp target = new Cp(target_, destDir);
		target.execute();
		String expected = "The target " + target_ + " and destination " + destDir + " cannot be found.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

	@Test
	public void toStringOfCp() throws Exception {
		String target_ = "target";
		String destDir = "destDir";
		Cp target = new Cp(target_, destDir);
		String actual = target.toString();
		String expected = "cp";
		assertThat(actual, is(equalTo(expected)));
	}

}
