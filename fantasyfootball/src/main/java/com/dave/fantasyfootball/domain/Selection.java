package com.dave.fantasyfootball.domain;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Selection {

	private int selectionGameweek;
	private List<Player> lineup;
	private int captainId;
	private int viceCaptainId;

	public int getSelectionGameweek() {
		return selectionGameweek;
	}

	public void setSelectionGameweek(int selectionGameweek) {
		this.selectionGameweek = selectionGameweek;
	}

	public List<Player> getLineup() {
		return lineup;
	}

	public void setLineup(List<Player> lineup) {
		this.lineup = lineup;
	}

	public int getCaptainId() {
		return captainId;
	}

	public void setCaptainId(int captainId) {
		this.captainId = captainId;
	}

	public int getViceCaptainId() {
		return viceCaptainId;
	}

	public void setViceCaptainId(int viceCaptainId) {
		this.viceCaptainId = viceCaptainId;
	}
}