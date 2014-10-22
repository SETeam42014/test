package ee.ut.math.tvt.salessystem.ui.tabs;

import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

public class StockTab {

	private JButton addItem;

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
		// mockup add item
		model.getWarehouseTableModel().addItem(
				new StockItem(1l, "Lays chips", "Potato chips", 11.0, 0));
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
