package org.owncloudupload.watcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.AllPermission;
import java.util.HashMap;
import java.util.Scanner;

import org.apache.commons.validator.routines.UrlValidator;

public class SettingsManager {

	private HashMap<File, ServerConfig> dirsToMonitor;
	private final String settingsFile = "config";

	public void initSettings() {
		dirsToMonitor = new HashMap<File, ServerConfig>();
		Scanner fileScanner;
		try {
			File f = new File(settingsFile);
			fileScanner = new Scanner(f);

			String[] splittedLine;
			while (fileScanner.hasNext()) {
				splittedLine = fileScanner.nextLine().split(",");
				dirsToMonitor.put(new File(splittedLine[0]), new ServerConfig(
						splittedLine[1], splittedLine[2]));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out
					.println("Settings file not found.Please enter your settings in the following format\n:"
							+ " <directory to be monitored>,<time to pass before synchronization>, <ownCloud url>\n"
							+ "This settings will create new settings file.");
			readSettingsFileFromKeyboard();
			initSettings();
		}
	}

	public HashMap<File, ServerConfig> getSettings() {
		return dirsToMonitor;
	}

	private void readSettingsFileFromKeyboard() {
		String newEntryQuestion = "y";
		long tmpLong;
		Scanner keyboard = new Scanner(System.in);
		String tmpStr;		
		StringBuffer settings = new StringBuffer();
		
		String[] schemes = {"http","https"};
	    UrlValidator urlValidator = new UrlValidator(schemes,UrlValidator.ALLOW_LOCAL_URLS);
	   
		while (newEntryQuestion.equals("y")) {
			System.out.print("Enter new directory for monitoring:");
			tmpStr = keyboard.nextLine();
			while (!new File(tmpStr).isDirectory()) {
				System.out.print("You have invalid path."
						+ "Please enter valid path to directory:");
				tmpStr = keyboard.nextLine();
			}
			settings.append(tmpStr + ",");
			
			System.out.print("Enter time before synchronization."
					+ "Value 0 indicates immediate synchronization:");
			tmpLong = keyboard.nextLong();
			while (tmpLong < 0) {
				System.out.print("You have entered negative value."
						+ "Please enter positive value or 0:");
				tmpLong = keyboard.nextLong();
			}
			settings.append(tmpLong + ",");
			
			System.out.print("Enter the ownClowd URL for synchronizing:");
			tmpStr = keyboard.nextLine();
			while (!urlValidator.isValid(tmpStr)) {
				tmpStr = keyboard.nextLine();
				System.out.print("You have entered invalid URL."
						+ "Please enter valid URL:");
			}
			settings.append(tmpStr + "\n");
			System.out.println("Another entry? [y/n]");
			newEntryQuestion = keyboard.next();
		}

		File settingsFile = new File("config");
		try {
			RandomAccessFile writer = new RandomAccessFile(settingsFile, "rw");
			writer.write(settings.toString().getBytes());
			keyboard.close();
			writer.close();
		} catch (FileNotFoundException e) {
			System.out.println("Settings file couldn't be created.Please try again.");
			keyboard.close();
			readSettingsFileFromKeyboard();
		} catch (IOException e) {
			System.out.println("Error in writing the file."
					+ "Make sure you have the right permissions for running the program");
			keyboard.close();
			readSettingsFileFromKeyboard();
		}

	}

	public SettingsManager() {
		initSettings();
	}
}
