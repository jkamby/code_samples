package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
public class VirusCheckingVisitorTest {

	@Test
	public void typeVirusCheckingVisitor() throws Exception {
		assertThat(VirusCheckingVisitor.class, notNullValue());
	}

	@Test
	public void instantiationOfVirusCheckingVisitor() throws Exception {
		VirusCheckingVisitor target = new VirusCheckingVisitor();
		assertThat(target, notNullValue());
	}

	@Test
	public void visitLinkOfVirusCheckingVisitor() throws Exception {
		VirusCheckingVisitor target = new VirusCheckingVisitor();
		Link link = new Link(new Directory(null, null, null, null));
		target.visit(link);
		int actual = target.getQuarantinedNum();
		int expected = 0;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitDirectoryOfVirusCheckingVisitor() throws Exception {
		VirusCheckingVisitor target = new VirusCheckingVisitor();
		Link link = new Link(new File(null, "null1", "own1"));
		File file = new File(null, "null2", "own2");
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		children.add(link); children.add(file);
		Directory dir = new Directory(null, children, "null3", "own3");
		target.visit(dir);
		int actual = target.getQuarantinedNum();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitFileOfVirusCheckingVisitor() throws Exception {
		VirusCheckingVisitor target = new VirusCheckingVisitor();
		File file = new File(null, "null4", "own4");
		target.visit(file);
		int actual = target.getQuarantinedNum();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void getQuarantinedNumOfVirusCheckingVisitor() throws Exception {
		VirusCheckingVisitor target = new VirusCheckingVisitor();
		int actual = target.getQuarantinedNum();
		int expected = 0;
		assertThat(actual, is(equalTo(expected)));
	}

}
