package org.owncloudupload.settings;

import java.io.Serializable;

public class ServerConfig implements Serializable{

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

	public boolean isConfigurationSame(ServerConfig other) {
		if (this.timeBeforeSynch != other.getTimeBeforeSynch()
				|| !this.serverURL.equals(other.getServerURL())
				|| !this.user.equals(other.getPassword())
				|| !this.password.equals(other.getPassword()))
			return true;
		else return false;
	}

	@Override
	public String toString() {
		return "ServerConfig [timeBeforeSynch=" + timeBeforeSynch
				+ ", serverURL=" + serverURL + ", user=" + user + ", password="
				+ password + "]";
	}

}
