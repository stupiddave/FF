package com.dave.fantasyfootball.domain;

public class GameweekEvent {

	private String description;
	private int occurrences;
	private int points;

	public GameweekEvent(String description, int occurrences, int points) {
		this.description = description;
		this.occurrences = occurrences;
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public int getOccurrences() {
		return occurrences;
	}

	public int getPoints() {
		return points;
	}
}
