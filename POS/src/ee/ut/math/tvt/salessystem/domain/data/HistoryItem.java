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

	// @OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL,
	// CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "historyItem")
	@OneToMany(mappedBy = "historyItem")
	private List<SoldItem> items;

	public HistoryItem(double sum, List<SoldItem> purchase) {
		this.date = new Date();
		this.items = purchase;
		for (SoldItem soldItem : purchase) {
			soldItem.setHistoryItem(this);
		}
	}

	public HistoryItem() {
	}

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
		double sum = 0.0;
		for (SoldItem item : items) {
			sum += item.getSum();
		}
		return (double) Math.round(sum * 100) / 100;
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public String getName() {
		return this.id + " " + this.date;
	}

}
