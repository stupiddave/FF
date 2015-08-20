package com.dave.fantasyfootball.repository;

import java.io.IOException;
import java.util.List;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.utils.Position;

public interface PlayerRepository {

	List<Player> getAllPlayers();
	List<Player> getPlayersByClub(String club);
	List<Player> getPlayersByPosition(Position position);
	void addPlayer(int string, int teamId);
	List<Integer> getPlayersByTeam(int teamId);
	void removePlayersFromTeam(int teamId);
	Player getPlayerById(int id);
	void deleteAllPlayerInfo();
	void addPlayerInfo(Player player);
	List<Player> getAllPlayersInfo();
	List<Player> getPlayersByIdList(List<Integer> ids);
	Player getPlayerDetail(Player player) throws JSONException, IOException;
}
