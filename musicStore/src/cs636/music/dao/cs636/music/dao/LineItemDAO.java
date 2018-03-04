package cs636.music.dao;

import static cs636.music.dao.DBConstants.LINEITEM_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.sql.SQLException;
import cs636.music.domain.LineItem;

/**
 * 
 * Access line item table through this class. 
 * This code could be moved into InvoiceDAO.
 * @author Chung-Hsien (Jacky) Yu
 */
public class LineItemDAO {
	
	private DbDAO dbDAO;
	
	/**
	 * An Data Access Object for LineItem table
	 * @param db the database connection
	 * @throws SQLException
	 */
	public LineItemDAO(DbDAO db) {
		this.dbDAO = db;
	}
	
	/**
	 * Insert a line item into an given (by invoice id) invoice
	 * @param invoiceID invoice id
	 * @param item new line item
	 * @throws SQLException
	 */
	public void insertLineItem(long invoiceID, LineItem item) throws SQLException {
		EntityManager em = dbDAO.getEM();
		int lineitem_id = getNextLineItemID();
		item.setId(lineitem_id);
		Query q = em.createNativeQuery("insert into " + LINEITEM_TABLE + 
			" (lineitem_id, invoice_id, product_id, quantity) values ("
			+ item.getId() + ", " + invoiceID + ", "
			+ item.getProduct().getId() + ", " + item.getQuantity() + ") ");
		q.executeUpdate();
		
	}
	
	/**
	 * Increase lineitem_id by 1 in the system table
	 * @throws SQLException
	 */
	private void advanceLineItemID() throws SQLException
	{
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" update " + SYS_TABLE
					+ " set lineitem_id = lineitem_id + 1");
		q.executeUpdate();
	}
	
	/**
	 * Get the available line item id 
	 * @return the line item id available 
	 * @throws SQLException
	 */
	private int getNextLineItemID() throws SQLException
	{
		int nextLID;
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" select lineitem_id from " + SYS_TABLE);
		nextLID = (int) q.getSingleResult();
		advanceLineItemID(); // the id has been taken, so set +1 for next one
		return nextLID;
	}
}
