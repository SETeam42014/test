/**
 * 
 */
package test.java.domain.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;

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

	// getColumn removed from StockItem
	// @Test
	// public void testgetColumnCorrectIndex() {
	// assertEquals((long) 1, stockItem1.getColumn(0));
	// }

	// @Test(expected = RuntimeException.class)
	// public void testgetColumnOutOfBoundsIndex() {
	// stockItem1.getColumn(6);
	// }

	@Test(expected = IllegalArgumentException.class)
	public void testStockSetItemIdNegative() {
		stockItem1.setId((long) -1);
	}

	@Test
	public void testStockSetItemId() {
		stockItem1.setId((long) 3);
		assertEquals((long) 3, (long) stockItem1.getId());
	}

	@Test(expected = NullPointerException.class)
	public void testStockSetItemNameNull() {
		stockItem1.setName(null);
	}

	@Test
	public void testStockSetItemName() {
		stockItem1.setName("Piim");
		assertEquals("Piim", stockItem1.getName());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStockSetItemPriceNegative() {
		stockItem1.setPrice(-1.3);
	}

	@Test
	public void testStockSetItemPrice() {
		stockItem1.setPrice(5.0);
		assertEquals(5.0, stockItem1.getPrice(), 0.0001);
	}

	@Test(expected = NullPointerException.class)
	public void testStockSetItemDescriptionNull() {
		stockItem1.setDescription(null);
	}

	@Test
	public void testStockSetItemDescription() {
		stockItem1.setDescription("kook");
		assertEquals("kook", stockItem1.getDescription());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testStockSetItemQuantityNegative() {
		stockItem1.setQuantity(-1);
	}

	@Test
	public void testStockSetItemQuantity() {
		stockItem1.setQuantity(3);
		assertEquals(3, stockItem1.getQuantity());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testReduceNotPositiveQuantity() throws OutOfStockException {
		stockItem1.reduceQuantity(-1);
	}

	@Test(expected = OutOfStockException.class)
	public void testReduceQuantityToNegative() throws OutOfStockException {
		stockItem1.setQuantity(0);
		stockItem1.reduceQuantity(1);
	}

	@Test
	public void testReduceQuantityCorrect() throws OutOfStockException {
		stockItem1.setQuantity(2);
		stockItem1.reduceQuantity(1);
		assertEquals(1, stockItem1.getQuantity());
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
