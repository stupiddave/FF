package com.dave.fantasyfootball.domain;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

public class EplTeam implements Serializable{

	private static final long serialVersionUID = 8558495648090361335L;

	private String name;
	private URL badgeUrl;

	public EplTeam(String name) {
		this.name = name;
	}

	public URL getBadgeUrl() {
		return badgeUrl;
	}

	public void setBadgeUrl(String badgeUrl) throws MalformedURLException {
		this.badgeUrl = new URL(badgeUrl);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return name;
	}
}
