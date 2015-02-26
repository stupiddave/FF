package com.dave.fantasyfootball.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.SelectionFormPlayer;
import com.dave.fantasyfootball.service.SelectionFormService;

@Service
public class SelectionFormServiceImpl implements SelectionFormService {

	@Override
	public List<SelectionFormPlayer> getSelectionFormPlayersFromTeam(Team team) {
		List<Player> playerLineup = team.getSelection().getLineup();
		List<SelectionFormPlayer> selectionFormPlayerLineup = new ArrayList<SelectionFormPlayer>();
		
		for(Player player : playerLineup) {
			SelectionFormPlayer selectionFormPlayer = new SelectionFormPlayer();
			selectionFormPlayer.setId(player.getId());
		
			if (playerLineup.indexOf(player) < 11) {
				selectionFormPlayer.setIsStarter(true);
				selectionFormPlayer.setSubRank("N/A");
			} else {
				selectionFormPlayer.setIsStarter(false);
				selectionFormPlayer.setSubRank(Integer.toString(playerLineup.indexOf(player) - 10));
			}
			
			selectionFormPlayerLineup.add(selectionFormPlayer);
		}
		return selectionFormPlayerLineup;
	}

}
