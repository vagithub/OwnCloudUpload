package org.owncloudupload.watcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.owncloudupload.command.CommandParser;
//import org.owncloudupload.settings.ServerConfig;
import org.owncloudupload.settings.Settings;
import org.owncloudupload.settings.SettingsManager;

public class Main {

	public static void main(String[] args) throws IOException {
		SettingsManager.initSettings();
		CommandParser cmd = new CommandParser();
		Thread thread = new Thread(cmd);
		   thread.start();
	
	}

}
