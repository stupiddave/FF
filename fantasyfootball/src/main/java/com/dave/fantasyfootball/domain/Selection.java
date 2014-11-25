package com.dave.fantasyfootball.domain;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.dave.fantasyfootball.utils.SquadListing;

@Component
public class Selection {

	private int teamId;
	private int selectionGameweek;
	private Map<SquadListing, Integer> squad;
	private Player captain;
	private Player viceCaptain;

	public Map<SquadListing, Integer> getSquad() {
		return squad;
	}

	public void setSquad(Map<SquadListing, Integer> squad) {
		this.squad = squad;
	}

	public Player getCaptain() {
		return captain;
	}

	public void setCaptain(Player captain) {
		this.captain = captain;
	}

	public Player getViceCaptain() {
		return viceCaptain;
	}

	public void setViceCaptain(Player viceCaptain) {
		this.viceCaptain = viceCaptain;
	}

	public int getSelectionGameweek() {
		return selectionGameweek;
	}

	public void setSelectionGameweek(int selectionGameweek) {
		this.selectionGameweek = selectionGameweek;
	}

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}
}
