package ee.ut.math.tvt.salessystem.domain.service;

//import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private Session session = HibernateUtil.currentSession();

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

	public void startTransaction() {
		session.beginTransaction();
	}

	public void addStockItem(StockItem stockItem) {
		session.beginTransaction();
		session.save(stockItem);
		session.getTransaction().commit();
	}

	public void endTransaction() {
		session.getTransaction().commit();
	}

	public void addHistoryItem(HistoryItem historyItem) {
		session.beginTransaction();
		session.save(historyItem);
		session.getTransaction().commit();
	}

	public void addSoldItem(SoldItem soldItem) {
		session.beginTransaction();
		session.save(soldItem);
		session.getTransaction().commit();
	}
}
