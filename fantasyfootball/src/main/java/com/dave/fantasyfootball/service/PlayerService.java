package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.form.TeamForm;

public interface PlayerService {

	List<Player> getAllPlayers();

	List<Player> getPlayersByClub(String club);

	void addTeamPlayers(TeamForm teamForm);

	void updateTeamPlayers(TeamForm teamForm);

	Player getPlayerById(int id) throws MalformedURLException, JSONException, IOException;

	List<Player> getSquadPlayersByPlayerIdList(List<Integer> playerIds)
			throws MalformedURLException, JSONException, IOException;

	void reloadPlayerInfo();

	List<Player> getAllPlayersInfo();

	Player getPlayerDetail(Player player) throws MalformedURLException, JSONException, IOException;

	List<Player> getSquadPlayersByTeamId(int teamId);

}