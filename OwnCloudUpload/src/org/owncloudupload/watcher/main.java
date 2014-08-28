package org.owncloudupload.watcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

//import org.owncloudupload.settings.ServerConfig;
import org.owncloudupload.settings.Settings;
import org.owncloudupload.settings.SettingsManager;

public class main {

	public static void main(String[] args) throws IOException {
		SettingsManager.initSettings();
		Settings se = SettingsManager.getSettings();
	/*	Set set = se.getConfiguration().keySet();
		Iterator i = set.iterator();
		while(i.hasNext()){
			File n = (File) i.next();
			new DirectoryMonitor(n.toPath(), true).run();;
		}
*/
	}

}
