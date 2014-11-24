/**
 * 
 */
package test.java.domain.model;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.model.HistoryTableModel;

/**
 * @author Johani
 *
 */
public class HistoryTableModelTest {
	private StockItem stockItem1;
	private SoldItem soldItem1;
	private List<SoldItem> soldItems;
	private HistoryItem historyItem1;
	private HistoryTableModel historyTableModel;

	// private HistoryTableModel historyTableModel;

	@Before
	public void setUp() throws Exception {
		stockItem1 = new StockItem((long) 1, "Product1", "Something", 2.5, 20);
		soldItem1 = new SoldItem(stockItem1, 90);
		soldItems = new ArrayList<>();
		soldItems.add(soldItem1);
		historyItem1 = new HistoryItem(soldItems);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHistoryTableModelDefaultConstructor() {
		historyTableModel = new HistoryTableModel();
		assertEquals(0, historyTableModel.getRowCount());
	}

	@Test
	public void testAddItem() {
		historyTableModel = new HistoryTableModel();
		historyTableModel.addItem(historyItem1);
		assertEquals(1, historyTableModel.getRowCount());
	}
}
