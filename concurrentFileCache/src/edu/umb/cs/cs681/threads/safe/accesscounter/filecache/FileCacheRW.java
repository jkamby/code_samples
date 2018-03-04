package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;
/**
 * 
 */

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author JohnPaul
 *
 */
public abstract class FileCacheRW {

	private static final int MAX_ENTRIES = 25;
	private HashMap<java.nio.file.Path, CacheMetrix> cache = new HashMap<java.nio.file.Path, CacheMetrix>(MAX_ENTRIES);
	// private ReentrantLock lock = new ReentrantLock();
	ReentrantReadWriteLock rwlock = new ReentrantReadWriteLock();

	/**
	 * 
	 */

	public String fetch(java.nio.file.Path targetPath) {
		rwlock.writeLock().lock();
		try {
			//lock.lock();
			if (!this.cache.containsKey(targetPath))
				if (this.cache.size() < MAX_ENTRIES)
					return cacheFile(targetPath);
				else
					return replace(targetPath);
			rwlock.readLock().lock(); // downgrading
		} finally {
			rwlock.writeLock().unlock();
			// lock.unlock();
		}
		try {
			return this.cache.get(targetPath).getContents(targetPath);
		} finally {
			rwlock.readLock().unlock();
		}
	}

	public String cacheFile(java.nio.file.Path targetPath) {
		String contents = dummyDiskRetrieval(targetPath);
		CacheMetrix temp = new CacheMetrix(contents, targetPath);
		this.cache.put(targetPath, temp);
		return contents;
	}

	protected String dummyDiskRetrieval(java.nio.file.Path filePath) {
		return "Content from " + filePath.toString();
	}

	protected abstract String replace(java.nio.file.Path filePath);

	protected HashMap<java.nio.file.Path, CacheMetrix> cache() {
		return this.cache;
	}

	protected void clear() {
		this.cache.clear();
	}
}
