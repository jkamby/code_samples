package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;
/**
 * 
 */


import java.nio.file.Path;

/**
 * @author JohnPaul
 *
 */
public class LFUFileCacheRW extends FileCacheRW {
	
	private static LFUFileCacheRW instance = null;
	private LFUFileCacheRW() {}
	
	
	public static LFUFileCacheRW getInstance() {
		if(instance == null)
			instance = new LFUFileCacheRW();
		return instance;
	}
	
	@Override
	protected String replace(Path filePath) {
		CacheMetrix minE = new CacheMetrix(null, filePath);
		for(CacheMetrix e: LFUFileCacheRW.instance.cache().values()) {
			if(minE.getCount(minE.getPath()) == 0)
				minE = e;
			else if (e.getCount(e.getPath()) < minE.getCount(minE.getPath()))
				minE = e;
		}
		LFUFileCacheRW.instance.cache().values().remove(minE);
		for(CacheMetrix c: LFUFileCacheRW.instance.cache().values())
			c.setIndex(c.getIndex() - 1);
		CacheMetrix newEntry = new CacheMetrix(this.dummyDiskRetrieval(filePath), filePath);
		newEntry.setIndex(LFUFileCacheRW.instance.cache().size());
		LFUFileCacheRW.instance.cache().put(filePath, newEntry);
		return newEntry.getContents(filePath);
	}
}
