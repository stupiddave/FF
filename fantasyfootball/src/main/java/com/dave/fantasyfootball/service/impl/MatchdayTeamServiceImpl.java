package com.dave.fantasyfootball.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.MatchdayTeam;
import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.service.MatchdayTeamService;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Service
public class MatchdayTeamServiceImpl implements MatchdayTeamService {

	public PlayerService playerService;

	@Autowired
	public MatchdayTeamServiceImpl(PlayerService playerService) {
		this.playerService = playerService;
	}

	@Override
	public MatchdayTeam buildMatchDayTeam(Team team) throws NumberFormatException,
			MalformedURLException, JSONException, IOException {

		MatchdayTeam matchdayTeam = buildSelectedMatchdayTeam(team);
		matchdayTeam.makeSubstitutions();
		reorderStarters(matchdayTeam.getStarters());
		resolveCaptaincy(matchdayTeam, team);

		return matchdayTeam;
	}

	private void reorderStarters(List<Player> starters) {
		Collections.sort(starters, new PlayerPositionComparator());
	}

	private MatchdayTeam resolveCaptaincy(MatchdayTeam matchdayTeam, Team team)
			throws MalformedURLException, JSONException, IOException {

		Player selectedCaptain = playerService.getPlayerById(team.getSelection().getCaptainId());
		Player selectedViceCaptain = playerService.getPlayerById(team.getSelection()
				.getViceCaptainId());

		Player playingCaptain = selectedCaptain;
		if (!matchdayTeam.getStarters().contains(selectedCaptain)
				&& matchdayTeam.getStarters().contains(selectedViceCaptain)) {
			playingCaptain = selectedViceCaptain;
		}

		matchdayTeam.setCaptain(playingCaptain);
		return matchdayTeam;
	}

	public MatchdayTeam buildSelectedMatchdayTeam(Team team) throws NumberFormatException,
			MalformedURLException, JSONException, IOException {

		List<Player> lineup = team.getSelection().getLineup();
		List<Player> starters = new ArrayList<Player>(lineup.subList(0, 11));
		List<Player> subs = new ArrayList<Player>(lineup.subList(11, lineup.size()));

		MatchdayTeam selectedTeam = new MatchdayTeam(team.getId(), starters, subs);
		return selectedTeam;
	}

}
