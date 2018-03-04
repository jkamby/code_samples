package edu.umb.cs.cs681.threads.safe.accesscounter.filecache;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RequestHandler implements Runnable{
	private int from, to;
	private volatile boolean done = false;
	private FileCache cache;
	// private AccessCounter accessC;
	// private ArrayList<java.nio.file.Path> files;
	// ReentrantLock lock = new ReentrantLock();
	// ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

	public RequestHandler(int from, int to){
		this.from = from;
		this.to = to; 
		//this.files = new ArrayList<java.nio.file.Path>();
		this.cache = FIFOFileCache.getInstance(); // This might as well have been LRU or LFU
		for (int i = from; i <= to; i++)
			this.cache.fetch(Paths.get(Integer.toString(i) + ".htm"));
			// this.files.add(Paths.get(Integer.toString(i) + ".htm"));
		
		//accessC = AccessCounter.getInstance();
	}
	
	public void setDone() {
		this.done = true;
	}
	
	public void run(){
		
		int x = (new java.util.Random ()).nextInt(this.to); // hard-coded coz I know how many AccessCounter calls.
		String y = this.cache.fetch(Paths.get(Integer.toString(x) + ".htm"));
		
		CacheMetrix z = this.cache.cache().get(Paths.get(Integer.toString(x) + ".htm"));
		
		z.incrementCount(Paths.get(Integer.toString(x) + ".htm"));
		
		int w = z.getCount(Paths.get(Integer.toString(x) + ".htm"));
		// rwLock.writeLock().lock();
		// accessC.increment(files.get(x));
		// rwLock.writeLock().unlock();
		// rwLock.readLock().lock();
		//int y = accessC.getCount(files.get(x));
		// rwLock.readLock().unlock();
		System.out.println("...the count was " + Integer.toString(w) + ".");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (done) {
			System.out.println("Stopped accessing AccessCounter.");
			//this.primes.clear();
			//break;
		}
	}
}
