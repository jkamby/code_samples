package cs636.music.dao;

import static cs636.music.dao.DBConstants.LINEITEM_TABLE;
import static cs636.music.dao.DBConstants.PRODUCT_TABLE;
import static cs636.music.dao.DBConstants.TRACK_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.math.BigDecimal;

import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.Download;
import cs636.music.domain.User;
import cs636.music.domain.Invoice;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 * 
 * Access line item table through this class. 
 * This code could be moved into InvoiceDAO.
 * @author Chung-Hsien (Jacky) Yu
 */
public class ProductDAO {
	
	private DbDAO dbDAO;
	
	/**
	 * An Data Access Object for LineItem table
	 * @param db the database connection
	 * @throws SQLException
	 */
	public ProductDAO(DbDAO db) {
		this.dbDAO = db;
	}
	
	/*public Track findTrackbyNumber(int trackNumber){
		for (Track track: tracks){
			if (trackNumber == track.getTrackNumber()) {
				return track;
			}
		}
		return null;
	}
	
	public Track findTrackbyID(int trackID){
		for (Track track: tracks){
			if (trackID == track.getId()) {
				return track;
			}
		}
		return null;
	}*/
	
	public Track findTrackByTID(int track_id) {
		Track track=null;
		EntityManager em = dbDAO.getEM();
		String sqlString = "select * from "+ PRODUCT_TABLE + " P, " + TRACK_TABLE +
		     " T where P.product_id = T.product_id and track_id = " + track_id;
		Query q = em.createNativeQuery(sqlString);
		track = (Track) q.getSingleResult();
		return track;
	}
	
	public Product findProductByPID(int product_id) {
		//work in progress
		Product product=null;
		EntityManager em = dbDAO.getEM();
		String sqlString =  " select * from " + 
				PRODUCT_TABLE + " p, " +
				TRACK_TABLE + " t " +		
				" where p.product_id = " + product_id + 
				" and p.product_id = t.product_id order by t.track_number";
		Query q = em.createNativeQuery(sqlString);
		product = (Product) q.getSingleResult();
		return product;
	}
	
	public Product findProductByPCode(String product_code) {
		//work in progress
		Product product=null;
		EntityManager em = dbDAO.getEM();
		String sqlString = " select * from " + 
			PRODUCT_TABLE + " p, " +
			TRACK_TABLE + " t " +		
			" where p.product_code = '" + product_code + "'" + 
			" and p.product_id = t.product_id  order by t.track_number";
		Query q = em.createNativeQuery(sqlString);
		product = (Product) q.getSingleResult();
		return product;
	}
	
	/**
	 * find all products 
	 * @return all products in a Set
	 * @throws SQLException
	 */
	public Set<Product> findAllProducts() {
		//Product product=null;
		EntityManager em = dbDAO.getEM();
		TypedQuery<Product> query = (TypedQuery<Product>) em.createQuery("select * from " + PRODUCT_TABLE);
		List<Product> products = query.getResultList();
		return new HashSet<Product> (products);
	}
}
