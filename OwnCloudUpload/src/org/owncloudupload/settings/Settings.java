package org.owncloudupload.settings;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Settings implements Serializable {

	private HashMap<File, ServerConfig> settings;

	public HashMap<File, ServerConfig> getConfiguration() {
		return settings;
	}

	public Settings(HashMap<File, ServerConfig> settings) {
		super();
		this.settings = settings;
	}

}
