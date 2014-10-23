package ee.ut.math.tvt.salessystem.domain.controller.impl;

import java.util.ArrayList;
import java.util.List;

import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;

/**
 * Implementation of the sales domain controller.
 */
public class SalesDomainControllerImpl implements SalesDomainController {

	public void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException {
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
		// after submit, synchronize warehouse data
	}

	public void cancelCurrentPurchase() throws VerificationFailedException {
		// XXX - Cancel current purchase
	}

	public void cancelCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) throws VerificationFailedException {
		// XXX - Cancel current purchase
		// Reload data from database?
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

	public List<StockItem> loadWarehouseState(List<StockItem> wareHouse) {
		return wareHouse;
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
