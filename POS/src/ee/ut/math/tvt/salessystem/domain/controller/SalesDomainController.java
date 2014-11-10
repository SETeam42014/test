package ee.ut.math.tvt.salessystem.domain.controller;

import java.util.List;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.domain.service.HibernateDataService;

/**
 * Sales domain controller is responsible for the domain specific business
 * processes.
 */
public abstract class SalesDomainController {

	// business processes
	/**
	 * Initiate new business transaction - purchase of the goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public abstract void startNewPurchase() throws VerificationFailedException;

	/**
	 * Roll back business transaction - purchase of goods.
	 * 
	 * @throws VerificationFailedException
	 */
	public abstract void cancelCurrentPurchase()
			throws VerificationFailedException;

	/**
	 * Commit business transaction - purchase of goods.
	 * 
	 * @param goods
	 *            Goods that the buyer has chosen to buy.
	 * @throws VerificationFailedException
	 *             Not eligible to buy
	 * @throws OutOfStockException
	 *             Not enough in stock
	 */
	public abstract void submitCurrentPurchase(List<SoldItem> goods)
			throws VerificationFailedException, OutOfStockException;

	/**
	 * Commit business transaction - purchase of goods.
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
	// public abstract void submitCurrentPurchase(List<SoldItem> goods,
	// List<StockItem> stock) throws VerificationFailedException,
	// OutOfStockException;

	/**
	 * 
	 * @return
	 */
	public abstract List<StockItem> loadWarehouseState();

	/**
	 * End session to the database
	 */
	public abstract void endSession();
}
