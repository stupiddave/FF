package com.dave.fantasyfootball.domain;

import java.io.Serializable;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.stereotype.Component;

@Component
public class Player implements Serializable {

	private static final long serialVersionUID = -5159589445791247199L;

	private int id;
	private String firstName;
	private String lastName;
	private String webName;
	private String club;
	private String position;
	private int gameweekPoints;
	private JSONArray gameweekEvent;
	private String imageFileId;
	private int minutesPlayed;
	private String nextOpposition;

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
		return imageFileId;
	}

	public void setImageFile(String imageFile) {
		this.imageFileId = imageFile;
	}

	public int getMinutesPlayed() {
		return minutesPlayed;
	}

	public void setMinutesPlayed() throws JSONException {
		for (int i = 0; i < gameweekEvent.length(); i++) {
			
			if ("Minutes played".equals(gameweekEvent.getJSONArray(i).getString(0))) {
				this.minutesPlayed = gameweekEvent.getJSONArray(i).getInt(1);
			}
		}
	}

	public String getNextOpposition() {
		return nextOpposition;
	}

	public void setNextOpposition(String nextOpposition) {
		this.nextOpposition = nextOpposition;
	}
}
