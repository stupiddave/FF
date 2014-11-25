package com.dave.fantasyfootball.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.repository.PlayerRepository;
import com.dave.fantasyfootball.utils.Position;

@Service
public class PlayerServiceImpl implements PlayerService{


	public PlayerServiceImpl() {

	}
	@Autowired
	private PlayerRepository playerRepository;
	
	private ArrayList<Player> players;

	public List<Player> getAllPlayers() {
		return playerRepository.getAllPlayers(); 
	}
	
	@Override
	public List<Player> getPlayersByClub(String club) {
		
		List<Player> playersByClub = new ArrayList<Player>();
		for (Player player : players) {
			playersByClub.add(player);
		}
		return playersByClub;
	}
}
