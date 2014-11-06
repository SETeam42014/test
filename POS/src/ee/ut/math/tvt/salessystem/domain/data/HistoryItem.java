/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.data;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Johani
 * 
 */
@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem {

	/**
	 * Variables
	 */
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "date")
	private Date date;

	@Transient
	private double sum;

	/**
	 * Constructor for HistoryItem
	 * 
	 * @param Sum
	 *            of the transaction
	 * @param Sold
	 *            goods
	 */
	public HistoryItem(double sum, List<SoldItem> purchase) {
		// this.id = nextId++;
		this.date = new Date();
		this.sum = sum;
		this.items = purchase;
	}

	public HistoryItem() {
		// this.sum = this.calculateSum();
	}

	@OneToMany(mappedBy = "historyItem")
	private List<SoldItem> items;

	public void setItems(List<SoldItem> purchase) {
		this.items = purchase;
	}

	public List<SoldItem> getItems() {
		return this.items;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	// private double calculateSum() {
	// double sum = 0.0;
	// for (SoldItem item : this.items) {
	// sum += item.getPrice();
	// }
	// return sum;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see ee.ut.math.tvt.salessystem.domain.data.DisplayableItem#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ee.ut.math.tvt.salessystem.domain.data.DisplayableItem#getName()
	 */
	@Override
	public String getName() {
		return this.id + " " + this.date;
	}

}
