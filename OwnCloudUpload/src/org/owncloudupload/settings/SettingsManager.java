package org.owncloudupload.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import org.apache.commons.validator.routines.UrlValidator;
import org.owncloudupload.watcher.MonitorService;

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
			MonitorService.settingsUpdated();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			new SettingsGUI().start(true, "No settings file found");
		} catch (IOException e) {
			e.printStackTrace();
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

	public static void setSettings(Settings sett) {System.out.println("Im here 1");
		settings = sett;
		MonitorService.settingsUpdated();
	}

	public static void editSettings() {
		int choice = -1;
		Scanner scanner = new Scanner(System.in);
		HashMap<Integer, File> quickAccess = new HashMap();
		
		
		while (choice != 4) {
			System.out.println("-----------------");	
			quickAccess = printSettings();
			System.out.println("-----------------");	
			System.out.println("Choose a task:\n"
					+ "1.Add new entries\n" + "2.Edit an entry\n"
					+ "3.Remove an entry\n" + "4.Exit the config utility\n");
			choice = scanner.nextInt();
			switch (choice) {
			
			case 1: {
				readEntryFromKeyboard();
				break;
			}
			case 2: {
				// todo
			}
			case 3: {
				// todo
			}
			}

		}
	}

	private static HashMap<Integer, File> printSettings() {
		System.out.println("These are the current settings:\n");
		Set keys = settings.getConfiguration().keySet();
		Iterator iter = keys.iterator();
		StringBuffer buff = new StringBuffer();
		File key;
		int pos = 0;
		HashMap<Integer, File> quickAccess = new HashMap();

		while (iter.hasNext()) {
			key = (File) iter.next();
			buff.append(pos + "." + key.getAbsolutePath() + " : "
					+ settings.getConfiguration().get(key).toString());
			quickAccess.put(new Integer(pos), key);
			pos++;
		}

		System.out.println(buff.toString());
		return quickAccess;
	}
	
	
	private static void readEntryFromKeyboard() {

		long tmpLong;
		Scanner keyboard = new Scanner(System.in);
		String tmpStr;
		StringBuffer entry = new StringBuffer();

		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes,
				UrlValidator.ALLOW_LOCAL_URLS);

		System.out.print("Enter new directory for monitoring:");
		tmpStr = keyboard.nextLine();
		while (!new File(tmpStr).isDirectory()) {
			System.out.print("You have invalid path."
					+ "Please enter valid path to directory:");
			tmpStr = keyboard.nextLine();
		}
		entry.append(tmpStr + ",");

		System.out.print("Enter time before synchronization."
				+ "Value 0 indicates immediate synchronization:");
		tmpLong = keyboard.nextLong();
		while (tmpLong < 0) {
			System.out.print("You have entered negative value."
					+ "Please enter positive value or 0:");
			tmpLong = keyboard.nextLong();
		}
		entry.append(tmpLong + ",");

		System.out.print("Enter the ownClowd URL for synchronizing:");
		tmpStr = keyboard.nextLine();
		while (!urlValidator.isValid(tmpStr)) {
			tmpStr = keyboard.nextLine();
			System.out.print("You have entered invalid URL."
					+ "Please enter valid URL:");
		}

		System.out.print("Enter the username:");
		tmpStr = keyboard.nextLine();
		entry.append(tmpStr + ",");

		System.out.print("Enter the password:");
		tmpStr = keyboard.nextLine();
		entry.append(tmpStr + "\n");

		String[] values = entry.toString().split(",");
		ServerConfig conf = new ServerConfig(
				new Long(Long.parseLong(values[1])), values[2], values[3],
				values[4]);
		settings.addEntry(new File(values[0]), conf);
		keyboard.close();

	}

	private SettingsManager() {

	}
}
