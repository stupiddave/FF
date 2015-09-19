package com.dave.fantasyfootball.domain;

import java.util.Date;

public class Fixture {

	private Date kickoff;
	private EplTeam homeTeam;
	private EplTeam awayTeam;
	private Integer gameweek;

	public Fixture(Date kickoff, EplTeam homeTeam, EplTeam awayTeam, int gameweek) {
		this.kickoff = kickoff;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.gameweek = gameweek;
	}

	public Date getKickoff() {
		return kickoff;
	}

	public void setKickoff(Date kickoff) {
		this.kickoff = kickoff;
	}

	public EplTeam getHomeTeam() {
		return homeTeam;
	}
	
	public void setHomeTeam(EplTeam homeTeam) {
		this.homeTeam = homeTeam;
	}

	public void setAwayTeam(EplTeam awayTeam) {
		this.awayTeam = awayTeam;
	}

	public void setGameweek(Integer gameweek) {
		this.gameweek = gameweek;
	}

	public EplTeam getAwayTeam() {
		return awayTeam;
	}

	public Integer getGameweek() {
		return gameweek;
	}
}
