package ee.ut.math.tvt.salessystem.domain.model;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;

/**
 * Purchase history details model.
 */
public class PurchaseInfoTableModel extends SalesSystemTableModel<SoldItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger
			.getLogger(PurchaseInfoTableModel.class);

	public PurchaseInfoTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity", "Sum" });
	}

	@Override
	protected Object getColumnValue(SoldItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		case 4:
			return (double) Math.round(item.getSum() * 100) / 100;
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final SoldItem item : rows) {
			buffer.append(item.getId() + "\t");
			buffer.append(item.getName() + "\t");
			buffer.append(item.getPrice() + "\t");
			buffer.append(item.getQuantity() + "\t");
			buffer.append(item.getSum() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

	/**
	 * Add new StockItem to table.
	 */
	public void addItem(final SoldItem item) {
		try {
			SoldItem sold = getItemById(item.getId());
			sold.setQuantity(sold.getQuantity() + item.getQuantity());
			log.debug("Found existing item " + item.getName()
					+ " increased quantity by " + item.getQuantity());
		} catch (NoSuchElementException e) {
			rows.add(item);
			log.debug("Added " + item.getName() + " quantity of "
					+ item.getQuantity());
		}
		fireTableDataChanged();
	}

	public double getSum() {
		double sum = 0.0;
		for (SoldItem soldItem : this.rows) {
			sum += soldItem.getSum();
		}
		sum = Math.round(sum * 100);
		return sum / 100;
	}
}
