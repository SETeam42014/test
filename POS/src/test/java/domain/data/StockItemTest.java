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
		stockItem1 = new StockItem((long) 1, "Lauapiim", "Jook", 10.0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testgetColumnFalseIndex() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testgetColumnCorrectIndex() {
		assertEquals(1, stockItem1.getColumn(0));
	}

	@Test(expected = RuntimeException.class)
	public void testgetColumnOutOfBoundsIndex() {
		stockItem1.getColumn(6);
	}

	@Test
	public void testStockItemId() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStockItemName() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStockItemPrice() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testStockItemDescription() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testClone() {
		fail("Not yet implemented"); // TODO
	}

}
