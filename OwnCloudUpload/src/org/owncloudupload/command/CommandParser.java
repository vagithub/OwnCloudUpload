package org.owncloudupload.command;

import java.util.Scanner;

import org.owncloudupload.settings.SettingsGUI;
import org.owncloudupload.settings.SettingsManager;

public class CommandParser implements Runnable {

	private SettingsManager settingsManager;

	@Override
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		String choice;

		while (true) {
			System.out.print("Available options: config, configc and exit:\n$>");
			choice = keyboard.nextLine();
			switch (choice) {
			case "config": {
				new SettingsGUI(settingsManager).show();
				break;
			}
			case "configc": {
				settingsManager.editSettings();
				break;
			}
			case "exit": {
				settingsManager.serializeSettings();
				System.exit(0);
			}
			}

		}

	}
	
	public CommandParser(SettingsManager settingsManager){
		this.settingsManager = settingsManager;
	}
}
