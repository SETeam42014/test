/**
 * 
 */
package test.java.domain.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.model.StockTableModel;

public class StockTableModelTest {
	private StockItem stockItem1;
	private StockItem stockItem2;
	private StockItem stockItem3;
	private StockItem stockItem4;
	private SoldItem soldItem1;
	private SoldItem soldItem2;
	private SoldItem soldItem3;
	private StockTableModel stock;

	@Before
	public void setUp() {
		stockItem1 = new StockItem((long) 1, "Lauaviin", "Alkohol", 15.0, 3);
		stockItem2 = new StockItem((long) 2, "Hapukurk", "Lisand", 2.0, 12);
		stockItem3 = new StockItem((long) 3, "Lauaviin", "Jook", 6.2, 42);
		stockItem4 = new StockItem((long) 1, "Koorejuust", "Toit", 10.0, 12);
		soldItem1 = new SoldItem(stockItem1, 1);
		soldItem2 = new SoldItem(stockItem2, 1);
		soldItem3 = new SoldItem(stockItem1, 2);
		stock = new StockTableModel();
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testStockTableModelDefaultConstructor() {
		assertTrue(stock.getRowCount() == 0);
	}

	@Test
	public void testStockTableAddItem() {
		stock.addItem(stockItem1);
		assertTrue(stock.getRowCount() == 1);
		assertEquals(stockItem1, stock.getItemAt(0));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStockTableAddItemWithSameId() {
		stock.addItem(stockItem1);
		stock.addItem(stockItem4);
	}

	@Test
	public void testStockTableAddItemWithSameName() {
		stock.addItem(stockItem1);
		stock.addItem(stockItem3);
		// TODO
		fail("Unhandled test case");
	}

	@Test(expected = NoSuchElementException.class)
	public void testStockTableSellItemWithNoStock() throws OutOfStockException {
		stock.sellItem(soldItem1);
	}

	@Test(expected = NoSuchElementException.class)
	public void testStockTableSellItemWithNotInStock()
			throws OutOfStockException {
		stock.addItem(stockItem2);
		stock.sellItem(soldItem1);
	}

	@Test(expected = OutOfStockException.class)
	public void testStockTableSellItemWithNotEnoughInStock()
			throws OutOfStockException {
		stock.addItem(stockItem1);
		stock.sellItem(new SoldItem(stockItem1, 4));
	}

	@Test
	public void testStockTableSellItemWithEnoughInStock()
			throws OutOfStockException {
		int goodsInStock = stockItem2.getQuantity();
		int goodsLeftInStock;
		stock.addItem(stockItem2);
		stock.sellItem(new SoldItem(stockItem2, 4));
		goodsLeftInStock = stock.getItemAt(0).getQuantity();
		assertEquals(4, goodsInStock - goodsLeftInStock);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getValueAtWrongRowIndex() {
		int rowCount = stock.getRowCount();
		int columnCount = stock.getColumnCount();
		stock.getValueAt(rowCount + 1, columnCount);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void getValueAtWrongColumnIndex() {
		int rowCount = stock.getRowCount();
		int columnCount = stock.getColumnCount();
		stock.getValueAt(rowCount, columnCount + 1);
	}

	@Test
	public void getValueInFirstCell() {
		stock.addItem(stockItem1);
		int rowCount = stock.getRowCount();
		int columnCount = stock.getColumnCount();
		System.out.println(rowCount + ";;" + columnCount);
		stock.getValueAt(1, 1);
	}

	@Test
	public void testStockTableModelToString() {
		String toString = stock.toString();
		assertTrue(toString.length() > 0);
	}

}
