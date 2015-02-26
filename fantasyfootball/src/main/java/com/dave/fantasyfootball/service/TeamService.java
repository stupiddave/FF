package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;

public interface TeamService {

	public abstract Team getTeamById(int teamId) throws MalformedURLException, JSONException, IOException;
	public abstract void addTeam(Team team);
	public abstract TeamForm getTeamFormById(int teamId);
	public abstract void updateTeam(TeamForm teamForm);
	public abstract void addSelection(SelectionForm selectionForm, int teamId);
	public abstract Selection getTeamSelection(int teamId);
}