package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.form.TeamForm;

public interface PlayerService {

	List<Player> getAllPlayers();

	abstract List<Player> getPlayersByClub(String club);
	abstract void addTeamPlayers(TeamForm teamForm);
	abstract void updateTeamPlayers(TeamForm teamForm);
	Player getPlayerById(int id) throws MalformedURLException, JSONException, IOException;
	List<Player> getPlayersByPlayerIdList(List<Integer> playerIds) throws MalformedURLException, JSONException, IOException;
	
}