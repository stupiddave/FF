package com.dave.fantasyfootball.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.utils.Position;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

	public PlayerRepositoryImpl() {
		players = new ArrayList<>();
		Player player1 = new Player();
		player1.setFirstName("Gary");
		player1.setLastName("Brown");
		player1.setId(1);
		player1.setPosition(Position.FORWARD);
		player1.setTeamId(1);

		Player player2 = new Player();
		player2.setFirstName("Andy");
		player2.setLastName("Beare");
		player2.setId(2);
		player2.setPosition(Position.MIDFIELDER);
		player2.setTeamId(1);
		
		Player player3 = new Player();
		player3.setFirstName("John");
		player3.setLastName("Nixon");
		player3.setId(3);
		player3.setPosition(Position.DEFENDER);
		player3.setTeamId(1);
		
		players.add(player1);
		players.add(player2);
		players.add(player3);
	}
	
	List<Player> players;
	
	@Override
	public List<Player> getAllPlayers() {
		return players;
	}

	@Override
	public List<Player> getPlayersByClub(String club) {
		List<Player> playersByClub = new ArrayList<Player>();
		for (Player player : players) {
			if (player.getClub() == club) {
				playersByClub.add(player);
			}
		}
		return playersByClub;
	}
	
	@Override
	public List<Player> getPlayersByPosition(Position position) {
		List<Player> playersByPosition = new ArrayList<Player>();
		for (Player player : players) {
			if (player.getPosition().equals(position)) {
				playersByPosition.add(player);
			}
		}
		return null;
	}

}
