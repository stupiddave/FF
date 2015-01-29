package com.dave.fantasyfootball.domain;

import java.util.List;


public class Team {

	private int id;
	private String name;
	private Selection selection;
	private List<Player> squad;
	private int totalPoints;

	public Selection getSelection() {
		return selection;
	}

	public void setSelection(Selection selection) {
		this.selection = selection;
	}

	public int getTotalPoints() {
		return totalPoints;
	}

	public void setTotalPoints(int totalPoints) {
		this.totalPoints = totalPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Player> getSquad() {
		return squad;
	}

	public void setSquad(List<Player> squad) {
		this.squad = squad;
	}
}
