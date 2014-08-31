package org.owncloudupload.settings;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Settings implements Serializable {

	private HashMap<File, ServerConfig> settings;

	public HashMap<File, ServerConfig> getConfiguration() {
		return settings;
	}

	public void addEntry(File file, ServerConfig conf){
		this.settings.put(file, conf);
	}
	
	public void removeEntry(File file){
		this.settings.remove(file);
	}
	
	public Settings(HashMap<File, ServerConfig> settings) {
		super();
		this.settings = settings;
	}

	public Settings() {
		super();
		settings = new HashMap<File, ServerConfig>();
	}
	
}
