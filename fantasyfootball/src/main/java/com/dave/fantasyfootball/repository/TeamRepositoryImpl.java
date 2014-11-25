package com.dave.fantasyfootball.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

	public TeamRepositoryImpl() {
		ArrayList<Player> team1Players = new ArrayList<Player>();
		Player player1 = new Player();
		player1.setFirstName("davy");
		Player player2 = new Player();
		player2.setFirstName("jonny");
		Player player3 = new Player();
		player3.setFirstName("danny");
		team1Players.add(player1);
		team1Players.add(player2);
		team1Players.add(player3);
		
		
		Team team1 = new Team();
		team1.setId(1);
		team1.setName("team1");
		team1.setTotalPoints(0);
//		team1.setSelection(team1Players);
		
		Team team2 = new Team();
		team2.setId(2);
		team2.setName("team2");
		team2.setTotalPoints(5);
		
		teams.add(team1);
		teams.add(team2);
	}
	List<Team> teams = new ArrayList<Team>();

	@Override
	public Team getTeamById(int id) {
		Team teamById = null;
		for(Team team : teams) {
			if(team.getId() == id) {
				teamById = team;
			}
		}
		return teamById;
	}

}