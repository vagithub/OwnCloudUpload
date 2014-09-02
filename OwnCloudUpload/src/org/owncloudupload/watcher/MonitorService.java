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

	private static HashMap<File, DirectoryMonitor> monitors = new HashMap<File, DirectoryMonitor>();

	/*
	 * public static void addMonitor(DirectoryMonitor monitor) {
	 * monitors.add(monitor); monitors.lastElement().run(); }
	 */
	public static void removeMonitor() {

	}

	public static void settingsUpdated() {

		File tmp;
		Settings sett = SettingsManager.getSettings();
		Set<File> keys = monitors.keySet();
		Iterator<File> iter = keys.iterator();

		if (monitors.size() != 0) {
			while (iter.hasNext()) {
				tmp = (File) iter.next();
				if (!sett.getConfiguration().containsKey(tmp)) {
					monitors.get(tmp).setStop();
					monitors.remove(tmp);
				} else if (!monitors.get(tmp).getConfig()
						.isConfigurationSame(sett.getConfiguration().get(tmp))) {
					monitors.get(tmp).setConfig(sett.getConfiguration().get(tmp));		
					
				}
			}

			keys = sett.getConfiguration().keySet();
			iter = keys.iterator();
			while (iter.hasNext()) {
				tmp = (File) iter.next();
				if (!monitors.containsKey(tmp)) {
					try {
						monitors.put(tmp, new DirectoryMonitor(tmp, true, sett
								.getConfiguration().get(tmp)));
						Thread thread = new Thread(monitors.get(tmp));
						   thread.start();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else {
			keys = sett.getConfiguration().keySet();
			iter = keys.iterator();
			while (iter.hasNext()) {
				tmp = (File) iter.next();
				try {
					monitors.put(tmp, new DirectoryMonitor(tmp, true, sett
							.getConfiguration().get(tmp)));
					Thread thread = new Thread(monitors.get(tmp));
					   thread.start();
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}
}
