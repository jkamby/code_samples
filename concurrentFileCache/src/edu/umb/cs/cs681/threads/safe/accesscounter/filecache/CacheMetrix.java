/**
 * 
 */
package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;

import java.util.Date;

/**
 * @author JohnPaul
 *
 */
public class CacheMetrix {

	private int index;
	// private int frequency;
	private AccessCounter accessCounter;
	private Date lastUsed;
	private String contents;
	private java.nio.file.Path path;
	/**
	 * 
	 */
	public CacheMetrix(String contents, java.nio.file.Path path) {
		this.index = 0;
		// this.frequency = 0;
		this.accessCounter = AccessCounter.getInstance();
		this.lastUsed = null;
		this.contents = contents;
		this.path = path;
	}
	
	public int getIndex() {
		return this.index;
	}
	
	/*public int getFrequency() {
		return this.frequency;
	}*/
	
	public int getCount(java.nio.file.Path path) {
		return this.accessCounter.getCount(path);
	}
	
	public Date getDate() {
		return this.lastUsed;
	}
	
	public String getContents(java.nio.file.Path path) {
		incrementCount(path);
		setLastUsed();
		return this.contents;
	}
	
	public java.nio.file.Path getPath() {
		return this.path;
	}
	
	/*public String getFileName() {
		return this.file.getName();
	}*/

	public void setIndex(int newIndex) {
		this.index = newIndex;
	}
	
	public void incrementCount(java.nio.file.Path path) {
		this.accessCounter.increment(path);
	}
	
	public void setLastUsed() {
		this.lastUsed = new Date();
	}
	
	/*public void putFile(File file) {
		this.file = file;
		setLastUsed();
	}*/
}
