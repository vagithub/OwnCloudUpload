package org.owncloudupload.settings;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component.Alignment;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.AbstractContainer;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Panel.Orientation;
import com.googlecode.lanterna.gui.component.PasswordBox;
import com.googlecode.lanterna.gui.component.Separator;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.component.TextGrid;
import com.googlecode.lanterna.gui.layout.BorderLayout;
import com.googlecode.lanterna.gui.layout.HorisontalLayout;
import com.googlecode.lanterna.gui.layout.LayoutParameter;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.gui.Component;
import com.googlecode.lanterna.input.Key.Kind;

public class SettingsGUI {
	//Table header used for future addition of properties.Last must always be the password
	private static final String[] TABLE_HEADER = { "Folder path",
			"ownCloud URL for upload", "Time before synch (0 for immediate)",
			"User", "Password" };


	public void start(boolean error, String message) {
		final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
		final Window window = new Window("Settings editor");
		window.setWindowSizeOverride(new TerminalSize(150, 50));
		window.setSoloWindow(true);

		final Panel panel = new Panel();
		final Table table = new Table(TABLE_HEADER.length);
		table.setColumnPaddingSize(1);
		
		
		
        panel.setLayoutManager(new VerticalLayout());
        Button button = new Button("Add Entry", new Action() {
		
			@Override
			public void doAction() {
				TextBox blankRow = new TextBox("", 47);
		        panel.addComponent(blankRow, LinearLayout.GROWS_HORIZONTALLY);
		        panel.addComponent(new Separator(), LinearLayout.GROWS_HORIZONTALLY);				
			}
		});
        button.setAlignment(Component.Alignment.RIGHT_CENTER);
        panel.addComponent(button, LinearLayout.GROWS_HORIZONTALLY);

		Component[] tableLabels = new Component[5];
		for(int i=0;i<TABLE_HEADER.length;i++)
		{
			tableLabels[i] = new Label(TABLE_HEADER[i]);
		}
		
		table.addRow(tableLabels);		
		panel.addComponent(table,LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(panel, LinearLayout.GROWS_HORIZONTALLY);
		
		

	        guiScreen.getScreen().startScreen();
	        guiScreen.showWindow(window);
	        guiScreen.getScreen().stopScreen();


		Settings settings = SettingsManager.getSettings();

	}
	
	
	public void starta(boolean error, String message) {
		final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
		final Window window = new Window("Settings editor");
		window.setWindowSizeOverride(new TerminalSize(150, 50));
		window.setSoloWindow(true);

		Panel panel = new Panel();
		final Table table = new Table(TABLE_HEADER.length);
		table.setColumnPaddingSize(1);
		
		
		
        panel.setLayoutManager(new VerticalLayout());
        Button button = new Button("Add Entry", new Action() {
		
			@Override
			public void doAction() {
				Component[] blankRow = new Component[TABLE_HEADER.length];
				for(int i=0;i<TABLE_HEADER.length-1;i++)
				{
					blankRow[i] = new TextBox("",TABLE_HEADER[i].length());
				}
				blankRow[blankRow.length-1] = new PasswordBox("","password".length()); 
				table.addRow(blankRow);			
				
			}
		});
        button.setAlignment(Component.Alignment.RIGHT_CENTER);
        panel.addComponent(button, LinearLayout.GROWS_HORIZONTALLY);

		Component[] tableLabels = new Component[5];
		for(int i=0;i<TABLE_HEADER.length;i++)
		{
			tableLabels[i] = new Label(TABLE_HEADER[i]);
		}
		
		table.addRow(tableLabels);		
		panel.addComponent(table,LinearLayout.GROWS_HORIZONTALLY);
		window.addComponent(panel, LinearLayout.GROWS_HORIZONTALLY);
		
		

	        guiScreen.getScreen().startScreen();
	        guiScreen.showWindow(window);
	        guiScreen.getScreen().stopScreen();


		Settings settings = SettingsManager.getSettings();

	}

/*	public static void main(String[] args) {
		final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
		final Window window = new Window("Sample window");
		window.setWindowSizeOverride(new TerminalSize(130, 50));
		window.setSoloWindow(true);

		// Panel panel = new Panel("Panel with a right-aligned button");

		// panel.setLayoutManager(new VerticalLayout());

		Table table = new Table(6);
		table.setColumnPaddingSize(5);

		Component[] row1 = new Component[6];
		row1[0] = new TextBox("Field-1");
		row1[1] = new TextBox("Field-2");
		row1[2] = new TextBox("Field-3");
		row1[3] = new TextBox("Field-4");
		row1[4] = new TextBox("Field-5");
		row1[5] = new TextBox("Field-6");

		table.addRow(row1);
		// panel.addComponent(table);

		window.addComponent(table);
		window.addComponent(new EmptySpace());

		Button quitButton = new Button("Save and exit", new Action() {

			public void doAction() {

				// TODO Auto-generated method stub
				// window.close();
				window.close();

			}
		});
		quitButton.setAlignment(Component.Alignment.RIGHT_CENTER);
		window.addComponent(quitButton, LinearLayout.GROWS_HORIZONTALLY);

		guiScreen.getScreen().startScreen();
		guiScreen.showWindow(window);
		guiScreen.getScreen().stopScreen();
	}*/
}
