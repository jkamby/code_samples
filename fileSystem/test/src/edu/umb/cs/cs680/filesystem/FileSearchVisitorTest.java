package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
public class FileSearchVisitorTest {

	@Test
	public void typeFileSearchVisitor() throws Exception {
		assertThat(FileSearchVisitor.class, notNullValue());
	}

	@Test
	public void instantiationOfFileSearchVisitor() throws Exception {
		String extension = null;
		FileSearchVisitor target = new FileSearchVisitor(extension);
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(FileSearchVisitor.class)));
	}

	@Test
	public void visitLinkOfFileSearchVisitor() throws Exception {
		String extension = "txt";
		FileSearchVisitor target = new FileSearchVisitor(extension);
		Link link = new Link(new Directory(null, null, null, null));
		target.visit(link);
		int actual = target.getFoundFiles().size();
		int expected = 0;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitDirectoryOfFileSearchVisitor() throws Exception {
		String extension = "txt";
		FileSearchVisitor target = new FileSearchVisitor(extension);
		Link link = new Link(new File(null, null, null));
		File file = new File(null, "helltxt", null);
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		children.add(link); children.add(file);
		Directory dir = new Directory(null, children, null, null);
		target.visit(dir);
		int actual = target.getFoundFiles().size();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void visitFileOfFileSearchVisitor() throws Exception {
		String extension = "txt";
		FileSearchVisitor target = new FileSearchVisitor(extension);
		File file = new File(null, "memo.txt", null);
		target.visit(file);
		int actual = target.getFoundFiles().size();
		int expected = 1;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void getFoundFilesOfFileSearchVisitor() throws Exception {
		String extension = "doc";
		FileSearchVisitor target = new FileSearchVisitor(extension);
		Link link = new Link(new File(null, null, null));
		File file = new File(null, "javadoc", null);
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		children.add(link); children.add(file);
		Directory dir = new Directory(null, children, null, null);
		target.visit(dir);
		ArrayList<FSElement> actual = target.getFoundFiles();
		FSElement expected = file;
		assertTrue(actual.contains(expected));
	}

}
