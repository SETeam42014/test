package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.model.StockTableModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.JTableHeader;

import org.apache.log4j.Logger;

public class StockTab {

	private JButton addItem;
	private static final Logger log = Logger.getLogger(StockTab.class);
	private SalesSystemModel model;

	/**
	 * Default constructor for StockTab
	 * 
	 * @param model
	 *            SalesSystemModel
	 */
	public StockTab(SalesSystemModel model) {
		this.model = model;
	}

	/**
	 * warehouse stock tab - consists of a menu and a table
	 * 
	 * @return Panel
	 */
	public Component draw() {
		JPanel panel = new JPanel();

		this.model.updateStock();

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		GridBagLayout gb = new GridBagLayout();
		GridBagConstraints gc = new GridBagConstraints();
		panel.setLayout(gb);

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		panel.add(drawStockMenuPane(), gc);

		gc.weighty = 1.0;
		gc.fill = GridBagConstraints.BOTH;
		panel.add(drawStockMainPane(), gc);
		return panel;
	}

	/**
	 * Warehouse menu
	 * 
	 * @return Panel
	 */
	private Component drawStockMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		addItem = createAddButton();
		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;

		addItem.setEnabled(true);
		panel.add(addItem, gc);

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	/**
	 * Creates the "Add" button
	 * 
	 * @return "Add" button
	 */
	private JButton createAddButton() {
		JButton b = new JButton("Add");
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addButtonClicked();
			}
		});
		b.setEnabled(false);

		return b;
	}

	/**
	 * Add button Clicked action
	 */
	private void addButtonClicked() {
		createInputWindow();
	}

	private void createInputWindow() {
		double sum = 0;
		for (SoldItem i : model.getCurrentPurchaseTableModel().getTableRows()) {
			sum += i.getSum();
		}
		;
		JTextField idField = new JTextField(3);
		JTextField priceField = new JTextField(5);
		JTextField nameField = new JTextField(15);
		JTextField descrField = new JTextField(15);
		JTextField quantityField = new JTextField(5);
		// JTextField descrField = new JTextField();
		nameField.setText(Double.toString(sum));
		JPanel myPanel = new JPanel();
		myPanel.add(new JLabel("Id"));
		myPanel.add(idField);
		myPanel.add(new JLabel("Name"));
		myPanel.add(nameField);
		myPanel.add(new JLabel("Price"));
		myPanel.add(priceField);
		myPanel.add(new JLabel("Description"));
		myPanel.add(descrField);
		myPanel.add(new JLabel("Quantity"));
		myPanel.add(quantityField);
		myPanel.add(Box.createHorizontalStrut(15)); // a spacer

		int result = JOptionPane
				.showConfirmDialog(null, myPanel,
						"Please Enter Product Parameters",
						JOptionPane.OK_CANCEL_OPTION);
		itemAdd(idField, priceField, nameField, descrField, quantityField);
	}

	private void itemAdd(JTextField idField, JTextField priceField,
			JTextField nameField, JTextField descrField,
			JTextField quantityField) {
		String name = nameField.getText();
		try {
			long id = Long.parseLong(idField.getText());
			double price = Double.parseDouble(priceField.getText());
			String description = descrField.getText();
			int quantity = Integer.parseInt(quantityField.getText());
			StockItem stockItem = new StockItem(id, name, description, price,
					quantity);
			model.getWarehouseTableModel().addItem(
					new StockItem(id, name, description, price, quantity));
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null,
					"Id, Price and quantity should be entered as numbers.");
			log.error("Adding StockItem failed, price or quantity not a number.");
		}
	}

	/**
	 * table of the wareshouse stock
	 * 
	 * @return Panel
	 */
	private Component drawStockMainPane() {
		JPanel panel = new JPanel();

		JTable table = new JTable(model.getWarehouseTableModel());

		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("Warehouse status"));
		return panel;
	}

}
