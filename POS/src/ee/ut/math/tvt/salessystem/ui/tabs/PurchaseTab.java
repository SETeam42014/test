package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.domain.exception.VerificationFailedException;
import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;
import ee.ut.math.tvt.SETeam42014.Intro;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.InputMismatchException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.apache.log4j.Logger;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "Point-of-sale" in the menu).
 */
public class PurchaseTab {

	private static final Logger log = Logger.getLogger(PurchaseTab.class);

	private final SalesDomainController domainController;

	private JButton newPurchase;

	private JButton submitPurchase;

	private JButton cancelPurchase;

	private PurchaseItemPanel purchasePane;

	private SalesSystemModel model;

	/**
	 * Default constructor
	 * 
	 * @param controller
	 *            SalesDomainController
	 * @param model
	 *            SalesSystemModel
	 */
	public PurchaseTab(SalesDomainController controller, SalesSystemModel model) {
		this.domainController = controller;
		this.model = model;
	}

	/**
	 * The purchase tab. Consists of the purchase menu, current purchase dialog
	 * and shopping cart table.
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add the purchase menu
		panel.add(getPurchaseMenuPane(), getConstraintsForPurchaseMenu());

		// Add the main purchase-panel
		purchasePane = new PurchaseItemPanel(model);
		panel.add(purchasePane, getConstraintsForPurchasePanel());

		return panel;
	}

	/**
	 * The purchase menu. Contains buttons "New purchase", "Submit", "Cancel".
	 * 
	 * @return Purchase menu panel
	 */
	private Component getPurchaseMenuPane() {
		JPanel panel = new JPanel();

		// Initialize layout
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gc = getConstraintsForMenuButtons();

		// Initialize the buttons
		newPurchase = createNewPurchaseButton();
		submitPurchase = createConfirmButton();
		cancelPurchase = createCancelButton();

		// Add the buttons to the panel, using GridBagConstraints we defined
		// above
		panel.add(newPurchase, gc);
		panel.add(submitPurchase, gc);
		panel.add(cancelPurchase, gc);

		return panel;
	}

