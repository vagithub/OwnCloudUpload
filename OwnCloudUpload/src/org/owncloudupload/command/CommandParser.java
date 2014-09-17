package org.owncloudupload.command;

import java.lang.reflect.InvocationTargetException;
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
				/*Thread thr = new Thread(new Runnable() {
					
					@Override
					public void run() {
						new SettingsGUI(settingsManager).createAndShowGUI();
						
					}
				}) ;
				thr.start();try {
					thr.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(!thr.isInterrupted()){System.out.println("$$$$$$");}*/
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
