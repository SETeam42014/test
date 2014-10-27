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
public interface SalesDomainController {

	/**
	 * Load the current state of the warehouse.
	 * 
	 * @return List of ${link
	 *         ee.ut.math.tvt.salessystem.domain.data.StockItem}s.
	 */
	public List<StockItem> loadWarehouseState();

	/**
	 * Dummy loadWareHouse
	 * 
	 * @param wareHouse
	 * @return wareHouse
	 */
	public List<StockItem> loadWarehouseState(List<StockItem> wareHouse);

	// business processes
	/**
	 * Initiate new business transaction - purchase of the goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public void startNewPurchase() throws VerificationFailedException;

	/**
	 * Rollback business transaction - purchase of goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public void cancelCurrentPurchase() throws VerificationFailedException;

	public void submitCurrentPurchase(List<SoldItem> goods)
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
	public void submitCurrentPurchase(List<SoldItem> goods,
			List<StockItem> stock) throws VerificationFailedException,
			OutOfStockException;

}
