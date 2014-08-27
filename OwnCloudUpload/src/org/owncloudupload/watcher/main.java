package org.owncloudupload.watcher;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class main {

	public static void main(String[] args) throws IOException {
		SettingsManager s = new SettingsManager();
		HashMap<File, ServerConfig> se = s.getSettings();
		Set set = se.keySet();
		Iterator i = set.iterator();
		while(i.hasNext()){
			File n = (File) i.next();
			new DirectoryMonitor(n.toPath(), true).run();;
		}

	}

}
