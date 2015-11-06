package com.dave.fantasyfootball.service.impl;

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
import com.dave.fantasyfootball.service.PlayerService;

@Service
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	public List<Player> getAllPlayers() {
		return playerRepository.getAllPlayersDetail();
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
		for (int i = 0; i < teamForm.getPlayerIds()
									.size(); i++) {
			playerRepository.addPlayer(Integer.parseInt(teamForm.getPlayerIds()
																.get(i)),
					teamId);
		}
	}

	@Override
	public void updateTeamPlayers(TeamForm teamForm) {
		playerRepository.removePlayersFromTeam(teamForm.getTeamId());
		addTeamPlayers(teamForm);

	}

	@Override
	public Player getPlayerById(int id) {
		return playerRepository.getPlayerById(id);
	}

	@Override
	public Player getPlayerDetail(Player player) throws MalformedURLException, JSONException, IOException {
		return playerRepository.getPlayerDetail(player);
	}

	public List<Player> getSquadPlayersByPlayerIdList(List<Integer> playerIds)
			throws MalformedURLException, JSONException, IOException {

		List<Player> squad = new ArrayList<Player>();
		for (Integer playerId : playerIds) {
			squad.add(playerRepository.getPlayerById(playerId));
		}
		return squad;
	}

	@Override
	public void reloadPlayerInfo() {
		playerRepository.deleteAllPlayerInfo();
		for (Player player : getAllPlayers()) {
			playerRepository.addPlayerInfo(player);
		}
	}

	@Override
	public List<Player> getAllPlayersInfo() {
		return playerRepository.getAllPlayers();
	}

	@Override
	public List<Player> getSquadPlayersByTeamId(int teamId) {
		return playerRepository.getPlayersByTeamId(teamId);
	}
}