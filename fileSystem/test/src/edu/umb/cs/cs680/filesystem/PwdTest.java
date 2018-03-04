package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class PwdTest {
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
	public void typePwd() throws Exception {
		assertThat(Pwd.class, notNullValue());
	}

	@Test
	public void instantiationOfPwd() throws Exception {
		Pwd target = new Pwd();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(Pwd.class)));
	}

	@Test
	public void executePwdCommand() throws Exception {
		Directory root = new Directory (null, new ArrayList<FSElement>(), "root", "System");
		FileSystem jpksys = FileSystem.getInstance();
		jpksys.setRoot(root);
		Pwd target = new Pwd();
		target.execute();
		String expected = "Your present working directory is root\r\n";
		assertTrue(expected.equals(outContent.toString())); // - Gamble...
	}

}
