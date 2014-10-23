/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Johani
 *
 */
public class HistoryItem implements DisplayableItem, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static long nextId;
	private long id;
	private Date endDate;
	private Date startDate;
	private double sum;
	private List<StockItem> items;
	private int state;

	public void setItems(List<StockItem> purchase) {
		this.items = purchase;
	}

	public List<StockItem> getItems() {
		return this.items;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public HistoryItem(double sum) {
		this.id = nextId++;
		this.endDate = new Date();
		this.sum = sum;
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
