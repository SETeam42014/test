package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.service.HibernateDataService;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
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
		this.dbService = new HibernateDataService();
		this.model = new SalesSystemModel(this, dbService);
	}

	/**
	 * Get SalesSystem model
	 * 
	 * @return SalesSystemModel
	 */
	public SalesSystemModel getModel() {
		return model;
	}

	/**
	 * Set SalesSystem model
	 * 
	 * @param model
	 *            SalesSystemModel
	 */
	public void setModel(SalesSystemModel model) {
		this.model = model;
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException, OutOfStockException {
		this.getModel().getWarehouseTableModel().sellItem(goods);
	}

	public void submitCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) throws VerificationFailedException,
			OutOfStockException {
		/**
		 * Sell goods
		 */
		for (SoldItem soldItem : goods) {
			for (StockItem stockItem : stock) {
				if (soldItem.getId() == stockItem.getId()) {
					stockItem.reduceQuantity(soldItem.getQuantity());
				}
			}
		}
		/**
		 * Check if not out of stock
		 */
		for (StockItem stockItem : stock) {
			/**
			 * If out of stock, cancel purchase and throw error
			 */
			if (stockItem.getQuantity() < 0) {
				this.cancelCurrentPurchase(goods, stock);
				throw new OutOfStockException();
			}
		}

		// after submit, synchronize warehouse data
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	/**
	 * Cancel current purchase
	 * 
	 * @param goods
	 *            Purchased goods
	 * @param stock
	 *            Warehouse stock
	 * @throws VerificationFailedException
	 */
	public void cancelCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) {
		// XXX - Cancel current purchase
		// Reload data from database?
		for (SoldItem soldItem : goods) {
			for (StockItem stockItem : stock) {
				if (soldItem.getId() == stockItem.getId()) {
					// stockItem.reduceQuantity(-soldItem.getQuantity());
					stockItem.setQuantity(stockItem.getQuantity()
							+ soldItem.getQuantity());
				}
			}
		}
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase

	}

	public List<StockItem> loadWarehouseState(List<StockItem> wareHouse) {
		return wareHouse;
	}

	public List<HistoryItem> loadHistoryState() {
		return this.dbService.getHistoryItems();
	}

	/**
	 * Load Warehouse items
	 */
	public List<StockItem> loadWarehouseState() {
		// return this.dbService.getStockItems();
		// XXX mock implementation
		// NEXT Practical will implement loadWarehouseState with database to
		// here???
		List<StockItem> dataset = new ArrayList<StockItem>();

		StockItem chips = new StockItem(1l, "Lays chips", "Potato chips", 11.0,
				0);
		StockItem chupaChups = new StockItem(2l, "Chupa-chups", "Sweets", 8.0,
				8);
		StockItem frankfurters = new StockItem(3l, "Frankfurters",
				"Beer sauseges", 15.0, 12);
		StockItem beer = new StockItem(4l, "Free Beer", "Student's delight",
				0.0, 100);

		dataset.add(chips);
		dataset.add(chupaChups);
		dataset.add(frankfurters);
		dataset.add(beer);

		return dataset;
	}

	/**
	 * End DB session
	 */
	public void endSession() {
		HibernateUtil.closeSession();
	}
}
