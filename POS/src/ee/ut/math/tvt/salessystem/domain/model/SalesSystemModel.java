package ee.ut.math.tvt.salessystem.domain.model;

import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
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

	public void updateStockTable() {
		log.debug("Stock update triggered");
		this.stockTableModel.populateWithData(this.databaseService
				.getStockItems());
	}

	public void getHistoryTableFromDatabase() {
		log.debug("History update triggered");
		this.historyTableModel.populateWithData(this.databaseService
				.getHistoryItems());
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
		this.updateStockTable();
		this.databaseService.startTransaction();
	}

	public void submitCurrentPurchase() throws VerificationFailedException {
		this.databaseService.endTransaction();
		log.debug("Purchase submitted");
		HistoryItem historyItem = new HistoryItem(
				this.currentPurchaseInfoTableModel.getSum(),
				this.currentPurchaseInfoTableModel.getTableRows());
		this.addItemToHistoryTable(historyItem);
		this.currentPurchaseInfoTableModel.clear();
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		log.debug("Purchase canceled.");
		List<SoldItem> goods = this.currentPurchaseInfoTableModel
				.getTableRows();
		for (SoldItem item : goods) {
			this.stockTableModel.addItem(new StockItem(item.getId(), item
					.getName(), "Automatically added by system", item
					.getPrice(), item.getQuantity()));
		}
		this.currentPurchaseInfoTableModel.clear();
		this.databaseService.endTransaction();
	}

	public void sellItem(SoldItem soldItem) throws OutOfStockException {
		try {
			this.stockTableModel.sellItem(soldItem);
			this.currentPurchaseInfoTableModel.addItem(soldItem);
		} catch (OutOfStockException exception) {
			throw exception;
		}

	}

	public void addItemToStockTable(StockItem stockItem) {
		this.databaseService.addStockItem(stockItem);
		this.stockTableModel.populateWithData(this.databaseService
				.getStockItems());
	}

	public void addItemToHistoryTable(HistoryItem historyItem) {
		this.databaseService.addHistoryItem(historyItem);
		// for (SoldItem soldItem : historyItem.getItems()) {
		// this.databaseService.addSoldItem(soldItem);
		// }
		this.historyTableModel.populateWithData(this.databaseService
				.getHistoryItems());
	}
}
