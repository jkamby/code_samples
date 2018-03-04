package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
public class LsTest {
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
	public void typeLs() throws Exception {
		assertThat(Ls.class, notNullValue());
	}

	@Test
	public void instantiationOfLs() throws Exception {
		String dir = null;
		Ls target = new Ls(dir);
		assertThat(target, notNullValue());
	}

	@Test
	public void executeLs() throws Exception {
		Directory root = new Directory (null, new ArrayList<FSElement>(), "root", "System");
		FileSystem jpksys = FileSystem.getInstance();
		jpksys.setRoot(root);
		Ls target = new Ls("non-ExistentDir");
		target.execute();
		String expected = "Unrecognized directory.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

}
