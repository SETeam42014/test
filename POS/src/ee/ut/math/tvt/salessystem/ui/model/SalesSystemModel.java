package ee.ut.math.tvt.salessystem.ui.model;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;

/**
 * Main model. Holds all the other models.
 */
public class SalesSystemModel {

	private static final Logger log = Logger.getLogger(SalesSystemModel.class);

	// History model
	private HistoryTableModel historyTableModel;

	// Warehouse model
	private StockTableModel warehouseTableModel;

	// Current shopping cart model
	private PurchaseInfoTableModel currentPurchaseTableModel;

	private final SalesDomainController domainController;

	/**
	 * Construct application model.
	 * 
	 * @param domainController
	 *            Sales domain controller.
	 */
	public SalesSystemModel(SalesDomainController domainController) {
		this.domainController = domainController;

		this.historyTableModel = new HistoryTableModel();

		this.warehouseTableModel = new StockTableModel();
		this.currentPurchaseTableModel = new PurchaseInfoTableModel();

		// populate stock model with data from the warehouse
		// DEPRICATED
		this.warehouseTableModel.populateWithData(this.domainController
				.loadWarehouseState());

	}

	public void updateStock() {
		this.warehouseTableModel.populateWithData(this.domainController
				.loadWarehouseState());
		log.debug("Stock update triggered");
	}

	public StockTableModel getWarehouseTableModel() {
		return this.warehouseTableModel;
	}

	public PurchaseInfoTableModel getCurrentPurchaseTableModel() {
		return this.currentPurchaseTableModel;
	}

	public HistoryTableModel getHistoryTableModel() {
		return this.historyTableModel;
	}

}
