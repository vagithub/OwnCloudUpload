package org.owncloudupload.watcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import org.owncloudupload.settings.Settings;
import org.owncloudupload.settings.SettingsManager;

public class MonitorService {

	private static HashMap<File, DirectoryMonitor> monitors;

	/*
	 * public static void addMonitor(DirectoryMonitor monitor) {
	 * monitors.add(monitor); monitors.lastElement().run(); }
	 */
	public static void removeMonitor() {

	}

	public static void settingsUpdated() {

		File tmp;
		Settings sett = SettingsManager.getSettings();
		Set keys = monitors.keySet();
		Iterator iter = keys.iterator();

		while (iter.hasNext()) {
			tmp = (File) iter.next();
			if (!sett.getConfiguration().containsKey(tmp)) {
				monitors.get(tmp).setStop(true);
				monitors.remove(tmp);
			} else if (!monitors.get(tmp).getConfig()
					.isConfigurationSame(sett.getConfiguration().get(tmp))) {
				monitors.get(tmp).setStop(true);
				monitors.remove(tmp);
				// TODO
			}
		}
		
		keys = sett.getConfiguration().keySet();
		iter = keys.iterator();
		while(iter.hasNext()){
			tmp = (File) iter.next();
			if(!monitors.containsKey(tmp)){
				//TODO
			}
		}
	}
}
