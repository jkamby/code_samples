/**
 * 
 */
package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;
/**
 * @author JohnPaul
 *
 */
public class AccessCounter {
	private HashMap<java.nio.file.Path, Integer> cache;
	private static AccessCounter instance = null;
	private static ReentrantLock lock = new ReentrantLock();
	private AccessCounter() {
		cache = new HashMap<java.nio.file.Path, Integer> ();
	}
	
	public static AccessCounter getInstance() {
		if(instance==null){
			lock.lock();
			try {
				instance = new AccessCounter();
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}

	public void increment(java.nio.file.Path path) {
		if (this.cache.containsKey(path)) {
			cache.put(path, cache.get(path) + 1);
			System.out.println("Incremented path " + path.toString() + "'s count.");
		} else {
			cache.put(path, new Integer(1));
			System.out.println("Added " + path.toString() + " to cache.");
		}
	}
	
	public int getCount(java.nio.file.Path path) {
		if (this.cache.containsKey(path)) {
			System.out.println("Retrieving the count of " + path.toString() + ",");
			return cache.get(path).intValue();
		} else {
			System.out.println(path.toString() + "not found in cache.");
			return 0;
		}
	}
}
