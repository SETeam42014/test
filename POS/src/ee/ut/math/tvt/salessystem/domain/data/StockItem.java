package ee.ut.math.tvt.salessystem.domain.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;

/**
 * Stock item. Corresponds to the Data Transfer Object design pattern.
 */
@Entity
@Table(name = "STOCKITEM")
public class StockItem implements Cloneable, DisplayableItem {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "description")
	private String description;

	@Column(name = "quantity")
	private int quantity;

	public StockItem(Long id, String name, String desc, double price,
			int quantity) {
		this.id = id;
		this.name = name;
		this.description = desc;
		this.price = price;
		this.quantity = quantity;
	}

	public StockItem() {

	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == null) {
			throw new NullPointerException();
		}
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		if (price < 0.0) {
			throw new IllegalArgumentException();
		}
		this.price = price;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		if (id < 0) {
			throw new IllegalArgumentException();
		}
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		if (quantity < 0) {
			throw new IllegalArgumentException();
		}
		this.quantity = quantity;
	}

	public void reduceQuantity(int quantity) throws OutOfStockException {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		if (this.quantity < quantity) {
			throw new OutOfStockException();
		}
		this.quantity -= quantity;
	}

	public String toString() {
		return id + " " + name + " " + description + " " + price;
	}

	public Object clone() {
		StockItem item = new StockItem(getId(), getName(), getDescription(),
				getPrice(), getQuantity());
		return item;
	}

}
