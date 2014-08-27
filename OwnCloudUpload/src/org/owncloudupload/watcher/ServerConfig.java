package org.owncloudupload.watcher;

class ServerConfig {

	private Long timeBeforeSynch;
	private String serverURL;
	
	public Long getTimeBeforeSynch() {
		return timeBeforeSynch;
	}
	public void setTimeBeforeSynch(Long timeBeforeSynch) {
		this.timeBeforeSynch = timeBeforeSynch;
	}
	public String getServerURL() {
		return serverURL;
	}
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	public ServerConfig(String timeBeforeSynch, String serverURL) {
		super();
		this.timeBeforeSynch = Long.parseLong(timeBeforeSynch);
		this.serverURL = serverURL;
	}	
		
}
