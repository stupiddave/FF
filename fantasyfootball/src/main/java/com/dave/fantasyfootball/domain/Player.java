package com.dave.fantasyfootball.domain;

import java.io.Serializable;
import java.util.List;

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
	private List<GameweekEvent> gameweekEvents;
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
		return "Player [id=" + id + ", webName=" + webName + ", club=" + club + ", position=" + position.toString()
				+ "]";
	}

	public int getGameweekPoints() {
		return gameweekPoints;
	}

	public void setGameweekPoints(int gameweekPoints) {
		this.gameweekPoints = gameweekPoints;
	}

	public List<GameweekEvent> getGameweekEvents() {
		return gameweekEvents;
	}

	public void setGameweekEvent(List<GameweekEvent> gameweekEvents) {
		this.gameweekEvents = gameweekEvents;
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
		for (int i = 0; i < gameweekEvents.size(); i++) {

			if ("Minutes played".equals(gameweekEvents	.get(i)
														.getDescription())) {
				this.minutesPlayed = gameweekEvents	.get(i)
													.getOccurrences();
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
