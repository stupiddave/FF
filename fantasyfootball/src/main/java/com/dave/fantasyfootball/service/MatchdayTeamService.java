package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.MatchdayTeam;
import com.dave.fantasyfootball.domain.Team;

public interface MatchdayTeamService {

	public MatchdayTeam buildMatchDayTeam(Team team)
			throws NumberFormatException, MalformedURLException, JSONException, IOException;

	public void commitWeeklyScores(Team team)
			throws NumberFormatException, MalformedURLException, JSONException, IOException;

	public int getWeeklyScore(Team team) throws MalformedURLException, JSONException, IOException;

	public List<Team> getNewTeamScores() throws MalformedURLException, IOException;
}
