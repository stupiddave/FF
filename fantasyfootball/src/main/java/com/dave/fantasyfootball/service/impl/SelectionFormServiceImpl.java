package com.dave.fantasyfootball.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.service.SelectionFormService;

@Service
public class SelectionFormServiceImpl implements SelectionFormService {

	@Override
	public List<Integer> getStartersFromTeam(Team team) {
		List<Integer> starters = new ArrayList<Integer>();
		List<Player> playerLineup = new ArrayList<Player>();
		if (team.getSelection() != null) {
			playerLineup = team.getSelection().getLineup();
		} else {
			playerLineup = team.getSquad();
		}

		for (Player player : playerLineup) {
			if (playerLineup.indexOf(player) < 11) {
				starters.add(player.getId());
			}
		}
		return starters;
	}

	@Override
	public List<Integer> getSubsFromTeam(Team team) {
		List<Integer> subs = new ArrayList<Integer>();
		List<Player> playerLineup = new ArrayList<Player>();
		if (team.getSelection() != null) {
			playerLineup = team.getSelection().getLineup();
		} else {
			playerLineup = team.getSquad();
		}

		for (int i = 11; i < playerLineup.size(); i++) {
			subs.add(playerLineup.get(i).getId());
		}

		return subs;
	}

}
