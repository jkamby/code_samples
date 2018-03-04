package cs636.music.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 * LineItem: like Murach, pg. 649, except:
 * --The Product field is called "product", not "item"
 * --The database id is exposed with getter/setter
 * --getTotal in Murach is called calculateItemTotal here
 * to signify it is not a table attribute.
 *
 */
 
@Entity
@Table(name="LINEITEM")
 
public class LineItem implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@TableGenerator(name="LineItemIdGen",
			table = "MUSIC_ID_GEN",
			pkColumnName = "GEN_NAME",
			valueColumnName = "GEN_VAL",
			pkColumnValue = "LineItemId_Gen")
				
	@GeneratedValue(generator="LineItemIdGen")
	@Column(unique=true, nullable=false)
	private long id;
	
	@ManyToOne // @Column(name="PRODUCT_ID", nullable=false, length=30)
	private Product product;//need to get the product from the product_id
	
	@Column(name="QUANTITY", nullable=false)
	private int quantity;
	
	// no-args constructor, to be proper JavaBean
	public LineItem() {}
	
	// for DAO use: LineItem from DB
	public LineItem(long id, Product product, int quantity) {
		this.id = id;
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getId() {
		return id;
	}

	public void setId(long lineitem_id) {
		this.id = lineitem_id;
	}

	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	// Note this is method returning a quantity that is not a database column
	// value from the lineitem table. To avoid problems later, we avoid "getXXX" 
	// naming for such methods.
	// Also, the name points out this is not just a value from the table.

	public BigDecimal calculateItemTotal() {
		// We can't use * to multiply with BigDecimal, but it knows how--
		BigDecimal total = product.getPrice().multiply(new BigDecimal(quantity));
		return total;
	}

}
