package cs636.music.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import cs636.music.dao.DbDAO;
import cs636.music.dao.UserDAO;
import cs636.music.dao.ProductDAO;
import cs636.music.dao.DownloadDAO;
import cs636.music.dao.LineItemDAO;
import cs636.music.dao.InvoiceDAO;
import cs636.music.domain.User;
import cs636.music.domain.Cart;
import cs636.music.domain.Product;
import cs636.music.domain.Track;
import cs636.music.domain.Download;
import cs636.music.domain.Invoice;
import cs636.music.domain.LineItem;
import cs636.music.presentation.clientserver.UserApp;
import cs636.music.service.data.UserData;
import cs636.music.service.data.InvoiceData;
import cs636.music.presentation.PresentationUtils;

/**
 * 
 * Provide user level services to user app through accessing DAOs 
 * @author Chung-Hsien (Jacky) Yu
 */
public class UserService {
	
	private DbDAO dbDao;
	private ProductDAO productDao;
	private UserDAO userDao;
	private DownloadDAO downloadDao;
	private LineItemDAO lineItemDao;
	private InvoiceDAO invoiceDao;
	
	/**
	 * construct a user service provider 
	 * @param dbDao
	 */
	public UserService(ProductDAO productDao, UserDAO userDao, DownloadDAO downloadDao, LineItemDAO lineItemDao, InvoiceDAO invoiceDao) {
		this.productDao = productDao;	
		this.userDao = userDao;
		this.downloadDao = downloadDao;
		this.lineItemDao = lineItemDao;
		this.invoiceDao = invoiceDao;
	}

	//Catalog browsing
	public void browseCatalog() {
		if (productDao == null)
				System.out.println("The productDao object is null!");
		else
			PresentationUtils.displayCDCatlog(productDao.findAllProducts(), System.out);
	}
	
	//Cart matters
	public Cart createCart() {
		Cart cart = new Cart();
		return cart;
	}
	
	public Set<Product> getProductList() throws ServiceException {
		HashSet<Product> productList = new HashSet<Product> ();
		try {
			dbDao.startTransaction();
			productList = (HashSet<Product>) productDao.findAllProducts();
			dbDao.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			dbDao.rollbackAfterException();
			throw new ServiceException(
					"Failed to access the product list.", e);
		}
		return productList;
	}
	
	/**
	 * Add a product to the cart. If the product is already in the cart, add
	 * quantity. Otherwise, insert a new line item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void addItemtoCart(Product prod, Cart cart, int quantity) {
		LineItem item = cart.findItem(prod);
		if (item != null) { // product is already in the cart, add quantity
			int qty = item.getQuantity();
			item.setQuantity(qty + quantity);
		} else { // not in the cart, add new item with quantity
			item = new LineItem(0, prod, quantity);
			// cart.addItem(item);
			cart.getItems().add(item);
		}
	}
	
	/**
	 * Change the quantity of one item. If quantity <= 0 then delete this item.
	 * 
	 * @param prod
	 * @param cart
	 * @param quantity
	 */
	public void changeCart(Product prod, Cart cart, int quantity) {
		LineItem item = cart.findItem(prod);
		if (item != null) {
			if (quantity > 0) {
				item.setQuantity(quantity);
			} else { // if the quantity was changed to 0 or less, remove the
						// product from cart
				cart.removeItem(prod);
			}
		}
	}

	/**
	 * Remove a product item from the cart
	 * 
	 * @param prod
	 * @param cart
	 */
	public void removeCartItem(Product prod, Cart cart) {
		LineItem item = cart.findItem(prod);
		if (item != null) {
			cart.removeItem(prod);
		}
	}
	
	public Product browseProductByCode (String code) throws ServiceException {
		Product prdct = null;
		try {
			dbDao.startTransaction();
			prdct = productDao.findProductByPCode(code);
			dbDao.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			dbDao.rollbackAfterException();
			throw new ServiceException(
					"Failed to access the product list.", e);
		}
		return prdct;
	}
	
	//not used anywhere!?!
	/*public void recordDownload(int trackNumber) throws ServiceException
	{
		Download newDL = new Download();
		newDL.setDownloadId(0);
		newDL.setUser(user);
		newDL.setTrack(trackNumber);
		newDL.setDownloadDate(new Date());
		downloadDao.insertDownload(newDL);
		//download.setDownloadId(downloadDao.getNextDownloadID());
		download.setTrack(track);
		downloadDao.insertDownload(download);
	     //System.out.println("registerUser called, email = " +email);
	     //userDao.insertUser(email);
	}*/
	
	public User findUserByEmail(String email) throws ServiceException
	{
	    // System.out.println("registerUser called, email = " +email);
		User user = null;
		try {
			dbDao.startTransaction();
			user = userDao.findUserByEmail(email);
			dbDao.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			dbDao.rollbackAfterException();
			throw new ServiceException(
					"Failed to search for the user.", e);
		}
	     return user;
	}


	// User Registration
	public User registerUser(String fName, String lName, String eMail) throws ServiceException {
		User user = null;
		try {
			dbDao.startTransaction();
			user = userDao.findUserByEmail(eMail);
			if (user == null)
				System.out.println("User already registered!");
			else {
				user = userDao.createUser(fName, lName, eMail);
				int newId = userDao.insertUser(user);
				user = userDao.findUserByID(newId);
			}
			dbDao.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			dbDao.rollbackAfterException();
			throw new ServiceException(
					"Something went wrong with the registration of the new user.", e);
		}
	     return user;
	}
	
	public void cartCheckOut(Invoice invoice) throws ServiceException {
		try {
			dbDao.startTransaction();
			invoiceDao.insertInvoice(invoice);
			dbDao.commitTransaction();
		} catch (Exception e) { // any exception
			// the following doesn't itself throw, but it handles the case that
			// rollback throws, discarding that exception object
			dbDao.rollbackAfterException();
			throw new ServiceException("Failed to insert the invoice.", e);
		}
	}

	public UserData getUserInfo(String usrEmail) {
		return new UserData(userDao.findUserByEmail(usrEmail));
	}

	public Product getProduct(String productCode) {
		return productDao.findProductByPCode(productCode);
	}

	public InvoiceData checkout(Cart cart, long userId) {
		Invoice cartInvoice = new Invoice();
		cartInvoice.setUser(userDao.findUserByID((int) userId));
		cartInvoice.setInvoiceDate(new Date());
		cartInvoice.setLineItems(cart.getItems());
		cartInvoice.setTotalAmount(cart.cartTotal());
		invoiceDao.insertInvoice(cartInvoice);
		return new InvoiceData(cartInvoice);
	}

	public void addDownload(long user_id, Track track0) {
		Download newDL = new Download();
		newDL.setUser(userDao.findUserByID((int)user_id));
		newDL.setTrack(track0);
		downloadDao.insertDownload(newDL);
		
	}
}
