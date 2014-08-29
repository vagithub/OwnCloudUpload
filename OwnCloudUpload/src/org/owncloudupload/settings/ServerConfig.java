package org.owncloudupload.settings;

class ServerConfig {

	private Long timeBeforeSynch;
	private String serverURL;
	private String user;	
	private String password;
	
	public Long getTimeBeforeSynch() {
		return timeBeforeSynch;
	}
	
	public String getServerURL() {
		return serverURL;
	}
	
	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}
	
	public ServerConfig(Long timeBeforeSynch, String serverURL, String user,
			String password) {
		super();
		this.timeBeforeSynch = timeBeforeSynch;
		this.serverURL = serverURL;
		this.user = user;
		this.password = password;
	}

	@Override
	public String toString() {
		return "ServerConfig [timeBeforeSynch=" + timeBeforeSynch
				+ ", serverURL=" + serverURL + ", user=" + user + ", password="
				+ password + "]";
	}
		
}
