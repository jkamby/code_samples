package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

public class CommandHistoryTest {

	@Test
	public void typeCommandHistory() throws Exception {
		assertThat(CommandHistory.class, notNullValue());
	}

	@Test
	public void instantiationOfCommandHistory() throws Exception {
		CommandHistory target = new CommandHistory();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(CommandHistory.class)));
	}

	@Test
	public void pushOntoCommandHistory() throws Exception {
		CommandHistory target = new CommandHistory();
		Command command = new Pwd();
		Command command2 = new Ls("");
		target.push(command); target.push(command2);
		int actual = target.search(command);
		int expected = 2;
		assertThat(actual, is(expected));
	}

	@Test
	public void popFromCommandHistory() throws Exception {
		CommandHistory target = new CommandHistory();
		Command command = new Pwd();
		Command command2 = new Ls("");
		target.push(command); target.push(command2);
		Command actual = target.pop();
		Command expected = command2;
		assertThat(actual, is(equalTo(expected)));
	}

}
