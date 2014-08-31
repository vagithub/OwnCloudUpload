package org.owncloudupload.command;

import java.util.Scanner;

import org.owncloudupload.settings.SettingsGUI;
import org.owncloudupload.settings.SettingsManager;

public class CommandParser implements Runnable {

	@Override
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		String choice;

		while (true) {
			System.out.print("Available options: config, configc and exit:\n$>");
			choice = keyboard.nextLine();
			switch (choice) {
			case "config": {
				new SettingsGUI().show(false, null);
				break;
			}
			case "configc": {
				SettingsManager.editSettings();
				break;
			}
			case "exit": {
				SettingsManager.serializeSettings();
				System.exit(0);
			}
			}

		}

	}
}
