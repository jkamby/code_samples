package cs636.music.dao;

import java.util.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import static cs636.music.dao.DBConstants.*;

/**
 * Database connection and initialization.
 * Implemented singleton on this class.
 * 
 * @author Chung-Hsien (Jacky) Yu
 * 
 */
public class DbDAO {
	   
	private EntityManagerFactory emf;

	// Each request runs in one Java thread from start to finish,
	// and thus runs in one Java thread for a whole transaction.
	// Multiple transactions (in multiple threads) can be running
	// at once, each of which needs its own EM. We save the thread's 
	// EntityManager in a ThreadLocal, i.e., in thread-private memory,
	// a standard Java capability. Also see comment in commitTransaction.
	
	// Side Note: There are other ways to provide an EM for each
	// transaction. We could have startTransaction return an object to 
	// the service layer, where it could be kept in a local variable 
	// for the transaction lifetime, and add an argument to each DAO
	// method for returning the object to the DAO on each method call.
	// Or we could give up on DAO singletons, and create a DAO
	// object for each transaction. Or even give up on singletons
	// for both service objects and DAOs. This would be closest to
	// EJB architecture, but EJB provides object pools to make this
	// a higher performance option.
	private ThreadLocal<EntityManager> threadEM = new ThreadLocal<EntityManager>();

	public EntityManager getEM() {
		return threadEM.get(); // get this thread's EM
	}

	public DbDAO(EntityManagerFactory emf) {
		this.emf = emf;
	}
	
	/**
	*  bring DB back to original state
	*  @throws  SQLException
	**/
	public void initializeDb() throws SQLException {
		clearTable(DOWNLOAD_TABLE);
		clearTable(LINEITEM_TABLE);
		clearTable(INVOICE_TABLE);
		clearTable(USER_TABLE);
		clearTable(SYS_TABLE);
		initSysTable();
		// initIdGenTable();		
	}

	/**
	*  Delete all records from the given table
	*  @param tableName table name from which to delete records
	*  @throws  SQLException
	**/
	private void clearTable(String tableName) throws SQLException {
		Query q = getEM().createNativeQuery("delete from " + tableName);
		int n = q.executeUpdate(); // SQL of update shows in FINE logging
		System.out.println("deleted " + n + " rows from " + tableName);
	}
	
	/**
	*  Set all the index number used in other tables back to 1
	*  @throws  SQLException
	**/
	private void initSysTable() throws SQLException {
		System.out.println("inserting row (1,1,1,1) into " + SYS_TABLE);
		Query q = getEM().createNativeQuery(
				"insert into " + SYS_TABLE + " values (1,1,1,1)");
		int n = q.executeUpdate();
		System.out.println("inserted " + n + " rows into " + SYS_TABLE);
	}
	/**
	 * format a date type date into appropriate string base on database 
	 * that current connection connects to.  Using package protection
	 * to indicate this is for DAO use--it's specific to DB needs.
	 * @param date
	 * @return time stamp as string
	 */
    /*String formatTimestamp(Date date) {
    	String outstr="";
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	outstr = timestampstr + " '" + formatter.format(date)+"'";
    	
    	return outstr;
    }*/
	public void startTransaction() {
		EntityManager em = emf.createEntityManager();
		threadEM.set(em); // save in thread-local storage
		EntityTransaction tx = em.getTransaction();
		tx.begin();

	}

	public void commitTransaction() {
		// the commit call can throw, and then the caller needs to rollback
		getEM().getTransaction().commit();
		// We are using an application-managed entity manager, so we need
		// to explicitly close it to release its resources.
		// See Keith & Schincariol, pg. 138, first paragraph.
		// By closing the em at the end of the transaction, we are
		// following the pattern of transaction-scoped entity managers
		// used in EJBs by default.
		getEM().close(); // this causes the entities to become detached
		
		// Drop reference from thread to EM object, a possibly significant 
		// amount of memory even after it has been closed. In an application 
		// server, threads are pooled, so an individual thread actually lives 
		// longer than a request. Also, Tomcat 6.0.2x checks for this kind
		// of possible memory leak and outputs nasty messages "SEVERE: ..."
		// when it detects objects ref'd from ThreadLocals
		threadEM.set(null);
	}

	public void rollbackTransaction() {
		try {
			getEM().getTransaction().rollback();
		} finally {
			getEM().close();
			threadEM.set(null);  // see comment in commitTransaction()
		}
	}

	// Exceptions occurring in JPA code are almost always fatal to the
	// EntityManager context, meaning that we need to rollback the transaction
	// (and also close the EntityManager in our setup) and start over
	// or fail the action. An exception to this rule is the NoResultException
	// from the method singleResult()--it's OK to handle the exception and
	// continue the EntityManager/transaction after that particular exception.
	// If the caller has already seen an exception, it probably
	// doesn't want to handle a failing rollback, so it can use this.
	// Then the caller should issue its own exception based on the
	// original exception.
	public void rollbackAfterException() {
		try {
			rollbackTransaction();
		} catch (Exception e) {
			// discard secondary exception--probably server can't be reached
		}
	}

}
