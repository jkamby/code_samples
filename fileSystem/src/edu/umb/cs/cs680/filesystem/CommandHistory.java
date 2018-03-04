/**
 * 
 */
package edu.umb.cs.cs680.filesystem;

import java.util.Stack;

/**
 * @author JohnPaul
 *
 */
public class CommandHistory {

	private Stack<Command> history;
	/**
	 * 
	 */
	public CommandHistory() {
		this.history = new Stack<Command>();
	}

	public boolean isEmpty() {
		return this.history.empty();
	}
	
	public void push(Command command) {
		history.push(command);
	}
	
	public Command peek() {
		return history.peek();
	}
	
	public Command pop() {
		return history.pop();
	}
	
	protected int search(Command c) {//reluctantly added this method for testing purposes
		return history.search(c);
	}
}
