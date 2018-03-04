package edu.umb.cs.cs680.filesystem;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;
public class DirectoryTest {

	@Test
	public void typeDirectory() throws Exception {
		assertThat(Directory.class, notNullValue());
	}

	@Test
	public void instantiationOfDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = null;
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		assertThat(target, notNullValue());
		assertThat(target, is(instanceOf(FSElement.class)));
	}

	@Test
	public void getParentOfDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = null;
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		Object actual = target.getParent();
		assertThat(actual, is(parent));
	}

	@Test
	public void setParentOfDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = null;
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		Directory parent1 = null;
		ArrayList<FSElement> children1 = null;
		String name1 = null;
		String owner1 = null;
		Directory newParent = new Directory(parent1, children1, name1, owner1);
		target.setParent(newParent);
		Directory actual = target.getParent();
		assertThat(actual, is(newParent));
	}

	@Test
	public void getChildrenOfDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		String name = null;
		String owner = null;
		Directory parent1 = null;
		ArrayList<FSElement> children1 = null;
		String name1 = null;
		String owner1 = null;
		Directory child1 = new Directory(parent1, children1, name1, owner1);
		Directory parent2 = null;
		String name2 = null;
		String owner2 = null;
		File child2 = new File(parent2, name2, owner2);
		children.add(child1); children.add(child2);
		Directory target = new Directory(parent, children, name, owner);
		ArrayList<FSElement> actual = target.getChildren();
		ArrayList<FSElement> expected = children;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void appendChildToADirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		Directory parent1 = null;
		ArrayList<FSElement> children1 = new ArrayList<FSElement>();
		String name1 = null;
		String owner1 = null;
		Directory child = new Directory(parent1, children1, name1, owner1);
		target.appendChild(child);
		ArrayList<FSElement> actual = target.getChildren();
		Directory expected = child;
		assertThat(actual.size(), is(equalTo(1)));
		assertThat(actual.get(0), is(expected));
	}

	@Test
	public void addChildrenToDirectory() throws Exception {
		Directory target = new Directory(null, new ArrayList<FSElement>(), "mother", null);
		Directory child1 = new Directory(null, null, "daughter", null);
		File child2 = new File(null, "son", null);
		ArrayList<FSElement> newChildren = new ArrayList<FSElement>();
		newChildren.add(child1); newChildren.add(child2);
		target.addChildren(newChildren);
		ArrayList<FSElement> actual = target.getChildren();
		ArrayList<FSElement> expected = newChildren;
		assertThat(actual, is(expected));
	}

	@Test
	public void removeChildFromDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = new ArrayList<FSElement>();
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		Directory parent1 = null;
		ArrayList<FSElement> children1 = new ArrayList<FSElement>();
		String name1 = null;
		String owner1 = null;
		Directory child1 = new Directory(parent1, children1, name1, owner1);
		Directory parent2 = null;
		String name2 = null;
		String owner2 = null;
		File child2 = new File(parent2, name2, owner2);
		ArrayList<FSElement> newChildren = new ArrayList<FSElement>();
		newChildren.add(child1); newChildren.add(child2);
		target.addChildren(newChildren);
		FSElement unChild = child1;
		target.removeChild(unChild);
		ArrayList<FSElement> actual = target.getChildren();
		File expected = child2;
		assertThat(actual.size(), is(equalTo(1)));
		assertThat(actual.get(0), is(expected));
	}

	@Test
	public void isFileOnDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = null;
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		boolean actual = target.isFile();
		boolean expected = false;
		assertThat(actual, is(equalTo(expected)));
	}

	@Test
	public void setLastModifiedOnDirectory() throws Exception {
		Directory parent = null;
		ArrayList<FSElement> children = null;
		String name = null;
		String owner = null;
		Directory target = new Directory(parent, children, name, owner);
		Date aLittleBefore = new Date();
		TimeUnit.SECONDS.sleep(1);
		target.setLastModified();
		TimeUnit.SECONDS.sleep(1);
		Date aLittleAfter = new Date();
		Date actual = target.getLastModified();
		assertTrue(actual.after(aLittleBefore));
		assertTrue(actual.before(aLittleAfter));
	}

}
