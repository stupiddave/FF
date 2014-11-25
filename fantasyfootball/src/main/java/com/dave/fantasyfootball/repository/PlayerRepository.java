package com.dave.fantasyfootball.repository;

import java.util.List;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.utils.Position;

public interface PlayerRepository {

	List<Player> getAllPlayers();
	List<Player> getPlayersByClub(String club);
	List<Player> getPlayersByPosition(Position position);
}
