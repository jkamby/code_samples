package cs636.music.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import static javax.persistence.FetchType.EAGER;

import cs636.music.domain.User;
import cs636.music.domain.LineItem;

/**
 * Invoice, like Murach pg. 649 except:
 * --Murach calls the id "invoiceNumber" vs. "invoiceId" here
 * --We use Set instead of List
 * --We use BigDecimal for money instead of double to keep pennies exact.
 * However, it does not strictly qualify as a ordinary JavaBean (definition pg. 175) 
 * because of lack of some setters.
 * The more general JavaBean (see Java tutorial at http://docs.oracle.com/javase/tutorial/javabeans)
 * allows read-only properties like the four fields here.
 */
 
@Entity
@Table(name="INVOICE")

public class Invoice implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@TableGenerator(name="InvoiceIdGen",
			table = "MUSIC_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "InvoiceId_Gen")
				
	@GeneratedValue(generator="InvoiceIdGen")
	@Column(unique=true, nullable=false)
	private long invoiceId;
	
	@ManyToOne
	@JoinColumn(name="USER_ID", nullable=false)
	private User user;
	
	@Temporal(TemporalType.DATE) // @Column(name="INVOICE_DATE", nullable=false, length=30)
	private Date invoiceDate;
	
	@Column(name="TOTAL_AMOUNT", nullable=false, length=30)
	private BigDecimal totalAmount;
	
	@Column(name="IS_PROCESSED", nullable=false, length=30)
	private boolean isProcessed;
	
	@OneToMany(fetch=EAGER, cascade=CascadeType.PERSIST/*, mappedBy="invoice"*/)
	private Set<LineItem> lineItems;
	
	public Invoice() {}
	
	public Invoice(long id, User u, Date d, boolean isProcessed, Set<LineItem> items, BigDecimal totAmount) {
		invoiceId = id;
		user = u;
		invoiceDate = d;
		lineItems = items;
		totalAmount = totAmount;
	}
	
	public long getInvoiceId() {
		return invoiceId;
	}
	
	public void setInvoiceId(long invoice_id) {
		this.invoiceId = invoice_id;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setLineItems(Set<LineItem> items) {
		lineItems = items;
	}

	public Set<LineItem> getLineItems() {
		return lineItems;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totAmount) {
		this.totalAmount = totAmount;
	}
	
	// getter for boolean: isX, not getIsX
	public boolean isProcessed() {
		return isProcessed;
	}

	public void setProcessed(boolean isProcessed) {
		this.isProcessed = isProcessed;
	}
	
	// We could have a method to calculate the invoice total here

}
