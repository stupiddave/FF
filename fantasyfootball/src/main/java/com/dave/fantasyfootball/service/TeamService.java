package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

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

	public void addSelection(Selection selection);

	public Selection getSelectionFromForm(SelectionForm selectionForm)
			throws MalformedURLException, JSONException, IOException;

	public List<Team> getTeamStandings();

	Selection getTeamSelection(int teamId, int gameweek);
}