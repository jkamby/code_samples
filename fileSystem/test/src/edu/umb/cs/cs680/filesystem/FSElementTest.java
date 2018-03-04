package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
public class FSElementTest {

	@Test
	public void typeFSElement() throws Exception {
		assertThat(FSElement.class, notNullValue());
	}
}
