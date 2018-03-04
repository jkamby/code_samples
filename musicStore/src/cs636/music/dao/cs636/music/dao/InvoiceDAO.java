package cs636.music.dao;

import static cs636.music.dao.DBConstants.INVOICE_TABLE;
import static cs636.music.dao.DBConstants.LINEITEM_TABLE;
import static cs636.music.dao.DBConstants.SYS_TABLE;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.domain.Product;


/**
 * 
 * Access Invoice table through this class. 
 * @author Chung-Hsien (Jacky) Yu
 */
public class InvoiceDAO {
	private DbDAO dbDAO;
    
	public InvoiceDAO(DbDAO db) {
		this.dbDAO = db;
	}

	/**
	 * Increase invoice_id by 1 in the system table
	 * @throws SQLException
	 */
	private void advanceInvoiceID()
	{
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" update " + SYS_TABLE
					+ " set invoice_id = invoice_id + 1");
		q.executeUpdate();
	}
	
	/**
	 * Get the available invoice id 
	 * @return the invoice id available 
	 * @throws SQLException
	 */
	private int getNextInvoiceID()	{
		int nextIID = 0;
		EntityManager em = dbDAO.getEM();
		Query q = em.createNativeQuery(" select invoice_id from " + SYS_TABLE);
		List rList = q.getResultList();
		if(!rList.isEmpty())
		nextIID = (int) rList.get(0/*"invoice_id"*/);
		advanceInvoiceID(); // the id has been taken, so set +1 for next one
		return nextIID;
	}
	
	
	/**
	 * Insert invoice data into invoice and lineitme table
	 * uses current_time to set invoice_date timestamp
	 * @param invoice
	 * @throws SQLException
	 */
	public void insertInvoice(Invoice invoice) {
		EntityManager em = dbDAO.getEM();
   		em.persist(invoice);
		long invoiceID =  getNextInvoiceID();
		String YN = "";
		if (invoice.isProcessed()){
			YN = "y";
		}else{
			YN = "n";
		}
		/*try{
			String sqlString = "insert into "+ INVOICE_TABLE + " values (" +
			invoiceID + ", " + 
			invoice.getUser().getId()+ " , " + "current_timestamp, " +
			//dbdao.formatTimestamp(invoice.getInvoiceDate()) + ", " + 
			invoice.getTotalAmount().toPlainString() + ", " +
			"'" + YN +"')";
			stmt.execute(sqlString);
			for (LineItem item: invoice.getLineItems()){
			   lineitemdb.insertLineItem(invoiceID, item);
			}
		} finally {
			stmt.close();
		}*/
	}
	
	
	/**
	 * find one invoice and its items by given invoice id
	 * @param invoiceId
	 * @return an invoice data with its items, null if not found
	 * @throws SQLException
	 */
	public Invoice findInvoice(long invoiceId) {
		Invoice invoice = null;
		EntityManager em = dbDAO.getEM();
		System.out.println("in findInvoice");

		String sqlString =  " select * from " + 
			INVOICE_TABLE + " i, " +
			LINEITEM_TABLE + " l " +		
			" where i.invoice_id = " + invoiceId + 
			" and i.invoice_id = l.invoice_id ";
		
		TypedQuery<Invoice> query = (TypedQuery<Invoice>) em.createQuery(sqlString);
		List<Invoice> invoices = query.getResultList();
		TreeSet<Invoice> set = new TreeSet<Invoice>(invoices);
		if (!set.isEmpty()){ 
			invoice = set.first();
			// if the result is not empty
				// first row: set up Invoice from invoice columns	
	//TODO: implement UserDAO and ProductDAO
				
//				invoice= new Invoice(set.getInt("invoice_id"),
//						userdb.findUserByID(set.getInt("user_id")),
//						set.getTimestamp("invoice_date"),
//						set.getString("is_processed") == "y",
//						null,// items added below
//						set.getBigDecimal("total_amount"));
//				Set<LineItem> items = new HashSet<LineItem>();
//				Product product = proddb.findProductByPID(set.getInt("product_id"));
//				LineItem item = new LineItem(set.getInt("lineitem_id"), product, invoice, set.getInt("quantity"));
//				items.add(item);
//				while (set.next()){ 
//					product = proddb.findProductByPID(set.getInt("product_id"));
//					item = new LineItem(set.getInt("lineitem_id"), product, invoice, set.getInt("quantity"));
//					items.add(item);
//				}
//				invoice.setLineItems(items);
		}
		return invoice;
	}
	
	/**
	 * find all unprocessed invoice
	 * @return all unprocessed invoice in db
	 * @throws SQLException
	 */
	public Set<Invoice> findAllUnprocessedInvoice() {
		// Set<Invoice> invoices = new HashSet<Invoice>();
		EntityManager em = dbDAO.getEM();
		//Invoice invoice;
		
		String sqlString =  " select invoice_id from " + INVOICE_TABLE + " where is_processed = 'n'";
		
		TypedQuery<Invoice> query = (TypedQuery<Invoice>) em.createQuery(sqlString);
		List<Invoice> invoices = query.getResultList();

		return new TreeSet<Invoice>(invoices);
	}
	
	/**
	 * find all invoices
	 * @return all invoices in db
	 * @throws SQLException
	 */
	public Set<Invoice> findAllInvoices() {
		// Set<Invoice> invoices = new HashSet<Invoice>();
		EntityManager em = dbDAO.getEM();
		// Invoice invoice;
		
		String sqlString =  " select invoice_id from " + INVOICE_TABLE ;
		TypedQuery<Invoice> query = (TypedQuery<Invoice>) em.createQuery(sqlString);
		List<Invoice> invoices = query.getResultList();

		return new TreeSet<Invoice>(invoices);
	}
	
	/**
	 * update the is_processed attribute of the invoice
	 * @param i  Invoice to update
	 * @throws SQLException
	 */
	public void updateInvoice(Invoice i) {
		EntityManager em = dbDAO.getEM();
		String sqlString = "update "+ INVOICE_TABLE + " set is_processed = 'y'" +
			" where invoice_id = " + i.getInvoiceId();
		System.out.println(sqlString);
		Query q = em.createNativeQuery(sqlString);
		q.executeUpdate();
	}
}
