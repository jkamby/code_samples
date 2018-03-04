package edu.umb.cs.cs681.threads.safe.futures;
 
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Future implements Pizza{
	private RealPizza realPizza = null;
	private ReentrantLock lock;
	private Condition ready;
	
	public Future(){
		lock = new ReentrantLock();
		ready = lock.newCondition();
		
	}
	
	public void setRealPizza( RealPizza real ){
		lock.lock();
		try{
			if( realPizza != null ){ return; }
			realPizza = real;
			ready.signalAll();
		}
		finally{
			lock.unlock();
		}
	}
	
	public String getPizza(){
		String pizzaData = null;
		lock.lock();
		try{
			if( realPizza == null ){
				ready.await();
			}
			pizzaData = realPizza.getPizza();
		}
		catch(InterruptedException e){}
		finally{
			lock.unlock();
		}
		return pizzaData;
	}

	public String getPizza(long timeOut) throws CashierTimeOutException{
		String pizzaData = null;
		lock.lock();
		try{
			if( realPizza == null ){
				ready.await(timeOut, TimeUnit.MILLISECONDS);
			}
			pizzaData = realPizza.getPizza();
		}
		catch(InterruptedException e){
			throw new CashierTimeOutException();
		}
		finally{
			lock.unlock();
		}
		return pizzaData;
	}
	
	public boolean isReady() {
		return realPizza != null ? true: false;
	}
}
