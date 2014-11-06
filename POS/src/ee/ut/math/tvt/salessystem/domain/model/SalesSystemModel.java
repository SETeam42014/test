package ee.ut.math.tvt.salessystem.domain.model;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.service.HibernateDataService;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

	private static final Logger log = Logger.getLogger(SalesSystemModel.class);

	// History model
	private HistoryTableModel historyTableModel;

	// Warehouse model
	private StockTableModel stockTableModel;

	// Current shopping cart model
	private PurchaseInfoTableModel currentPurchaseInfoTableModel;

	// Database Service
	private final HibernateDataService databaseService;

	/**
	 * Construct application model.
	 */
	public SalesSystemModel() {
		this.databaseService = new HibernateDataService();

		// Create models
		this.historyTableModel = new HistoryTableModel();
		this.stockTableModel = new StockTableModel();
		this.currentPurchaseInfoTableModel = new PurchaseInfoTableModel();
	}

	public void updateStock() {
		log.debug("Stock update triggered");
		this.stockTableModel.populateWithData(this.databaseService
				.getStockItems());
	}

	public StockTableModel getStockTableModel() {
		return this.stockTableModel;
	}

	public PurchaseInfoTableModel getCurrentPurchaseInfoTableModel() {
		return this.currentPurchaseInfoTableModel;
	}

	public HistoryTableModel getHistoryTableModel() {
		return this.historyTableModel;
	}

	public void startNewPurchase() throws VerificationFailedException {
		// sync DB
	}

	public void submitCurrentPurchase() throws VerificationFailedException {
		try {
			for (SoldItem soldItem : this.currentPurchaseInfoTableModel
					.getTableRows()) {
				this.stockTableModel.sellItem(soldItem);
			}

		} catch (OutOfStockException e) {
			log.debug("Not enough items in stock.");
			this.cancelCurrentPurchase();
			return;
		}
		log.debug("Purchase submitted");
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		List<SoldItem> goods = this.currentPurchaseInfoTableModel
				.getTableRows();
		log.debug("Purchase canceled.");
		for (SoldItem item : goods) {
			try {
				this.stockTableModel.addItem(this.stockTableModel
						.getItemById(item.getId()));
			} catch (NoSuchElementException exception) {
				// if there is no product in the stock, that is sold (it
				// shouldn't happen, but just in case.
				// item is added to stock
				this.stockTableModel.addItem(new StockItem(item.getId(), item
						.getName(), "Automatically added by system", item
						.getPrice(), item.getQuantity()));
			}
		}
	}

	/**
	 * 
	 */
	public void updateHistory() {
		log.debug("History update triggered");
		this.historyTableModel.populateWithData(this.databaseService
				.getHistoryItems());
	}

}
