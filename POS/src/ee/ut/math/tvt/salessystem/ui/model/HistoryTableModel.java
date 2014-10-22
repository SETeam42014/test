/**
 * 
 */
package ee.ut.math.tvt.salessystem.ui.model;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * @author Johani
 *
 */
public class HistoryTableModel extends SalesSystemTableModel<SoldItem> {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(HistoryTableModel.class);

	public HistoryTableModel() {
		super(new String[] { "Id", "Name", "Quantity", "Sum" });
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getQuantity();
		case 3:
			return item.getPrice();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with same
	 * id, then existing item's quantity will be increased.
	 * 
	 * @param stockItem
	 */
	public void addItem(final SoldItem soldItem) {
		try {
			SoldItem item = getItemById(soldItem.getId());
			item.setQuantity(item.getQuantity() + soldItem.getQuantity());
			log.debug("Found existing item " + soldItem.getName()
					+ " increased quantity by " + soldItem.getQuantity());
		} catch (NoSuchElementException e) {
			rows.add(soldItem);
			log.debug("Added " + soldItem.getName() + " quantity of "
					+ soldItem.getQuantity());
		}
		fireTableDataChanged();
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem soldItem : rows) {
			buffer.append(soldItem.getId() + "\t");
			buffer.append(soldItem.getName() + "\t");
			buffer.append(soldItem.getQuantity() + "\t");
			buffer.append(soldItem.getPrice() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

}
