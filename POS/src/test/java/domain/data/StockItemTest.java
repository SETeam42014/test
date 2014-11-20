/**
 * 
 */
package test.java.domain.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * @author Johani
 * 
 */
public class StockItemTest {
	StockItem stockItem1;
	StockItem stockItem2;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		stockItem1 = new StockItem((long) 1, "Lauapiim", "Jook", 10.0, 12);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testgetColumnCorrectIndex() {
		assertEquals((long) 1, stockItem1.getColumn(0));
	}

	@Test(expected = RuntimeException.class)
	public void testgetColumnOutOfBoundsIndex() {
		stockItem1.getColumn(6);
	}

	@Test
	public void testStockItemId() {
		assertEquals(1, stockItem1.getId(), 0.0001);
	}

	@Test
	public void testStockItemName() {
		assertEquals("Lauapiim", stockItem1.getName());
	}

	@Test
	public void testStockItemPrice() {
		assertEquals(10.0, stockItem1.getPrice(), 0.0001);
	}

	@Test
	public void testStockItemDescription() {
		assertEquals("Jook", stockItem1.getDescription());
	}

	@Test
	public void testStockItemQuantity() {
		assertEquals(12, stockItem1.getQuantity(), 0.0001);
	}

	@Test
	public void testCloneId() {
		StockItem stockItem2 = (StockItem) stockItem1.clone();
		assertEquals(stockItem1.getId(), stockItem2.getId(), 0.0001);
	}

	@Test
	public void testCloneName() {
		StockItem stockItem2 = (StockItem) stockItem1.clone();
		assertEquals(stockItem1.getName(), stockItem2.getName());
	}

	@Test
	public void testClonePrice() {
		StockItem stockItem2 = (StockItem) stockItem1.clone();
		assertEquals(stockItem1.getPrice(), stockItem2.getPrice(), 0.0001);
	}

	@Test
	public void testCloneDescription() {
		StockItem stockItem2 = (StockItem) stockItem1.clone();
		assertEquals(stockItem1.getDescription(), stockItem2.getDescription());
	}

	@Test
	public void testCloneQuantity() {
		StockItem stockItem2 = (StockItem) stockItem1.clone();
		assertEquals(stockItem1.getQuantity(), stockItem2.getQuantity(), 0.0001);
	}

}
