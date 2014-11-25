package com.dave.fantasyfootball.service;

import java.util.List;

import com.dave.fantasyfootball.domain.Player;

public interface PlayerService {

	List<Player> getAllPlayers();

	public abstract List<Player> getPlayersByClub(String club);
}
