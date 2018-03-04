package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SizeCountingVisitorTest {

	@Test
	public void typeSizeCountingVisitor() throws Exception {
		assertThat(SizeCountingVisitor.class, notNullValue());
	}

	@Test
	public void instantiationOfSizeCountingVisitor() throws Exception {
		SizeCountingVisitor target = new SizeCountingVisitor();
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(SizeCountingVisitor.class)));
	}

	@Test
	public void visitLinkOfSizeCountingVisitor() throws Exception {
		SizeCountingVisitor target = new SizeCountingVisitor();
		Link link = new Link(new File(null, null, null));
		target.visit(link);
		int actual = target.getTotalSize();
		int expected = 0;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitDirectoryOfSizeCountingVisitor() throws Exception {
		SizeCountingVisitor target = new SizeCountingVisitor();
		Link link = new Link(new File(null, null, null));
		File file = new File(null, null, null);
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		children.add(link); children.add(file);
		Directory dir = new Directory(null, children, null, null);
		target.visit(dir);
		int actual = target.getTotalSize();
		int expected = 5;
		assertThat(actual, is(expected));
	}

	@Test
	public void visitFileOfSizeCountingVisitor() throws Exception {
		SizeCountingVisitor target = new SizeCountingVisitor();
		File file = new File(null, null, null);
		target.visit(file);
		int actual = target.getTotalSize();
		int expected = 5;
		assertThat(actual, is(equalTo(expected)));
	}

}
