package org.owncloudupload.watcher;

import java.util.Vector;

public class MonitorService {

	private Vector<DirectoryMonitor> monitors;

	public Vector<DirectoryMonitor> getMonitors() {
		return monitors;
	}
	
	public void addMonitor(DirectoryMonitor monitor){
		monitors.add(monitor);
		monitors.get(monitors.size()).run();
	}
	
	public void removeMonitor(){
		
	}
}
