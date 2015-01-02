package com.dave.fantasyfootball.domain;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

@Component
public class Player {

	private int id;
	private String firstName;
	private String lastName;
	private String webName;
	private String club;
	private String position;
	private int gameweekPoints;
	private JSONArray gameweekEvent;
	private String imageFile;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWebName() {
		return webName;
	}

	public void setWebName(String webName) {
		this.webName = webName;
	}

	public String getClub() {
		return club;
	}

	public void setClub(String club) {
		this.club = club;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	@Override
	public String toString() {
		return "Player [id=" + id + ", webName=" + webName + ", club=" + club
				+ ", position=" + position.toString() + "]";
	}

	public int getGameweekPoints() {
		return gameweekPoints;
	}

	public void setGameweekPoints(int gameweekPoints) {
		this.gameweekPoints = gameweekPoints;
	}

	public JSONArray getGameweekEvent() {
		return gameweekEvent;
	}

	public void setGameweekEvent(JSONArray gameweekEvent) {
		this.gameweekEvent = gameweekEvent;
	}

	public String getImageFile() {
		return imageFile;
	}

	public void setImageFile(String imageFile) {
		this.imageFile = imageFile;
	}

//	public int getTeamId() {
//		return teamId;
//	}
//
//	public void setTeamId(int teamId) {
//		this.teamId = teamId;
//	}
}
