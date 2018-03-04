package cs636.music.dao;

import static cs636.music.dao.DBConstants.ADMIN_TABLE;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 * 
 * Access admin related tables through this class.
 * @author Chung-Hsien (Jacky) Yu
 */
public class AdminDAO {

	private DbDAO dbDAO;

	public AdminDAO(DbDAO db) {
		dbDAO = db;
	}

	// We are using plain SQL for this DAO since the SYS_TABLE, having
	// only one row, is not a classic entity table. We could still use
	// set up a domain class for it, but it would clutter up our domain
	// package with an unneeded class. It is normal practice to provide
	// entities only for the database rows actively used by the service
	// layer of the app. (as objects)

	// Alternative way: set up an entity class SysTime...
	// public int findLastReportDayUsingEntity() {
	// EntityManager em = dbDAO.getEM();
	// // access the one row--
	// SysTime sysTab = (SysTime) em.find(SysTime.class, 1);
	// return sysTab.getLastReport();
	// }
	
	/**
	 * check login user name and password
	 * @param uid
	 * @param pwd
	 * @return
	 * @throws SQLException
	 */
	public Boolean findAdminUser(String uid, String pwd) throws SQLException {
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" select * from " + ADMIN_TABLE +
					" where username = '" + uid + "'" +
					" and password = '" + pwd + "'");
		List rList = q.getResultList();
		if(!rList.isEmpty())
			return true;
		else
			return false;
	}
}
