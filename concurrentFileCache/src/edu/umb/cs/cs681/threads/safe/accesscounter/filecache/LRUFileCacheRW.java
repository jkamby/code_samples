package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;
/**
 * 
 */


import java.nio.file.Path;

/**
 * @author JohnPaul
 *
 */
public class LRUFileCacheRW extends FileCacheRW {
	
	private static LRUFileCacheRW instance = null;
	private LRUFileCacheRW() {}
	
	
	public static LRUFileCacheRW getInstance() {
		if(instance == null)
			instance = new LRUFileCacheRW();
		return instance;
	}
	
	@Override
	protected String replace(Path filePath) {
		CacheMetrix oldE = new CacheMetrix(null, filePath);
		for(CacheMetrix e: LRUFileCacheRW.instance.cache().values()) {
			if(oldE.getDate() == null)
				oldE = e;
			else if (e.getDate().before(oldE.getDate()))
				oldE = e;
		}
		LRUFileCacheRW.instance.cache().values().remove(oldE);
		for(CacheMetrix c: LRUFileCacheRW.instance.cache().values())
			c.setIndex(c.getIndex() - 1);
		CacheMetrix newEntry = new CacheMetrix(this.dummyDiskRetrieval(filePath), filePath);
		newEntry.setIndex(LRUFileCacheRW.instance.cache().size());
		LRUFileCacheRW.instance.cache().put(filePath, newEntry);
		return newEntry.getContents(filePath);
	}
}
