package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.repository.TeamRepository;

@Service
public class TeamServiceImpl implements TeamService {

	private PlayerService playerService;
	private TeamRepository teamRepository;

	@Autowired
	public TeamServiceImpl(PlayerService playerService, TeamRepository teamRepository) {
		this.playerService = playerService;
		this.teamRepository = teamRepository;
	}
	
	@Override
	public Team getTeamById(int teamId) throws MalformedURLException, JSONException, IOException {
		Team team = teamRepository.getTeamInfoById(teamId);
		team.setSelection(getTeamSelection(teamId));
		List<Player> squad = playerService.getPlayersByPlayerIdList(teamRepository.getPlayerIdsByTeam(teamId));
		team.setSquad(squad);

		return team;
	}

	public Selection getTeamSelection(int teamId) {
		
		return teamRepository.getTeamSelection(teamId);
	}

	@Override
	public void addTeam(Team team) {

		teamRepository.addTeam(team);
	}

	@Override
	public TeamForm getTeamFormById(int teamId) {
		
		return teamRepository.getTeamFormById(teamId);
	}

	@Override
	public void updateTeam(TeamForm teamForm) {
		playerService.updateTeamPlayers(teamForm);
		
		Team team = new Team();
		team.setId(teamForm.getTeamId());
		team.setName(teamForm.getTeamName());
		team.setTotalPoints(teamForm.getTeamPoints());
		teamRepository.updateTeam(team);
	}

	@Override
	public void addSelection(Selection selection, int teamId) {
		
		teamRepository.addSelection(selection, teamId);
	}

}
