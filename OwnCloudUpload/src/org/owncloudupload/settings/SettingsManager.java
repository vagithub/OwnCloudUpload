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
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.UrlValidator;
import org.owncloudupload.watcher.MonitorService;

public class SettingsManager {

	private Settings settings;
	private static final String SETTINGS_FILE = "config.ser";
	private HashMap<Integer, File> quickAccess;
	private MonitorService monitorService;

	public void initSettings() {

		FileInputStream settingsFileIn;
		ObjectInputStream in;
		try {
			settingsFileIn = new FileInputStream(SETTINGS_FILE);
			in = new ObjectInputStream(settingsFileIn);
			settings = (Settings) in.readObject();
			monitorService.settingsUpdated(this);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("No settings file found.Please use the config(GUI) or configc(command line) utilities to configure the client");
//			new SettingsGUI().show(true, "No settings file found");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error reading the settings file.Please use the config(GUI) or configc(command line) utilities to configure the client");
//			new SettingsGUI().show(true,
//					"Error reading the settings file.Creating a new one");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void serializeSettings() {
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

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings sett) {
		settings = sett;
		monitorService.settingsUpdated(this);
	}

	public void editSettings() {
		int choice = -1;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		int index;

		while (choice != 4) {
			System.out.println("-----------------");
			quickAccess = printSettings();
			if(quickAccess == null){
				settings = new Settings();
			}
			System.out.println("-----------------");
			System.out.println("Choose a task:\n" + "1.Add new entries\n"
						+ "2.Edit an entry\n" + "3.Remove an entry\n"
						+ "4.Exit the config utility\n");
		 
			choice = scanner.nextInt();
			switch (choice) {

			case 1: {
				readEntryFromKeyboard(false, null);
				break;
			}
			case 2: {
				System.out.println("Enter the index of the entry for editing:");
				index = scanner.nextInt();
				File key = quickAccess.get(new Integer(index));
				readEntryFromKeyboard(true, key);
				break;
			}
			case 3: {
				System.out
						.println("Enter the index of the entry for deletion:");
				index = scanner.nextInt();
				File file = quickAccess.get(new Integer(index));
				settings.removeEntry(file);
				break;
			}
			}

		}
		monitorService.settingsUpdated(this);
	}

	private HashMap<Integer, File> printSettings() {

		
		if (settings != null) {
			System.out.println("These are the current settings:\n");
			Set keys = settings.getConfiguration().keySet();
			Iterator iter = keys.iterator();
			StringBuffer buff = new StringBuffer();
			File key;
			int pos = 0;
			HashMap<Integer, File> map = new HashMap();

			while (iter.hasNext()) {
				key = (File) iter.next();
				buff.append(pos + "." + key.getAbsolutePath() + " : "
						+ settings.getConfiguration().get(key).toString() + "\n");
				map.put(new Integer(pos), key);
				pos++;
			}

			System.out.println(buff.toString());
			return map;
		} else {
			System.out.println("These are no current settings:\n");
			return null;
		}
	}

	private void populateQuickAccess() {
		Set keys = settings.getConfiguration().keySet();
		Iterator iter = keys.iterator();
		StringBuffer buff = new StringBuffer();
		File key;
		int pos = 0;
		HashMap<Integer, File> map = new HashMap();

		while (iter.hasNext()) {
			key = (File) iter.next();
			buff.append(pos + "." + key.getAbsolutePath() + " : "
					+ settings.getConfiguration().get(key).toString());
			map.put(new Integer(pos), key);
			pos++;
		}
		quickAccess = map;
	}

	private  void readEntryFromKeyboard(boolean edit, File key) {

		long tmpLong;
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
		String tmpStr;
		StringBuffer entry = new StringBuffer();

		String[] schemes = { "http", "https" };
		UrlValidator urlValidator = new UrlValidator(schemes,
				UrlValidator.ALLOW_LOCAL_URLS);

		if (!edit) {
			System.out.print("Enter new directory for monitoring:");
			tmpStr = keyboard.nextLine();
			while (!new File(tmpStr).isDirectory()) {
				System.out.print("You have entered invalid path."
						+ "Please enter valid path to directory:");
				tmpStr = keyboard.nextLine();
			}
			entry.append(tmpStr + ",");
		}

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
			System.out.print("You have entered invalid URL."
					+ "Please enter valid URL:");
			tmpStr = keyboard.nextLine();
		}
		entry.append(tmpStr + ",");

		System.out.print("Enter the username:");
		tmpStr = keyboard.nextLine();
		entry.append(tmpStr + ",");

		System.out.print("Enter the password:");
		tmpStr = keyboard.nextLine();
		entry.append(tmpStr + "\n");

		String[] values = entry.toString().split(",");
		
		if (!edit) {
			ServerConfig conf = new ServerConfig(
					new Long(Long.parseLong(values[1])), values[2], values[3],
					values[4]);
			settings.addEntry(new File(values[0]), conf);
		} else {
			ServerConfig conf = new ServerConfig(
					new Long(Long.parseLong(values[0])), values[1], values[2],
					values[3]);
			settings.addEntry(key, conf);
		}
//		keyboard.close();

	}

	public SettingsManager() {
		monitorService = new MonitorService();
	}
}
