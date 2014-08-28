package org.owncloudupload.settings;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.gui.Action;
import com.googlecode.lanterna.gui.Component.Alignment;
import com.googlecode.lanterna.gui.GUIScreen;
import com.googlecode.lanterna.gui.Window;
import com.googlecode.lanterna.gui.component.Button;
import com.googlecode.lanterna.gui.component.EmptySpace;
import com.googlecode.lanterna.gui.component.Label;
import com.googlecode.lanterna.gui.component.Panel;
import com.googlecode.lanterna.gui.component.Panel.Orientation;
import com.googlecode.lanterna.gui.component.Table;
import com.googlecode.lanterna.gui.component.TextBox;
import com.googlecode.lanterna.gui.layout.LinearLayout;
import com.googlecode.lanterna.gui.layout.VerticalLayout;
import com.googlecode.lanterna.terminal.TerminalSize;
import com.googlecode.lanterna.gui.Component;

public class SettingsGUI {
	private static final String[] TABLE_HEADER = { "Folder path",
			"ownCloud URL for upload", "Time before synch (0 for immediate)",
			"User", "Password" };

	public void start(boolean error, String message) {
		final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
		final Window window = new Window("Settings editor");
		window.setWindowSizeOverride(new TerminalSize(130, 50));
		window.setSoloWindow(true);

		Table table = new Table(5);
		table.setColumnPaddingSize(1);
		Component[] tableLabels = new Component[5];
		for(int i=0;i<TABLE_HEADER.length;i++)
		{
			tableLabels[i] = new Label(TABLE_HEADER[i]);
		}

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
