package com.dave.fantasyfootball.repository;

import java.util.List;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.TeamForm;

public interface TeamRepository {

	Team getTeamInfoById(int id);

	void addTeam(Team team);

	TeamForm getTeamFormById(int teamId);

	void updateTeam(Team team);

	Selection getTeamSelection(int teamId, int gameweek);

	List<Player> getPlayersByTeam(int teamId);

	void addSelection(Selection selection, int selectionGameweek);

	void commitTeamScore(int teamId, int teamScore);

	List<Team> getTeamStandings();
}
