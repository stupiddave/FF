package com.dave.fantasyfootball.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.repository.PlayerRepository;
import com.dave.fantasyfootball.repository.TeamRepository;

public class TeamServiceImpl implements TeamService {

	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Override
	public Team getTeamById(int teamId) {
//		List<Player> players = playerRepository.getPlayersByClub(teamId);
		
		return teamRepository.getTeamById(teamId);
	}

	

}
