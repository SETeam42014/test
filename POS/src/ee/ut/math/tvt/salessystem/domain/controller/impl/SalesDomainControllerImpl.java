package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {
	// private List<StockItem> wareHouse;

	// private static final Logger log = Logger
	// .getLogger(SalesDomainControllerImpl.class);

	/*
	 * private SalesDomainControllerImpl() { // this.wareHouse =
	 * loadWarehouseState(); }
	 */
	public void submitCurrentPurchase() throws VerificationFailedException {
	}

	public void submitCurrentPurchase(List<SoldItem> goods,
			SalesSystemModel model) throws VerificationFailedException,
			OutOfStockException {
		model.getWarehouseTableModel().sellItem(goods);
		// Let's assume we have checked and found out that the buyer is
		// underaged and
		// cannot buy chupa-chups
		// throw new VerificationFailedException("Underaged!");
		// XXX - Save purchase
		/*
		 * try { for (SoldItem item : goods) { if (item.getName() ==
		 * "Chupa-chups") throw new VerificationFailedException(); } } catch
		 * (VerificationFailedException e) {
		 * 
		 * }
		 */
	}

	public void submitCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) throws VerificationFailedException,
			OutOfStockException {
		for (SoldItem soldItem : goods) {
			for (StockItem stockItem : stock) {
				if (soldItem.getId() == stockItem.getId()) {
					stockItem.reduceQuantity(soldItem.getQuantity());
				}
			}
		}
		for (StockItem stockItem : stock) {
			if (stockItem.getQuantity() < 0) {
				this.cancelCurrentPurchase(goods, stock);
				throw new OutOfStockException();
			}
		}
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void cancelCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) throws VerificationFailedException {
		// XXX - Cancel current purchase
		for (SoldItem soldItem : goods) {
			for (StockItem stockItem : stock) {
				if (soldItem.getId() == stockItem.getId()) {
					stockItem.reduceQuantity(-soldItem.getQuantity());
				}
			}
		}
	}

	public void startNewPurchase() throws VerificationFailedException {
		// XXX - Start new purchase
	}

	public List<StockItem> loadWarehouseState() {
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
}
