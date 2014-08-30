package org.owncloudupload.settings;

/*
 * TableDemo.java requires no other files.
 */

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Array;
import java.sql.Savepoint;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * TableDemo is just like SimpleTableDemo, except that it uses a custom
 * TableModel.
 */
public class SettingsGUI extends JPanel {

	private Object[][] data;
	private String[] columnNames = { "Folder path", "ownCloud URL for upload",
			"Minutes before synch (0 for immediate)", "User", "Password" };

	public SettingsGUI() {
		super(new GridLayout(2, 0));	
		populateData();
		CustomDefaultTableModel model = new CustomDefaultTableModel(data, columnNames);
		
		final JTable table = new JTable(model);
		
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setFillsViewportHeight(true);

		// Create the scroll pane and add the table to it.
		JScrollPane scrollPane = new JScrollPane(table);
		JPanel buttons = new JPanel();

		// Add the scroll pane to this panel.
		add(scrollPane);
		// ***********************

		JButton addButton = new JButton("Add");

		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.addRow(new Object[] {});
			}
		});

		JButton removeButton = new JButton("Remove");

		removeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				model.removeRow(table.getSelectedRow());
			}
		});
		JButton saveButton = new JButton("Save");

		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				saveSettings(table);
			}
		});

		buttons.add(addButton);
		buttons.add(removeButton);
		buttons.add(saveButton);
		add(buttons);
	}

	private void populateData() {

		Settings settings = SettingsManager.getSettings();
		if(settings == null) return;
		Set keys = settings.getConfiguration().keySet();
		Iterator iter = keys.iterator();
		Object key;
		this.data = new Object[keys.size()][5];
		int i = 0;
		while (iter.hasNext()) {
			key = iter.next();
			this.data[i][0] = key;
			this.data[i][1] = settings.getConfiguration().get(key)
					.getServerURL();
			this.data[i][2] = settings.getConfiguration().get(key)
					.getTimeBeforeSynch();
			this.data[i][3] = settings.getConfiguration().get(key).getUser();
			this.data[i][4] = settings.getConfiguration().get(key)
					.getPassword();
		}
	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be
	 * invoked from the event-dispatching thread.
	 */
	private static void createAndShowGUI(boolean error, String msg) {
		// Create and set up the window.
		JFrame frame = new JFrame("Settings editor");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		if (error) {
			JOptionPane.showMessageDialog(frame, msg);

		}
		// Create and set up the content pane.
		SettingsGUI newContentPane = new SettingsGUI();
		if(!error){
			newContentPane.populateData();
		}
		newContentPane.setOpaque(true); // content panes must be opaque
		frame.setContentPane(newContentPane);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	
	public void show(final boolean error, final String msg) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				createAndShowGUI(error, msg);
			}
		});
	}

	private void saveSettings(JTable table) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Vector tableData = model.getDataVector();
		Settings settings = new Settings();
		Vector tmp;

		for (Object o : tableData) {
		tmp = (Vector) o;
			settings.addEntry(
					 (File) tmp.get(0),
					new ServerConfig((Long) tmp.get(2), (String) tmp
							.get(1), (String) tmp.get(3),
							(String) tmp.get(4)));
		}
		SettingsManager.setSettings(settings);
	}

}

class CustomDefaultTableModel extends DefaultTableModel{

	public CustomDefaultTableModel(Object[][] data, String[] columnNames) {
		super(data,columnNames);
		this.setDataVector(data, columnNames);
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if(columnIndex==2)
			return Long.class;
		if(columnIndex==0)
			return File.class;
		return super.getColumnClass(columnIndex);
	}
	
}

