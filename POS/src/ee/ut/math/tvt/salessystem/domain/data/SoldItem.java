package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//import javax.persistence.Transient;

/**
 * Already bought StockItem. SoldItem duplicates name and price for preserving
 * history.
 */
@Entity
@Table(name = "SOLDITEM")
public class SoldItem implements Cloneable, DisplayableItem {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price")
	private double price;

	@Column(name = "quantity")
	private Integer quantity;

	// @ManyToOne
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "historyitem_id", nullable = false)
	private HistoryItem historyItem;

	public SoldItem(StockItem stockItem, int quantity) {
		this.id = stockItem.getId();
		this.name = stockItem.getName();
		this.description = stockItem.getDescription();
		this.price = stockItem.getPrice();
		this.quantity = quantity;
	}

	public SoldItem() {

	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		if (price < 0.0) {
			throw new IllegalArgumentException();
		}
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		this.quantity = quantity;
	}

	public HistoryItem getHistoryItem() {
		return historyItem;
	}

	public void setHistoryItem(HistoryItem historyItem) {
		if (historyItem == null) {
			throw new NullPointerException();
		}
		this.historyItem = historyItem;
	}

	public double getSum() {
		return this.price * ((double) this.quantity);
	}

}