	/**
	 * Creates the button "New purchase"
	 * 
	 * @return JButton
	 */
	private JButton createNewPurchaseButton() {
		JButton b = new JButton("New purchase");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newPurchaseButtonClicked();
			}
		});

		return b;
	}

	/**
	 * Creates the "Confirm" button
	 * 
	 * @return JButton
	 */
	private JButton createConfirmButton() {
		JButton b = new JButton("Confirm");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				submitPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/**
	 * Creates the "Cancel" button
	 * 
	 * @return JButton
	 */
	private JButton createCancelButton() {
		JButton b = new JButton("Cancel");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancelPurchaseButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/*
	 * === Event handlers for the menu buttons (get executed when the buttons
	 * are clicked)
	 */

	/** Event handler for the <code>new purchase</code> event. */
	protected void newPurchaseButtonClicked() {
		log.info("New sale process started");
		try {
			domainController.startNewPurchase();
			startNewSale();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/**
	 * Event handler for the <code>cancel purchase</code> event. Now adds added
	 * products back to warehouse, when purchase isn't confirmed
	 */
	protected void cancelPurchaseButtonClicked() {
		log.info("Sale cancelled");
		try {
			domainController.cancelCurrentPurchase();
			endSale();
			/*
			 * DEPRICATED for (SoldItem i : model.getCurrentPurchaseTableModel()
			 * .getTableRows()) { StockItem stockItem = i.getStockItem();
			 * stockItem .setQuantity(stockItem.getQuantity() +
			 * i.getQuantity()); } ;
			 */
			model.getCurrentPurchaseTableModel().clear();
		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		}
	}

	/**
	 * Event handler for the <code>submit purchase</code> event. At the moment
	 * payment window also here.
	 */
	protected void submitPurchaseButtonClicked() {
		log.info("Sale complete");
		try {
			if (model.getCurrentPurchaseTableModel().getRowCount() == 0)
				throw new NullPointerException();
			log.debug("Contents of the current basket:\n"
					+ model.getCurrentPurchaseTableModel());
			domainController.submitCurrentPurchase(model
					.getCurrentPurchaseTableModel().getTableRows(), model
					.getWarehouseTableModel().getTableRows());

			while (createPaymentWindow() == 1)
				;

		} catch (VerificationFailedException e1) {
			log.error(e1.getMessage());
		} catch (OutOfStockException e) {
			log.error("Product out of Stock");
			JOptionPane.showMessageDialog(null, "Not enough product in stock",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e) {
			log.error("No items in cart");
		}
	}

	/**
	 * https://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-
	 * places
	 * 
	 * @param value
	 * @param places
	 * @return
	 */
	public static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	/*
	 * === Helper methods that bring the whole purchase-tab to a certain state
	 * when called.
	 */
	private int createPaymentWindow() {
		try {
			double sum = 0;
			for (SoldItem item : model.getCurrentPurchaseTableModel()
					.getTableRows()) {
				sum += item.getSum();
			}
			sum = round(sum, 2);
			JTextField sumField = new JTextField(5);
			JTextPane sumPane = new JTextPane();
			sumPane.setText(Double.toString(sum));
			JPanel paymentPanel = new JPanel();
			paymentPanel.add(new JLabel("Bill"));
			paymentPanel.add(sumPane);
			paymentPanel.add(new JLabel("Payment"));
			paymentPanel.add(sumField);
			paymentPanel.add(Box.createHorizontalStrut(15)); // a spacer

			// int result = JOptionPane.showConfirmDialog(null, myPanel,
			// "Please Enter Payment size", JOptionPane.OK_CANCEL_OPTION);

			if (JOptionPane.showConfirmDialog(null, paymentPanel,
					"Please Enter Payment size", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
				JTextPane paymentPane = new JTextPane();
				paymentPane.setText(sumField.getText());
				paymentPanel.remove(sumField);
				paymentPanel.add(paymentPane);
				submitPayment(paymentPanel,
						round(Double.parseDouble(paymentPane.getText()), 2)
								- sum);
				this.model.getHistoryTableModel()
						.addItem(
								new HistoryItem(sum, model
										.getCurrentPurchaseTableModel()
										.getTableRows()));
				endSale();
				model.getCurrentPurchaseTableModel().clear();
			}
		} catch (IllegalArgumentException e) {
			log.error(e);
			return 1;
		}
		return 0;
	}

	private int submitPayment(JPanel paymentPanel, double payBack)
			throws IllegalArgumentException {
		if (payBack < 0.0)
			throw new IllegalArgumentException("Payment not sufficient.");
		JTextPane payBackField = new JTextPane();
		payBackField.setText(Double.toString(payBack));
		paymentPanel.add(new JLabel("Money back"));
		paymentPanel.add(payBackField);
		return JOptionPane.showConfirmDialog(null, paymentPanel,
				"Please Enter Payment size", JOptionPane.CLOSED_OPTION);
	}

	// switch UI to the state that allows to proceed with the purchase
	private void startNewSale() {
		purchasePane.reset();

		purchasePane.setEnabled(true);
		submitPurchase.setEnabled(true);
		cancelPurchase.setEnabled(true);
		newPurchase.setEnabled(false);
	}

	// switch UI to the state that allows to initiate new purchase
	private void endSale() {
		purchasePane.reset();

		cancelPurchase.setEnabled(false);
		submitPurchase.setEnabled(false);
		newPurchase.setEnabled(true);
		purchasePane.setEnabled(false);
	}

	/*
	 * === Next methods just create the layout constraints objects that control
	 * the the layout of different elements in the purchase tab. These
	 * definitions are brought out here to separate contents from layout, and
	 * keep the methods that actually create the components shorter and cleaner.
	 */

	private GridBagConstraints getConstraintsForPurchaseMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getConstraintsForPurchasePanel() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	// The constraints that control the layout of the buttons in the purchase
	// menu
	private GridBagConstraints getConstraintsForMenuButtons() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 0;
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridwidth = GridBagConstraints.RELATIVE;

		return gc;
	}

}
