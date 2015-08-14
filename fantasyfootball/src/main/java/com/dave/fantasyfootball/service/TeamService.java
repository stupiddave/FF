package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;

public interface TeamService {

	public Team getTeamById(int teamId) throws MalformedURLException, JSONException, IOException;
	public void addTeam(Team team);
	public TeamForm getTeamFormById(int teamId);
	public void updateTeam(TeamForm teamForm);
	public Selection getTeamSelection(int teamId);
	public void addSelection(Selection selection, int teamId);
}