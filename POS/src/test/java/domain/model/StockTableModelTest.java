/**
 * 
 */
package test.java.domain.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import ee.ut.math.tvt.salessystem.domain.model.StockTableModel;

/**
 * @author Johani
 *
 */
public class StockTableModelTest {

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testStockTableModelDefaultConstructor() {
		StockTableModel stock = new StockTableModel();
		assertTrue(stock.getRowCount() == 0);
	}

}
