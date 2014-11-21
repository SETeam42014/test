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
	private SoldItem soldItem1;
    private List<SoldItem> soldItems;
    private HistoryItem historyItem1;
    private HistoryTableModel historyTableModel;

	
	@Before
	public void setUp() throws Exception {
		soldItem1 = new SoldItem(new StockItem((long) 1, "Product1", "Something", 2.5), 90);
		soldItems = new ArrayList<>();
	soldItems.add(soldItem1);
	historyItem1 = new HistoryItem(soldItems);
	historyItem1.setId((long) 1);
	historyTableModel = new HistoryTableModel();
	historyTableModel.addItem(historyItem1);		
	}

	
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetSum() {
		assertEquals(historyItem1.getSum(),
				historyTableModel.getColumnValue(historyItem1, 1));     
	}

}
