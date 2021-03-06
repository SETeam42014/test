/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * @author Johani
 * 
 */
public class HistoryTableModel extends SalesSystemTableModel<HistoryItem> {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(HistoryTableModel.class);

	public HistoryTableModel() {
		super(new String[] { "Date", "Time", "Sum" });
	}

	@Override
	protected Object getColumnValue(HistoryItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getDate();
		case 1:
			return item.getDate();
		case 2:
			return item.getSum();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with same
	 * id, then existing item's quantity will be increased.
	 * 
	 * @param stockItem
	 */
	public void addItem(final HistoryItem historyItem) {
		rows.add(historyItem);
		log.debug("Added new item: " + historyItem.getDate());
		fireTableDataChanged();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final HistoryItem historyItem : rows) {
			buffer.append(historyItem.getDate() + "\t");
			buffer.append(historyItem.getDate() + "\t");
			buffer.append(historyItem.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}
}
