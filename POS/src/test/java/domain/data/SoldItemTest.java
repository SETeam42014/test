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

	
	@Before
	public void setUp() throws Exception {
		soldItem1 = new SoldItem(new StockItem((long) 1, "Kalapulgad", "Suupiste", 4.0, 12), 4);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link ee.ut.math.tvt.salessystem.domain.data.SoldItem#SoldItem(ee.ut.math.tvt.salessystem.domain.data.StockItem, int)}.
	 */

	/**
	 * Test method for {@link ee.ut.math.tvt.salessystem.domain.data.SoldItem#SoldItem()}.
	 */
	
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
		assertEquals(12, soldItem1.getQuantity(), 0.0001);
	}
    
	@Test
	public void testgetSum() {
		assertEquals(soldItem1.getSum(), soldItem1.getPrice() * soldItem1.getQuantity(), 0.0001);
	}
	
	@Test
	public void testgetSumWithZeroQuantity() {
		soldItem1.setQuantity(0);
		assertEquals(soldItem1.getSum(), 0.0, 0.0001);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testgetSumWithNegativeQuantity() {
		soldItem1.setQuantity(-3);
		assertEquals(soldItem1.getSum(), -12.0, 0.0001);
	}
	
}
>>>>>>> branch 'development' of https://github.com/SETeam42014/test.git
