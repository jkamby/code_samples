package cs636.music.dao;

/**
 *
 * Data access class for user objects.
 */

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.Download;
import cs636.music.domain.User;
//use static import to simplify use of constants--
import static cs636.music.dao.DBConstants.*;

public class UserDAO {
	
    private DbDAO dbDAO;  // for common DB methods
    
	public UserDAO(DbDAO db) {
		this.dbDAO = db;
	}
	
	
	// Creating a new User object: note that it doesn't yet have an ID.
	public User createUser(String fName, String lName, String eMail) {
		User newUser = new User();
		newUser.setId(0);
		newUser.setFirstname(fName);
		newUser.setLastname(lName);
		newUser.setEmailAddress(eMail);
		return newUser;
	}
	
	/**
	 * check email address
	 * @param email
	 * @return
	 * @throws SQLException
	 */
	public Boolean findUser(String email) {
		EntityManager em = dbDAO.getEM();
		Query q = em.createQuery(" select * from " + USER_TABLE +
					" where email_address = '" + email + "'");

		List<User> rList = q.getResultList();
		
		if (!rList.isEmpty()) // if the result is not empty
			return true;
		else
			return false;
	}	
	
	public User findUserByEmail(String email) {
		EntityManager em = dbDAO.getEM();
		User user = null;
		Query q = em.createQuery(" select * from " + USER_TABLE +
					" where email_address = '" + email + "'");
		List<User> rList = q.getResultList();
		if (!rList.isEmpty())
			user = rList.get(0);
		
		return user;
	}	

	public int insertUser(User user) {
		EntityManager em = dbDAO.getEM();
		em.persist(user);
		//Incomplete...
		int userId = getNextUserID();
		user.setId(userId);
		Query q = em.createNativeQuery("insert into " + USER_TABLE + 
			" (user_id, firstname, lastname, email_address) values ("
			+ user.getId() + ", '" + user.getFirstname() + "', '"
			+ user.getLastname() + "', '" + user.getEmailAddress() + "') ");
		return (int) q.executeUpdate();
	}

	public User findUserByID(int user_id) {
		EntityManager em = dbDAO.getEM();
		User user = null;
		Query q = em.createQuery(" select * from " + USER_TABLE +
					" where user_id = '" + user_id + "'");
		List<User> rList = q.getResultList();
		if (!rList.isEmpty())
			user = rList.get(0);
		
		return user;
	}
	
	public DbDAO whichDbDao() {
		return this.dbDAO;
	}
	
	/**
	 * Get the available user id 
	 * @return the user id available 
	 * @throws SQLException
	 */
	public int getNextUserID()
	{
		int nextUID;
		EntityManager em = dbDAO.getEM();
		Query q = em.createQuery(" select user_id from " + SYS_TABLE);
		nextUID = (int) q.getSingleResult();
		advanceUserID(); // the id has been taken, so set +1 for next one
		return nextUID;
	}
	
	private void advanceUserID()
	{
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" update " + SYS_TABLE
					+ " set user_id = user_id + 1");
		q.executeUpdate();
	}

}
