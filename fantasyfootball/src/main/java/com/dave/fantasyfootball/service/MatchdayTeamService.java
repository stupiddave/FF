package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.MatchdayTeam;
import com.dave.fantasyfootball.domain.Team;

public interface MatchdayTeamService {

	public abstract MatchdayTeam buildMatchDayTeam(Team team) throws NumberFormatException, MalformedURLException, JSONException, IOException;

	public abstract void commitWeeklyScores(Team team) throws NumberFormatException, MalformedURLException, JSONException, IOException;
}
