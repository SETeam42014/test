package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;

import ee.ut.math.tvt.salessystem.domain.data.HistoryItem;
import ee.ut.math.tvt.salessystem.domain.data.SoldItem;
import ee.ut.math.tvt.salessystem.domain.data.StockItem;
import ee.ut.math.tvt.salessystem.ui.model.PurchaseInfoTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;
import ee.ut.math.tvt.salessystem.ui.panels.PurchaseItemPanel;

/**
 * Encapsulates everything that has to do with the purchase tab (the tab
 * labelled "History" in the menu).
 */
public class HistoryTab {

	// TODO - implement!
	private SalesSystemModel model;

	public HistoryTab(SalesSystemModel model) {
		this.model = model;
	}

	public Component draw() {
		JPanel panel = new JPanel();

		// Layout
		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.setLayout(new GridBagLayout());

		// Add history menu
		panel.add(drawHistoryMenuPane(), getConstraintsForHistoryMenu());

		// Add the main panel
		panel.add(drawHistoryMainPane(), getConstraintsForHistoryMainPane());
		return panel;
	}

	private GridBagConstraints getConstraintsForHistoryMenu() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 0d;

		return gc;
	}

	private GridBagConstraints getConstraintsForHistoryMainPane() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.NORTH;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.weightx = 1.0d;
		gc.weighty = 1.0;

		return gc;
	}

	private Component drawHistoryMenuPane() {
		JPanel panel = new JPanel();

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();

		panel.setLayout(gb);

		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.weightx = 0;

		gc.gridwidth = GridBagConstraints.RELATIVE;
		gc.weightx = 1.0;

		panel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		return panel;
	}

	private Component drawHistoryMainPane() {
		JPanel panel = new JPanel();

		panel.setLayout(new GridBagLayout());

		// Create table
		JTable table = new JTable(model.getHistoryTableModel());
		JTableHeader header = table.getTableHeader();
		header.setReorderingAllowed(false);

		// historyItemClicked(table);

		JScrollPane scrollPane = new JScrollPane(table);

		GridBagConstraints gc = new GridBagConstraints();
		GridBagLayout gb = new GridBagLayout();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		panel.setLayout(gb);
		panel.add(scrollPane, gc);

		panel.setBorder(BorderFactory.createTitledBorder("History"));

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				historyTabelMouseListener(e);
			}
		});
		return panel;
	}

	private void historyTabelMouseListener(MouseEvent e) {
		if (e.getClickCount() == 1) {
			// get item clicked
			HistoryItem item = model.getHistoryTableModel().getItemById(
					(long) ((JTable) e.getSource()).getSelectedRow());
			// Create new panel for purchase history view
			JPanel paymentPanel = new JPanel();
			paymentPanel.add(drawBasketPane(item), getBasketPaneConstraints());

			JOptionPane.showConfirmDialog(null, paymentPanel, "Order details",
					JOptionPane.OK_CANCEL_OPTION);
		}
	}

	private JComponent drawBasketPane(HistoryItem item) {

		// Create the basketPane
		JPanel basketPane = new JPanel();
		basketPane.setPreferredSize(new Dimension(400, 150));
		basketPane.setLayout(new GridBagLayout());
		basketPane.setBorder(BorderFactory.createTitledBorder("Order details"));

		// Create the table, put it inside a scollPane,
		// and add the scrollPane to the basketPanel.
		item.getItems();
		PurchaseInfoTableModel soldGoods = new PurchaseInfoTableModel();
		for (SoldItem i : item.getItems()) {
			soldGoods.addItem(i);
		}
		JTable table = new JTable(soldGoods);
		JScrollPane scrollPane = new JScrollPane(table);

		basketPane.add(scrollPane, getBacketScrollPaneConstraints());

		return basketPane;
	}

	private GridBagConstraints getBacketScrollPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0;
		gc.weighty = 1.0;

		return gc;
	}

	private GridBagConstraints getBasketPaneConstraints() {
		GridBagConstraints gc = new GridBagConstraints();

		gc.anchor = GridBagConstraints.WEST;
		gc.weightx = 0.2;
		gc.weighty = 1.0;
		gc.gridwidth = GridBagConstraints.REMAINDER;
		gc.fill = GridBagConstraints.BOTH;

		return gc;
	}

}