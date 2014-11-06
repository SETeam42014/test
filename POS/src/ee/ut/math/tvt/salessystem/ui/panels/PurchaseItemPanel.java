package ee.ut.math.tvt.salessystem.ui.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.NoSuchElementException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;

/**
 * Purchase pane + shopping cart tabel UI.
 */
public class PurchaseItemPanel extends JPanel {
	private static final Logger log = Logger.getLogger(PurchaseItemPanel.class);
	private static final long serialVersionUID = 1L;

	// Text field on the dialogPane
	private JTextField barCodeField;
	private JTextField quantityField;
	private JTextField nameField;
	private JTextField priceField;
	private JComboBox<String> products;

	private JButton addItemButton;

	private SalesDomainControllerImpl domainController;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesDomainControllerImpl domainController) {

		this.domainController = domainController;

		setLayout(new GridBagLayout());

		add(drawDialogPane(), getDialogPaneConstraints());
		add(drawBasketPane(), getBasketPaneConstraints());

		setEnabled(false);
	}

	// shopping cart pane
	private JComponent drawBasketPane() {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Shopping cart"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		JTable table = new JTable(
				domainController.getCurrentPurchaseInfoTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel unifiedPanel = new JPanel();
		unifiedPanel.setLayout(new BorderLayout());
		JPanel namePanel = new JPanel();
		namePanel.setLayout(new GridLayout(6, 1));
		JPanel itemPanel = new JPanel();
		itemPanel.setLayout(new GridLayout(6, 1));

		unifiedPanel.setBorder(BorderFactory.createTitledBorder("Product"));
		// Initialize the textfields

		products = new JComboBox<String>();
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();

		products.setEnabled(false);
		nameField.setEditable(false);
		priceField.setEditable(false);

		namePanel.add(new JLabel("Products:"));
		itemPanel.add(products);

		namePanel.add(new JLabel("Bar code:"));
		itemPanel.add(barCodeField);

		// - amount
		namePanel.add(new JLabel("Amount:"));
		itemPanel.add(quantityField);

		// - name
		namePanel.add(new JLabel("Name:"));
		itemPanel.add(nameField);

		// - price
		namePanel.add(new JLabel("Price:"));
		itemPanel.add(priceField);

		// - populate products combobox with product names
		populateProducts();

		// Create and add the button
		addItemButton = new JButton("Add to cart");
		addItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addItemEventHandler();
			}
		});
		/**
		 * JComboBox "products" ActionListener
		 */
		products.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				itemSelectHandler(e);
			}
		});

		// Fill the dialog fields if the bar code text field loses focus
		barCodeField.addFocusListener(new FocusListener() {
			public void focusGained(FocusEvent e) {
			}

			public void focusLost(FocusEvent e) {
				fillDialogFields();
			}
		});
		unifiedPanel.add(itemPanel, BorderLayout.EAST);
		unifiedPanel.add(namePanel, BorderLayout.WEST);
		unifiedPanel.add(addItemButton, BorderLayout.PAGE_END);

		return unifiedPanel;
	}

	private StockItem getItemByBarcode() throws NumberFormatException,
			NoSuchElementException {
		return this.domainController.getStockTableModel().getItemById(
				Long.parseLong(this.barCodeField.getText()));
	}

	// Fill dialog with data from the "database".
	private void fillDialogFields() {
		try {
			StockItem stockItem = this.getItemByBarcode();
			nameField.setText(stockItem.getName());
			priceField.setText(String.valueOf(stockItem.getPrice()));
			products.setSelectedItem(stockItem.getName());
		} catch (NoSuchElementException exception) {
			reset();
		} catch (NumberFormatException exception) {
			reset();
		}
	}

	public void populateProducts() {
		if (this.products.getItemCount() > 0)
			this.products.removeAllItems();
		for (StockItem product : domainController.getStockTableModel()
				.getTableRows()) {
			this.products.addItem(product.getName());
		}
	}

	/**
	 * Add new item to the cart. When item out of stock then error message
	 * displayed
	 */
	private void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = new StockItem();
		int quantity;
		try {
			stockItem = this.getItemByBarcode();
			quantity = Integer.parseInt(quantityField.getText());
			this.domainController.getModel().sellItem(
					new SoldItem(stockItem, quantity));
		} catch (NumberFormatException ex) {
			quantity = 1;
		} catch (OutOfStockException e) {
			log.info("Product out of Stock");
			JOptionPane.showMessageDialog(null, "Product out of stock",
					"Error", JOptionPane.ERROR_MESSAGE);
		} catch (NullPointerException e) {
			log.info("No product selected");
		} catch (NoSuchElementException e) {
			log.info("There is no element like: " + stockItem.getId());
		}
	}

	private void itemSelectHandler(ActionEvent e) {
		try {
			StockItem item = getSelectedItem(e);
			this.barCodeField.setText(item.getId().toString());
			this.nameField.setText(item.getName().toString());
			this.priceField.setText(Double.toString(item.getPrice()));
		} catch (NoSuchElementException e1) {
			// start DB sync / update??
			// log.error(e1);
		}
	}

	private StockItem getSelectedItem(ActionEvent e)
			throws NoSuchElementException {
		return this.domainController.getStockTableModel().getItemByName(
				(String) ((JComboBox<String>) e.getSource()).getSelectedItem());
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.products.setEnabled(enabled);
		this.addItemButton.setEnabled(enabled);
		this.barCodeField.setEnabled(enabled);
		this.quantityField.setEnabled(enabled);
	}

	/**
	 * Reset dialog fields.
	 */
	public void reset() {
		this.populateProducts();
		barCodeField.setText("");
		quantityField.setText("1");
		nameField.setText("");
		priceField.setText("");
	}

	// Formatting constraints for the dialogPane
	private GridBagConstraints getDialogPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 0d;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.NONE;

		return gc;
	}

	// Formatting constraints for the basketPane
	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

}
