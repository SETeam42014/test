package ee.ut.math.tvt.salessystem.ui.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;

/**
 * Stock item table model.
 */
public class StockTableModel extends SalesSystemTableModel<StockItem> {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(StockTableModel.class);

	/**
	 * Default constructor. Creates headers.
	 */
	public StockTableModel() {
		super(new String[] { "Id", "Name", "Price", "Quantity" });
	}

	@Override
	protected Object getColumnValue(StockItem item, int columnIndex) {
		switch (columnIndex) {
		case 0:
			return item.getId();
		case 1:
			return item.getName();
		case 2:
			return item.getPrice();
		case 3:
			return item.getQuantity();
		}
		throw new IllegalArgumentException("Column index out of range");
	}

	/**
	 * Add new stock item to table. If there already is a stock item with same
	 * id, then existing item's quantity will be increased.
	 * 
	 * @param stockItem
	 */
	public void addItem(final StockItem stockItem) {
		try {
			StockItem item = getItemById(stockItem.getId());
			item.setQuantity(item.getQuantity() + stockItem.getQuantity());
			log.debug("Found existing item " + stockItem.getName()
					+ " increased quantity by " + stockItem.getQuantity());
		} catch (NoSuchElementException e) {
			System.out.println("ei ole sellist elementi");
			rows.add(stockItem);
			log.debug("Added " + stockItem.getName() + " quantity of "
					+ stockItem.getQuantity());
		}
		fireTableDataChanged();
	}

	/**
	 * Reduce stock item Quantity
	 * 
	 * DEPRECATED
	 * 
	 * @param stockItem
	 *            Item
	 * @param quantity
	 *            Quantity
	 * @throws Exception
	 *             Not enough items in stock
	 */
	private void reduceItemQuantity(final StockItem stockItem, int quantity)
			throws Exception {
		if (stockItem.getQuantity() < quantity) {
			throw new Exception();
		}
		this.getItemById(stockItem.getId()).reduceQuantity(quantity);
	}

	/**
	 * Sell items from WareHouse
	 * 
	 * @param stockItem
	 *            Item to sell
	 * @throws Exception
	 *             Not enough items in stock
	 */
	private void reduceItemQuantity(final SoldItem soldItem)
			throws OutOfStockException {
		if (this.getItemById(soldItem.getId()).getQuantity() < soldItem
				.getQuantity()) {
			throw new OutOfStockException();
		}
		this.getItemById(soldItem.getId()).reduceQuantity(
				soldItem.getQuantity());
	}

	/**
	 * Sell items from WareHouse
	 * 
	 * @param stockItem
	 *            List of items to be sold
	 * @throws Exception
	 */
	public void sellItem(final List<SoldItem> soldItem)
			throws OutOfStockException {
		for (SoldItem item : soldItem) {
			this.reduceItemQuantity(item);
		}
	}

	@Override
	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < headers.length; i++)
			buffer.append(headers[i] + "\t");
		buffer.append("\n");

		for (final StockItem stockItem : rows) {
			buffer.append(stockItem.getId() + "\t");
			buffer.append(stockItem.getName() + "\t");
			buffer.append(stockItem.getPrice() + "\t");
			buffer.append(stockItem.getQuantity() + "\t");
			buffer.append("\n");
		}

		return buffer.toString();
	}

}
