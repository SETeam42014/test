/**
 * 
 */
package test.java.domain.data;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * @author Johani
 *
 */
public class SoldItemTest {
	SoldItem soldItem1;
	int quantity = 2;
	double sum;

	@Before
	public void setUp() throws Exception {
		StockItem stock = new StockItem((long) 1, "Kalapulgad", "Suupiste",
				4.0, 12);
		soldItem1 = new SoldItem(stock, 4);
		sum = 4.0 * 4;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSoldItemId() {
		assertEquals(1, soldItem1.getId(), 0.0001);
	}

	@Test
	public void testSoldItemName() {
		assertEquals("Kalapulgad", soldItem1.getName());
	}

	@Test
	public void testSoldItemPrice() {
		assertEquals(4.0, soldItem1.getPrice(), 0.0001);
	}

	@Test
	public void testSoldItemQuantity() {
		assertEquals(4, soldItem1.getQuantity(), 0.0001);
	}

	@Test
	public void testgetSum() {
		assertEquals(sum, soldItem1.getSum(), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetSumWithZeroQuantity() {
		soldItem1.setQuantity(0);
		assertEquals(sum, soldItem1.getSum(), 0.0001);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testgetSumWithNegativeQuantity() {
		soldItem1.setQuantity(-3);
		assertEquals(sum, soldItem1.getSum(), 0.0001);
	}
}