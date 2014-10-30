/**
 * 
 */
package ee.ut.math.tvt.salessystem.domain.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

//import ee.ut.tvt.hibernateDemo.model.Course;
//import ee.ut.tvt.hibernateDemo.model.Lecturer;
//import ee.ut.tvt.hibernateDemo.model.Speciality;
//import ee.ut.tvt.hibernateDemo.model.Student;
//import ee.ut.tvt.hibernateDemo.util.HibernateUtil;

@SuppressWarnings("unchecked")
public class HibernateDataService {

	private Session session = HibernateUtil.currentSession();

	public List<SoldItem> getSoldItems() {
		List<SoldItem> result = session.createQuery("from Solditem").list();
		return result;
	}

	// // WTH??? - need to research it
	// // public List<Lecturer> getLecturers() {
	// // return Collections.checkedList(session.createQuery("from Lecturer")
	// // .list(), Lecturer.class);
	// // }
	//
	public List<StockItem> getStockItems() {
		// CASE SENSITIVE
		List<StockItem> result = session.createQuery("from StockItem").list();

		// This is the same thing
		// return Collections.checkedList(session.createQuery("from StockItem")
		// .list(), StockItem.class);
		return result;
	}

	public List<HistoryItem> getHistoryItems() {
		List<HistoryItem> result = session.createQuery("from Historyitem")
				.list();
		return result;
	}

}
