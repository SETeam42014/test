package ee.ut.math.tvt.salessystem.ui.panels;

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

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.domain.exception.OutOfStockException;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

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
	private JComboBox products;

	private JButton addItemButton;

	// Warehouse model
	private SalesSystemModel model;

	/**
	 * Constructs new purchase item panel.
	 * 
	 * @param model
	 *            composite model of the warehouse and the shopping cart.
	 */
	public PurchaseItemPanel(SalesSystemModel model) {
		this.model = model;

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
		JTable table = new JTable(model.getCurrentPurchaseTableModel());
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	// purchase dialog
	private JComponent drawDialogPane() {

		// Create the panel
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));
		panel.setBorder(BorderFactory.createTitledBorder("Product"));
		// Initialize the textfields

		// Add products to JComboBox
		/*
		 * int i = model.getWarehouseTableModel().getRowCount(); String[]
		 * warehouseProducts = new String[i--]; for (; i >= 0; i--) {
		 * warehouseProducts[i] = model.getWarehouseTableModel() .getValueAt(i,
		 * 1).toString(); }
		 */

		products = new JComboBox<String>();
		barCodeField = new JTextField();
		quantityField = new JTextField("1");
		nameField = new JTextField();
		priceField = new JTextField();

		products.setEnabled(false);
		nameField.setEditable(false);
		priceField.setEditable(false);

		// == Add components to the panel
		// JComboBox nimed = new JComboBox(nameOptions);
		// - bar code
		// final JPanel comboPanel = new JPanel();
		// JLabel comboLbl = new JLabel("Team members:");
		// JComboBox nimed = new JComboBox(nameOptions);
		// comboPanel.add(comboLbl); comboPanel.add(nimed);
		panel.add(new JLabel("Products:"));
		panel.add(products);

		panel.add(new JLabel("Bar code:"));
		panel.add(barCodeField);

		// - amount
		panel.add(new JLabel("Amount:"));
		panel.add(quantityField);

		// - name
		panel.add(new JLabel("Name:"));
		panel.add(nameField);

		// - price
		panel.add(new JLabel("Price:"));
		panel.add(priceField);

		// - populate products
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
		panel.add(addItemButton);

		return panel;
	}

	// Fill dialog with data from the "database".
	private void fillDialogFields() {
		StockItem stockItem = getStockItemByBarcode();

		if (stockItem != null) {
			nameField.setText(stockItem.getName());
			String priceString = String.valueOf(stockItem.getPrice());
			priceField.setText(priceString);
			products.setSelectedItem(stockItem.getName());

		} else {
			reset();
		}

	}

	public void populateProducts() {
		if (this.products.getItemCount() > 0)
			this.products.removeAllItems();
		// log.debug("Listis on: " + this.products.getItemCount());
		for (StockItem product : model.getWarehouseTableModel().getTableRows()) {
			this.products.addItem(product.getName());
		}
		getStockItemByBarcode();

	}

	// Search the warehouse for a StockItem with the bar code entered
	// to the barCode textfield.
	private StockItem getStockItemByBarcode() {
		try {
			int code = Integer.parseInt(barCodeField.getText());
			return model.getWarehouseTableModel().getItemById(code);
		} catch (NumberFormatException ex) {
			return null;
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	/**
	 * Add new item to the cart. When item out of stock then error message
	 * displayed
	 */
	public void addItemEventHandler() {
		// add chosen item to the shopping cart.
		StockItem stockItem = getStockItemByBarcode();
		int quantity;
		try {
			quantity = Integer.parseInt(quantityField.getText());
			if (stockItem.getQuantity() == 0
					|| quantity > stockItem.getQuantity()) {
				throw new OutOfStockException();
			}
			try {
				if (model.getCurrentPurchaseTableModel()
						.getItemById(stockItem.getId()).getQuantity() >= stockItem
						.getQuantity()) {
					throw new OutOfStockException();
				}
			} catch (NoSuchElementException e) {
				// If the element isn't in the purchase list yet
			}
			// quantity = Integer.parseInt(quantityField.getText());
			// add item to sold item list
			this.model.getCurrentPurchaseTableModel().addItem(
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
			log.info("There is no element like: " + e);
		}

	}

	/**
	 * Handles selected item in JComboBox as StockItem
	 * 
	 * @param e
	 *            JComboBox actionEvent
	 */
	public void itemSelectHandler(ActionEvent e) {
		@SuppressWarnings("unchecked")
		StockItem item = model.getWarehouseTableModel().getItemByName(
				(String) ((JComboBox<String>) e.getSource()).getSelectedItem());
		this.barCodeField.setText(item.getId().toString());
		this.nameField.setText(item.getName().toString());
		this.priceField.setText(Double.toString(item.getPrice()));
	}

	/**
	 * Sets whether or not this component is enabled.
	 */
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
		barCodeField.setText("");
		quantityField.setText("1");
		nameField.setText("");
		priceField.setText("");
		getStockItemByBarcode();
	}

	/*
	 * === Ideally, UI's layout and behavior should be kept as separated as
	 * possible. If you work on the behavior of the application, you don't want
	 * the layout details to get on your way all the time, and vice versa. This
	 * separation leads to cleaner, more readable and better maintainable code.
	 * 
	 * In a Swing application, the layout is also defined as Java code and this
	 * separation is more difficult to make. One thing that can still be done is
	 * moving the layout-defining code out into separate methods, leaving the
	 * more important methods unburdened of the messy layout code. This is done
	 * in the following methods.
	 */

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
