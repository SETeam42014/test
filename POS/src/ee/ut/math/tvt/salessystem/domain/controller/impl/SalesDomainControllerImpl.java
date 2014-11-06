package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.List;

import org.hibernate.HibernateException;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.domain.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.domain.model.StockTableModel;
import ee.ut.math.tvt.salessystem.util.HibernateUtil;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl extends SalesDomainController {

	private SalesSystemModel model;

	public SalesDomainControllerImpl() {
		this.model = new SalesSystemModel();
	}

	public SalesSystemModel getModel() {
		return model;
	}

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
		this.model.submitCurrentPurchase();
	}

	public void submitCurrentPurchase() throws VerificationFailedException {
		this.model.submitCurrentPurchase();
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		this.model.cancelCurrentPurchase();
	}

	public void startNewPurchase() throws VerificationFailedException {
		this.model.startNewPurchase();
	}

	/**
	 * Load Warehouse items
	 * 
	 * USED ONLY FOR CONSOLEUI
	 */
	public List<StockItem> loadWarehouseState() {
		this.updateStockTableModel();
		return this.model.getStockTableModel().getTableRows();
	}

	public StockTableModel getStockTableModel() {
		return this.model.getStockTableModel();
	}

	public void updateStockTableModel() {
		this.model.updateStockTable();
	}

	public void updateHistoryTableModel() {
		this.model.getHistoryTableFromDatabase();
	}

	public PurchaseInfoTableModel getCurrentPurchaseInfoTableModel() {
		return this.model.getCurrentPurchaseInfoTableModel();
	}

	public void addStockItem(StockItem stockItem) {
		// this.model.getStockTableModel().addItem(stockItem);
		this.model.addItemToStockTable(stockItem);
	}

	public HistoryItem getHistoryItemById(long l) {
		return this.model.getHistoryTableModel().getItemById(l);
	}

	/**
	 * End database session
	 */
	public void endSession() {
		try {
			HibernateUtil.closeSession();
		} catch (HibernateException e) {
			System.out.println(e);
		}
	}
}
