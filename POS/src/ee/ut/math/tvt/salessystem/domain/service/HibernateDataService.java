/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.service;

//import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private Session session = HibernateUtil.currentSession();
	private Transaction tx;

	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("from SoldItem").list();
		return result;
	}

	public List<StockItem> getStockItems() {
		// CASE SENSITIVE
		List<StockItem> result = session.createQuery("from StockItem").list();
		return result;
	}

	public List<HistoryItem> getHistoryItems() {
		List<HistoryItem> result = session.createQuery("from HistoryItem")
				.list();
		return result;
	}

	public void startSale() {
		this.tx = session.beginTransaction();
	}

	public void endSale() {
		tx.commit();
	}
}
