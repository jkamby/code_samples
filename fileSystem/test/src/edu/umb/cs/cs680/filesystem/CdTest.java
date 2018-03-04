package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CdTest {
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
	public void typeCd() throws Exception {
		assertThat(Cd.class, notNullValue());
	}

	@Test
	public void instantiationOfCd() throws Exception {
		String toDir = null;
		Cd target = new Cd(toDir);
		assertThat(target, notNullValue());
	}

	@Test
	public void executeCd() throws Exception {
		Directory root = new Directory (null, new ArrayList<FSElement>(), "root", "System");
		FileSystem jpksys = FileSystem.getInstance();
		jpksys.setRoot(root);
		Cd target = new Cd("non-ExistentDir");
		target.execute();
		String expected = "Directory could not be found.\r\n";
		assertTrue(expected.equals(outContent.toString()));
	}

}
