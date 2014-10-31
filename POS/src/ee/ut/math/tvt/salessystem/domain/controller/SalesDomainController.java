package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
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

	/**
	 * Dummy loadWareHouse
	 * 
	 * @param wareHouse
	 * @return wareHouse
	 */
	public abstract List<StockItem> loadWarehouseState(List<StockItem> wareHouse);

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

	public abstract void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException;

	/**
	 * Commit business transaction - purchsae of goods.
	 * 
	 * @param goods
	 *            Goods that the buyer has chosen to buy.
	 * @param model
	 *            wareHouse model
	 * @throws VerificationFailedException
	 *             Not eligible to buy
	 * @throws OutOfStockException
	 *             Not enough in stock
	 */
	public abstract void submitCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) throws VerificationFailedException,
			OutOfStockException;

	/**
	 * End session to the database
	 */
	public abstract void endSession();
}
