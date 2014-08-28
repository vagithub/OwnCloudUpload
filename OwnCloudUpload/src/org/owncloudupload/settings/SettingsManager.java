package org.owncloudupload.settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SettingsManager {

	private static Settings settings;
	private static final String SETTINGS_FILE = "config.ser";

	public static void initSettings() {

		FileInputStream settingsFileIn;
		ObjectInputStream in;
		try {
			settingsFileIn = new FileInputStream(SETTINGS_FILE);
			in = new ObjectInputStream(settingsFileIn);
			settings = (Settings) in.readObject();
		} catch (FileNotFoundException e) {
			new SettingsGUI().start(true, "No settings file found");
		} catch (IOException e) {			
			new SettingsGUI().start(true,
					"Error reading the settings file.Creating a new one");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void serializeSettings() {
		try {
			FileOutputStream fileOut = new FileOutputStream(SETTINGS_FILE);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(settings);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public static Settings getSettings() {
		return settings;
	}

	public static void setSettings(Settings sett) {
		settings = sett;
	}

	private SettingsManager() {

	}
}
