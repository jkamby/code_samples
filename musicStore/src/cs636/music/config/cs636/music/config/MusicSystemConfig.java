package cs636.music.config;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import cs636.music.dao.AdminDAO;
import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.dao.LineItemDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.dao.UserDAO;
import cs636.music.service.AdminService;
import cs636.music.service.UserService;

/**
 * @author Betty O'Neil
 *
 * Configure the service objects, shut them down
 * 
 */

public class MusicSystemConfig {

	public static final String SOUND_BASE_URL = "http://www.cs.umb.edu/cs636/music1-setup/sound/";
	// the service objects in use, representing all lower layers to the app
	private static AdminService adminService;
	private static AdminDAO adminDao;	
	private static DbDAO dbDAO;  // contains Connection
	private static EntityManagerFactory emf;

	private static UserService userService;
//	// the lower-level service objects-- you can vary this as desired
	private static DownloadDAO downloadDao;
	private static InvoiceDAO invoiceDao;
	private static LineItemDAO lineItemDao;
	private static ProductDAO productDao;
	private static UserDAO userDao;

	// set up service API, data access objects
	public static void configureServices() throws Exception {	
		// configure service layer and DAO objects--
		// The service objects get what they need at creation-time
		// This is known as "constructor injection" 

		try {
			// read persistence.xml, etc.
			emf = configureJPA(); 
			testEMF(emf);  // check we can connect, and try to report JDBC isolation level
			System.out.println("calling dbDAO constructor");
			dbDAO = new DbDAO(emf);
			
			adminDao = new AdminDAO(dbDAO);

			// one way: you can change this--
			productDao = new ProductDAO(dbDAO);
			userDao = new UserDAO(dbDAO);
			downloadDao = new DownloadDAO(dbDAO);		
			lineItemDao = new LineItemDAO(dbDAO);	
			invoiceDao = new InvoiceDAO(dbDAO);
			adminService = new AdminService(dbDAO, adminDao, invoiceDao, downloadDao);
			userService = new UserService(productDao,userDao,downloadDao,lineItemDao,invoiceDao);
		} catch (Exception e) {
			System.out.println("Problem with contacting DB");
		    // rethrow to notify caller (caller should print exception details)
			throw new Exception("Exception in configureServices",e);
		}
	}
	
	// The configuration information is read from the persistence.xml file
	// on the classpath.  This may throw a RuntimeException.
	public static EntityManagerFactory configureJPA() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("music3el");
		return emf;
	}
	
	// This method is not needed for setup: just testing early for ability to get an EM
	// Try a test EM session and get the isolation level, if such access is supported by this driver
	private static void testEMF(EntityManagerFactory emf) throws Exception
	{
		EntityManager em = null;
		System.out.println("starting testEMF");
		try {
			em = emf.createEntityManager();
		} catch (Throwable e) {
			System.out.println("Running outside of web container, i.e, in client-server context, so JNDI is unavailable");
			System.out.println("To run a client-server app, use ant config-clientserver-hsqldb or similar");
			throw new Exception("can't get EM, probably need ant config-clientserver-xxxx", e);  // bail out
		}
		Map<String, Object> props = emf.getProperties();
		System.out.println(props);
		System.out.println("testEMF: Got an EM");
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		// dig down in software to get the actual JDBC Connection, if this driver will allow us...
		try {
			System.out.println("testEM: Trying to get JDBC Connection from EM (not always supported) ...");
			// This trick doesn't work for mysql, or some versions of it anyway
			Connection conn = em.unwrap(Connection.class);
			if (conn == null)
				System.out.println("failed to get underlying JDBC Connection from EM, so won't be able to get isolation level this way");
			else {
				System.out.println("Got Connection: JDBC isolation level (0=none,1=RU,2=RC,4=RR,8=SR) is " 
					+ conn.getTransactionIsolation());
			}
			tx.commit();
			em.close();
		} catch (Exception e) {
			System.out.println(" Exception trying to get isolation level: " + e + "\n Continuing...");
		}
	}
	
	// Compose an exception report
	// and return the string for callers to use
	public static String exceptionReport(Exception e) {
		String message = e.toString(); // exception name + message
		if (e.getCause() != null) {
			message += "\n  cause: " + e.getCause().toString();
			if (e.getCause().getCause() != null)
				message += "\n    cause's cause: "
						+ e.getCause().getCause().toString();
		}
		return message;
	}
	
	private static String exceptionStackTraceString(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
	
	// When the app exits, the shutdown happens automatically
	// For other cases, call this to free up the JDBC Connection
	public static void shutdownServices() throws Exception {
		if (emf != null && emf.isOpen())
			emf.close();
	}
	// Let the apps get the business logic layer services
	public static AdminService getAdminService() {
		return adminService;
	}

	public static UserService getUserService() {
		return userService;
	}
}
