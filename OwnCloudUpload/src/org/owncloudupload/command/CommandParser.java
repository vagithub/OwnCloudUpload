package org.owncloudupload.command;

import java.util.Scanner;

import org.owncloudupload.settings.SettingsGUI;
import org.owncloudupload.settings.SettingsManager;

public class CommandParser implements Runnable{

	@Override
	public void run() {
		Scanner keyboard = new Scanner(System.in);
		String choice;
		
		while(true){
		System.out.print("Available options: config and exit:\n$>");
		choice = keyboard.nextLine();
		if(choice.equals("config")){
			new SettingsGUI().show(false,null);
		}else if (choice.equals("exit"))
		{
			SettingsManager.serializeSettings();
			System.exit(0);
		}
		}
		
	}

}
