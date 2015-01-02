package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.repository.PlayerRepository;

@Service
public class PlayerServiceImpl implements PlayerService{

	@Autowired
	private PlayerRepository playerRepository;


	public List<Player> getAllPlayers() {
		return playerRepository.getAllPlayers(); 
	}
	
	@Override
	public List<Player> getPlayersByClub(String club) {
		List<Player> players = new ArrayList<Player>();
		
		List<Player> playersByClub = new ArrayList<Player>();
		for (Player player : players) {
			playersByClub.add(player);
		}
		return playersByClub;
	}

	@Override
	public void addTeamPlayers(TeamForm teamForm) {
		int teamId = teamForm.getTeamId();
		for(int i = 0; i < teamForm.getPlayerIds().size(); i++) {
			playerRepository.addPlayer(Integer.parseInt(teamForm.getPlayerIds().get(i)), teamId);
		}
	}

	@Override
	public void updateTeamPlayers(TeamForm teamForm) {
		playerRepository.removePlayersFromTeam(teamForm.getTeamId());
		addTeamPlayers(teamForm);
				
	}

	@Override
	public Player getPlayerById(int id) throws MalformedURLException, JSONException, IOException {
		return playerRepository.getPlayerById(id);
	}

	public List<Player> getPlayersByPlayerIdList(
			List<Integer> playerIds) throws MalformedURLException, JSONException, IOException {
		List<Player> squad = new ArrayList<Player>();
		for (int id : playerIds) {
			Player player = getPlayerById(id);
			squad.add(player);
		}
		return squad;
	}
}