package cs636.music.service;


import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.AdminDAO;
import cs636.music.dao.DbDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.domain.Invoice;
import cs636.music.domain.Download;
import cs636.music.service.data.DownloadData;
import cs636.music.service.data.InvoiceData;

/**
 * 
 * Provide admin level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
public class AdminService {
	
	private DbDAO db;
	private AdminDAO adminDb;
	private InvoiceDAO invoiceDb;
	private DownloadDAO downloadDb;
	
	/**
	 * construct a admin service provider 
	 * @param dbDao
	 */
	public AdminService(DbDAO dbDao, AdminDAO adminDao, InvoiceDAO invoiceDao, DownloadDAO downloadDao) {
		this.db = dbDao;	
		this.adminDb = adminDao;
		this.invoiceDb = invoiceDao;
		this.downloadDb = downloadDao;
	}
	
	/**
	 * Clean all user table, not product and system table to empty
	 * and then set the index numbers back to 1
	 * @throws ServiceException
	 */
	public void initializeDB()throws ServiceException {
		try {
			db.startTransaction();
			db.initializeDb();
			db.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			db.rollbackAfterException();
			throw new ServiceException(
					"Can't initialize DB: (probably need to load DB)", e);
		}
	}
	
	/**
	 * process the invoice
	 * @param invoiceId
	 * @throws ServiceException
	 */
	public void processInvoice(long invoiceId) throws ServiceException {
		//System.out.println("TEMP: processing invoice");
		try {
			db.startTransaction();
			invoiceDb.updateInvoice(invoiceDb.findInvoice(invoiceId));
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("The invoice was not processed successfully: ",
					e);
		}
	}

	/**
	 * Get a list of all invoices
	 * @return list of all invoices
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofInvoices() throws ServiceException {
		//System.out.println("TEMP: getting invoices");
		HashSet<Invoice> invoices;
		try {
			db.startTransaction();
			invoices = (HashSet<Invoice>) invoiceDb.findAllInvoices();
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("The invoices were not retrieved successfully: ",
					e);
		}
		HashSet<InvoiceData> invoiceData = new HashSet<InvoiceData>(); 
		for (Invoice inv : invoices) {
			InvoiceData invoiceDatum = new InvoiceData(inv);
			invoiceData.add(invoiceDatum);
		}
		return invoiceData;
	}
	
	/**
	 * Get a list of all unprocessed invoices
	 * @return list of all unprocessed invoices
	 * @throws ServiceException
	 */
	public Set<InvoiceData> getListofUnprocessedInvoices() throws ServiceException {
		//System.out.println("TEMP: getting unprocessed invoices");
		HashSet<Invoice> invoices;
		try {
			db.startTransaction();
			invoices = (HashSet<Invoice>) invoiceDb.findAllUnprocessedInvoice();
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("The invoices were not retrieved successfully: ",
					e);
		}
		HashSet<InvoiceData> invoiceData = new HashSet<InvoiceData>(); 
		for (Invoice inv : invoices) {
			InvoiceData invoiceDatum = new InvoiceData(inv);
			invoiceData.add(invoiceDatum);
		}
		return invoiceData;
	}
	
	/**
	 * Get a list of all downloads
	 * @return list of all downloads
	 * @throws ServiceException
	 */
	public Set<DownloadData> getListofDownloads() throws ServiceException {
		//System.out.println("TEMP: getting downloads");
		HashSet<Download> downloads;
		try {
			db.startTransaction();
			downloads = (HashSet<Download>) downloadDb.findAllDownloads();
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("The downloads were not retrieved successfully: ",
					e);
		}
		HashSet<DownloadData> downloadData = new HashSet<DownloadData>(); 
		for (Download dld : downloads) {
			DownloadData downloadDatum = new DownloadData(dld);
			downloadData.add(downloadDatum);
		}
		return downloadData;
	}
	
	
	/**
	 * Check login user
	 * @param username
	 * @param password
	 * @return true if useranme and password exist, otherwise return false
	 * @throws ServiceException
	 */
	public Boolean checkLogin(String username,String password) throws ServiceException {
		Boolean loggedIn = false;
		try {
			db.startTransaction();
			loggedIn = adminDb.findAdminUser(username, password);
			db.commitTransaction();
		} catch (Exception e) {
			db.rollbackAfterException();
			throw new ServiceException("There was a problem trying to locate this user: ",
					e);
		}
		return loggedIn;
	}
	
}
