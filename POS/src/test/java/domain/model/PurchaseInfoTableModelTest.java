/**
 * 
 */
package test.java.domain.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.model.PurchaseInfoTableModel;

public class PurchaseInfoTableModelTest {
	private StockItem stockitem1;
	private StockItem stockitem2;

	private SoldItem solditem1;
	private SoldItem solditem2;
	private SoldItem solditem3;
	private PurchaseInfoTableModel model;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		model = new PurchaseInfoTableModel();

		stockitem1 = new StockItem((long) 1, "Lauapiim", "Jook", 0.75, 5);
		stockitem2 = new StockItem((long) 2, "Porgand", "Suupiste", 0.30, 5);

		solditem1 = new SoldItem(stockitem1, 1); // sum 0.75
		solditem2 = new SoldItem(stockitem1, 4); // sum 3
		solditem3 = new SoldItem(stockitem2, 1); // sum 0.3

		model.addItem(solditem1);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testaddItemExistingId() {
		model.addItem(solditem2);
		assertEquals(3.75, (double) model.getValueAt(0, 4), 0.0001);
		assertEquals(1, model.getRowCount());
	}

	@Test
	public void testaddItemNewId() {
		model.addItem(solditem3);
		assertEquals(1, (long) model.getValueAt(0, 0), 0.0001);
		assertEquals(2, (long) model.getValueAt(1, 0), 0.0001);
		assertEquals(2, model.getRowCount());
	}

	@Test
	public void testGetSumWithOneItem() {
		assertEquals(0.75, model.getSum(), 0.0001);
	}

	@Test
	public void testGetSumWithMultipleItems() {
		model.addItem(solditem3);
		assertEquals(1.05, model.getSum(), 0.0001);
	}

	/**
	 * @Test public void testgetColumnValueCorrectIndex() { assertEquals(1,
	 *       model.getColumnValue(solditem1, 0)); }
	 * @Test(expected = RuntimeException.class) public void
	 *                testgetColumnValueOutOfBoundsIndex() {
	 *                model.getColumnValue(solditem1, 8); }
	 * @Test public void testSoldItemGetId() { assertEquals(1,
	 *       model.getColumnValue(solditem1, 0)); }
	 * @Test public void testSoldItemGetName() { assertEquals("Lauapiim",
	 *       model.getColumnValue(solditem1, 1)); }
	 * @Test public void testSoldItemGetPrice() { assertEquals(0.75,
	 *       model.getColumnValue(solditem1, 2)); }
	 * @Test public void testSoldItemGetQuantity() { assertEquals(1,
	 *       model.getColumnValue(solditem1, 3)); }
	 * @Test public void testSoldItemGetSum() { assertEquals(0.75,
	 *       model.getColumnValue(solditem1, 4)); }
	 */

}
