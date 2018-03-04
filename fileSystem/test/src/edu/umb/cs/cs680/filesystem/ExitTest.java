package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ExitTest {
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
	public void typeExit() throws Exception {
		assertThat(Exit.class, notNullValue());
	}

	@Test
	public void instantiationOfExit() throws Exception {
		Exit target = new Exit();
		assertThat(target, allOf(notNullValue(), instanceOf(Exit.class)));
	}

	@Test
	public void executeExit() throws Exception {
		Exit target = new Exit();
		target.execute();
		String expected = "Logging out and shutting down. Thank you.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

}
