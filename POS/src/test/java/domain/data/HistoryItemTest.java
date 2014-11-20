/**
 * 
 */
package test.java.domain.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * @author Johani
 *
 */
public class HistoryItemTest {

	StockItem stockItem1;
	StockItem stockItem2;
	SoldItem soldItem1;
	SoldItem soldItem2;
	SoldItem soldItem3;
	SoldItem soldItem4;
	SoldItem soldItem5;
	double sum;
	List<SoldItem> soldItems = new ArrayList<SoldItem>();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		stockItem1 = new StockItem((long) 1, "Lauaviin", "Alkohol", 15.0);
		stockItem2 = new StockItem((long) 1, "Hapukurk", "Lisand", 2.0);
		soldItem1 = new SoldItem(stockItem1, 0);
		soldItem2 = new SoldItem(stockItem2, 1);
		soldItem3 = new SoldItem(stockItem1, 2);
		soldItem4 = new SoldItem(stockItem2, 3);
		soldItem5 = new SoldItem(stockItem1, 4);
		soldItems.add(soldItem2);
		soldItems.add(soldItem3);
		soldItems.add(soldItem4);
		sum = 1 * 2.0 + 2 * 15.0 + 3 * 2;
		// soldItems.add(soldItem1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link ee.ut.math.tvt.salessystem.domain.data.HistoryItem#HistoryItem(java.util.List)}
	 * .
	 */
	@Test
	public void testHistoryItemDoubleListOfSoldItem() {
		HistoryItem historyItem = new HistoryItem(soldItems);
		assertEquals(soldItems, historyItem.getItems());
	}

	/**
	 * Test method for
	 * {@link ee.ut.math.tvt.salessystem.domain.data.HistoryItem#getSum()}.
	 */
	@Test
	public void testGetSum() {
		HistoryItem historyItem = new HistoryItem(soldItems);
		assertTrue(this.sum == historyItem.getSum());
	}

}
