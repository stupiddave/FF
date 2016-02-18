package com.hfl.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Player {

	private int id;
	@JsonProperty("first_name")
	private String firstName;
	@JsonProperty("second_name")
	private String lastName;
	@JsonProperty("web_name")
	private String webName;
	@JsonProperty("team_name")
	private String club;
	@JsonProperty("type_name")
	private String position;
	@JsonProperty("event_explain")
	private Object[][] gameweekEvents;

	public Player() {
	}

	public Player(int id, String firstName, String lastName, String webName, String club, String position) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.webName = webName;
		this.club = club;
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getWebName() {
		return webName;
	}

	public String getClub() {
		return club;
	}

	public String getPosition() {
		return position;
	}

	public String getWebname() {
		return webName;
	}
}
