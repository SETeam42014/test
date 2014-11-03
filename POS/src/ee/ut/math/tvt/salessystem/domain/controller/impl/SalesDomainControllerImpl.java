package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;

import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.service.HibernateDataService;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl extends SalesDomainController {

	private SalesSystemModel model;

	private HibernateDataService dbService;

	/**
	 * Default constructor
	 * 
	 * @param model
	 *            SalesSystemModel
	 */
	public SalesDomainControllerImpl(SalesSystemModel model) {
		this.model = model;
		this.dbService = new HibernateDataService();
	}

	public SalesDomainControllerImpl() {
		this.model = new SalesSystemModel(this, dbService);
		this.dbService = new HibernateDataService();
	}

	public SalesSystemModel getModel() {
		return model;
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException, OutOfStockException {
		try {
			this.model.getWarehouseTableModel().sellItem(goods);
		} catch (OutOfStockException e) {
			this.cancelCurrentPurchase();
		}
	}

	// public void submitCurrentPurchase(List<SoldItem> goods,
	// List<StockItem> stock) throws VerificationFailedException,
	// OutOfStockException {
	// /**
	// * Sell goods
	// */
	// for (SoldItem soldItem : goods) {
	// for (StockItem stockItem : stock) {
	// if (soldItem.getId() == stockItem.getId()) {
	// stockItem.reduceQuantity(soldItem.getQuantity());
	// }
	// }
	// }
	// /**
	// * Check if not out of stock
	// */
	// for (StockItem stockItem : stock) {
	// /**
	// * If out of stock, cancel purchase and throw error
	// */
	// if (stockItem.getQuantity() < 0) {
	// this.cancelCurrentPurchase(goods, stock);
	// throw new OutOfStockException();
	// }
	// }
	//
	// // after submit, synchronize warehouse data
	// }

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
		List<SoldItem> goods = this.model.getCurrentPurchaseTableModel()
				.getTableRows();
		for (SoldItem item : goods) {
			StockItem warehouseItem = this.model.getWarehouseTableModel()
					.getItemById(item.getId());
			warehouseItem.setQuantity(warehouseItem.getQuantity()
					+ item.getQuantity());
		}
	}

	// /**
	// * Cancel current purchase
	// *
	// * @param goods
	// * Purchased goods
	// * @param stock
	// * Warehouse stock
	// * @throws VerificationFailedException
	// */
	// public void cancelCurrentPurchase(List<SoldItem> goods,
	// List<StockItem> stock) {
	// // XXX - Cancel current purchase
	// // Reload data from database?
	// for (SoldItem soldItem : goods) {
	// for (StockItem stockItem : stock) {
	// if (soldItem.getId() == stockItem.getId()) {
	// // stockItem.reduceQuantity(-soldItem.getQuantity());
	// stockItem.setQuantity(stockItem.getQuantity()
	// + soldItem.getQuantity());
	// }
	// }
	// }
	// // NEEDS new logic
	// }

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase

	}

	public void loadHistoryState() {
		this.model.getHistoryTableModel().populateWithData(
				this.dbService.getHistoryItems());
	}

	public void synchronizeWithDatabase() {
		// TODO
	}

	/**
	 * Load Warehouse items
	 */
	public List<StockItem> loadWarehouseState() {
		List<StockItem> warehouse = this.dbService.getStockItems();
		this.model.getWarehouseTableModel().populateWithData(warehouse);
		return warehouse;
		// return this.dbService.getStockItems();
		// XXX mock implementation
		// NEXT Practical will implement loadWarehouseState with database to
		// here???
		// BFORE:
		// List<StockItem> dataset = new ArrayList<StockItem>();
		//
		// StockItem chips = new StockItem(1l, "Lays chips", "Potato chips",
		// 11.0,
		// 0);
		// StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets",
		// 8.0,
		// 8);
		// StockItem frankfurters = new StockItem(3l, "Frankfurters",
		// "Beer sauseges", 15.0, 12);
		// StockItem beer = new StockItem(4l, "Free Beer", "Student's delight",
		// 0.0, 100);
		//
		// dataset.add(chips);
		// dataset.add(chupaChups);
		// dataset.add(frankfurters);
		// dataset.add(beer);
		//
		// return dataset;
	}

	/**
	 * End DB session
	 */
	public void endSession() {
		try {
			HibernateUtil.closeSession();
		} catch (HibernateException e) {
			System.out.println(e);
		}
	}
}
