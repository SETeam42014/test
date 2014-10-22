package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public abstract class SalesDomainController {

	/**
	 * Load the current state of the warehouse.
	 * 
	 * @return List of ${link
	 *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
	 */
	public abstract List<StockItem> loadWarehouseState();

	// business processes
	/**
	 * Initiate new business transaction - purchase of the goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public abstract void startNewPurchase() throws VerificationFailedException;

	/**
	 * Rollback business transaction - purchase of goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public abstract void cancelCurrentPurchase()
			throws VerificationFailedException;

	/**
	 * Commit business transaction - purchsae of goods.
	 * 
	 * @param goods
	 *            Goods that the buyer has chosen to buy.
	 * @throws VerificationFailedException
	 */
	public abstract void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException;

}
