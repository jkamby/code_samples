package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class CountingVisitorTest {

	@Test
	public void typeCountingVisitor() throws Exception {
		assertThat(CountingVisitor.class, notNullValue());
	}

	@Test
	public void instantiationOfCountingVisitor() throws Exception {
		CountingVisitor target = new CountingVisitor();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(CountingVisitor.class)));
	}

	@Test
	public void visitLinkOfCountingVisitor() throws Exception {
		CountingVisitor target = new CountingVisitor();
		Link link = new Link(new Directory(null, null, null, null));
		target.visit(link);
		int actual = target.getLinkNum();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitDirectoryOfCountingVisitor() throws Exception {
		CountingVisitor target = new CountingVisitor();
		Link link = new Link(new File(null, null, null));
		File file = new File(null, null, null);
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		children.add(link); children.add(file);
		Directory dir = new Directory(null, children, null, null);
		target.visit(dir);
		int actual = target.getDirNum();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitFileOfCountingVisitor() throws Exception {
		CountingVisitor target = new CountingVisitor();
		File file = new File(null, null, null);
		target.visit(file);
		int actual = target.getFileNum();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

}
