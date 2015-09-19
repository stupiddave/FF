package com.dave.fantasyfootball.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.MatchdayTeam;
import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.repository.TeamRepository;
import com.dave.fantasyfootball.service.MatchdayTeamService;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Service
public class MatchdayTeamServiceImpl implements MatchdayTeamService {

	private PlayerService playerService;
	private TeamRepository teamRepository;
	private TeamService teamService;
	private static final Logger log = Logger.getLogger(MatchdayTeamServiceImpl.class);

	@Autowired
	public MatchdayTeamServiceImpl(PlayerService playerService, TeamRepository teamRepository,
			TeamService teamService) {
		this.playerService = playerService;
		this.teamRepository = teamRepository;
		this.teamService = teamService;
	}

	@Override
	public MatchdayTeam buildMatchDayTeam(Team team)
			throws NumberFormatException, MalformedURLException, JSONException, IOException {

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

		Player selectedCaptain = playerService.getPlayerById(team	.getSelection()
																	.getCaptainId());
		Player selectedViceCaptain = playerService.getPlayerById(team	.getSelection()
																		.getViceCaptainId());

		Player playingCaptain = selectedCaptain;
		if (!matchdayTeam	.getStarters()
							.contains(selectedCaptain)
				&& matchdayTeam	.getStarters()
								.contains(selectedViceCaptain)) {
			playingCaptain = selectedViceCaptain;
		}

		matchdayTeam.setCaptain(playingCaptain);
		return matchdayTeam;
	}

	public MatchdayTeam buildSelectedMatchdayTeam(Team team)
			throws NumberFormatException, MalformedURLException, JSONException, IOException {

		List<Player> lineup = team	.getSelection()
									.getLineup();
		for (Player player : lineup) {
			playerService.getPlayerDetail(player);
		}
		List<Player> starters = new ArrayList<Player>(lineup.subList(0, 11));
		List<Player> subs = new ArrayList<Player>(lineup.subList(11, lineup.size()));

		MatchdayTeam selectedTeam = new MatchdayTeam(team.getId(), starters, subs);
		return selectedTeam;
	}

	@Override
	public void commitWeeklyScores(Team team)
			throws NumberFormatException, MalformedURLException, JSONException, IOException {

		teamRepository.commitTeamScore(team.getId(), getWeeklyScore(team));
	}

	@Override
	public int getWeeklyScore(Team team) throws MalformedURLException, JSONException, IOException {
		MatchdayTeam matchdayTeam = buildMatchDayTeam(team);
		int teamScore = 0;
		for (Player player : matchdayTeam.getStarters()) {
			teamScore = teamScore + player.getGameweekPoints();
		}
		teamScore = teamScore + playerService	.getPlayerDetail(matchdayTeam.getCaptain())
												.getGameweekPoints();
		return teamScore;
	}

	@Override
	public List<Team> getNewTeamScores() throws MalformedURLException, IOException {
		List<Team> teams = new ArrayList<Team>();
		for (int i = 1; i < 7; i++) {
			Team team = new Team();
			team = teamService.getTeamById(i);
			log.info("Getting score for " + team.getName());
			int weeklyScore = getWeeklyScore(team);
			team.setGameweekPoints(weeklyScore);
			team.setTotalPoints(team.getTotalPoints() + weeklyScore);
			teams.add(team);
		}
		return teams;
	}

}
