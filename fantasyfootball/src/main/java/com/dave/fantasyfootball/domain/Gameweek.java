package com.dave.fantasyfootball.domain;

import java.sql.Timestamp;

public class Gameweek {

	private int gameweek;
	private Timestamp start;
	private Timestamp end;

	public Gameweek(int gameweek, Timestamp start, Timestamp end) {
		this.gameweek = gameweek;
		this.start = start;
		this.end = end;
	}

	public Gameweek() {
	}

	public int getGameweek() {
		return gameweek;
	}

	public void setGameweek(int gameweek) {
		this.gameweek = gameweek;
	}

	public Timestamp getStart() {
		return start;
	}

	public void setStart(Timestamp start) {
		this.start = start;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}
}
