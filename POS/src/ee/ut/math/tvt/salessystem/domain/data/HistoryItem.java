/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Johani
 * 
 */
@Entity
@Table(name = "HISTORYITEM")
public class HistoryItem implements Cloneable, DisplayableItem, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Variables
	 */
	private static long nextId;
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(name = "date")
	private Date date;
	@Column(name = "sum")
	private double sum;
	@ManyToMany
	@JoinTable(name = "HISTORYITEM_TO_SOLDITEM", joinColumns = @JoinColumn(name = "HISTORYITEM_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "STOCKITEM_ID", referencedColumnName = "ID"))
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

	public void setEndDate(Date date) {
		this.date = date;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	/**
	 * Constructor for HistoryItem
	 * 
	 * @param Sum
	 *            of the transaction
	 * @param Sold
	 *            goods
	 */
	public HistoryItem(double sum, List<SoldItem> purchase) {
		this.id = nextId++;
		this.date = new Date();
		this.sum = sum;
		this.items = purchase;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ee.ut.math.tvt.salessystem.domain.data.DisplayableItem#getId()
	 */
	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ee.ut.math.tvt.salessystem.domain.data.DisplayableItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

}
