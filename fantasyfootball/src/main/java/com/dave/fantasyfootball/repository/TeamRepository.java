package com.dave.fantasyfootball.repository;

import java.util.List;

import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.TeamForm;

public interface TeamRepository {

	Team getTeamInfoById(int id);
	void addTeam(Team team);
	TeamForm getTeamFormById(int teamId);
	void updateTeam(Team team);
	Selection getTeamSelection(int teamId);
	void addSelection(Selection selection, int teamId);
	List<Integer> getPlayerIdsByTeam(int teamId);
}
