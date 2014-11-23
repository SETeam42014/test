/**
 * 
 */
package test.java.domain.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

public class HistoryItemTest {

	private StockItem stockItem1;
	private StockItem stockItem2;
	private SoldItem soldItem1;
	private SoldItem soldItem2;
	private SoldItem soldItem3;
	private SoldItem soldItem4;
	private SoldItem soldItem5;
	private double sum;
	private List<SoldItem> soldItems = new ArrayList<SoldItem>();
	HistoryItem historyItem;

	@Before
	public void setUp() throws Exception {
		stockItem1 = new StockItem((long) 1, "Lauaviin", "Alkohol", 15.0, 3);
		stockItem2 = new StockItem((long) 2, "Hapukurk", "Lisand", 2.0, 12);
		soldItem1 = new SoldItem(stockItem1, 1);
		soldItem2 = new SoldItem(stockItem2, 1);
		soldItem3 = new SoldItem(stockItem1, 2);
		soldItem4 = new SoldItem(stockItem2, 3);
		soldItem5 = new SoldItem(stockItem1, 4);
		soldItems.add(soldItem2);
		soldItems.add(soldItem3);
		soldItems.add(soldItem4);
		sum = 1 * 2.0 + 2 * 15.0 + 3 * 2;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHistoryItemConstructorWithSoldItems() {
		historyItem = new HistoryItem(soldItems);
		assertEquals(soldItems, historyItem.getItems());
	}

	@Test(expected = NullPointerException.class)
	public void testHistoryItemConstructorWithNullItems() {
		historyItem = new HistoryItem(null);
	}

	@Test
	public void testHistoryItemConstructorWithNoVariables() {
		historyItem = new HistoryItem();
		List<SoldItem> items = historyItem.getItems();
		assertEquals(0, items.size());
	}

	@Test
	public void testGetSum() {
		historyItem = new HistoryItem(soldItems);
		assertTrue(this.sum == historyItem.getSum());
	}

	@Test
	public void testGetSumWithNoItems() {
		historyItem = new HistoryItem(new ArrayList<SoldItem>());
		assertEquals(0.0, historyItem.getSum(), 0.00001);
	}

}
