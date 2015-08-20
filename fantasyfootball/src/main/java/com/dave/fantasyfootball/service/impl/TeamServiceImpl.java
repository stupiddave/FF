package com.dave.fantasyfootball.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.repository.TeamRepository;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.TeamService;

@Service
public class TeamServiceImpl implements TeamService {

	private PlayerService playerService;
	private TeamRepository teamRepository;
	private PropertiesService propertiesService;

	@Autowired
	public TeamServiceImpl(PlayerService playerService,
			TeamRepository teamRepository, PropertiesService propertiesService) {
		this.playerService = playerService;
		this.teamRepository = teamRepository;
		this.propertiesService = propertiesService;
	}

	@Override
	public Team getTeamById(int teamId) throws MalformedURLException,
			JSONException, IOException {
		Team team = teamRepository.getTeamInfoById(teamId);
		team.setSelection(getTeamSelection(teamId));
		List<Player> squad = playerService
				.getSquadPlayersByPlayerIdList(teamRepository
						.getPlayerIdsByTeam(teamId));
		team.setSquad(squad);

		return team;
	}

	public Selection getTeamSelection(int teamId) {

		return teamRepository.getTeamSelection(teamId);
	}

	@Override
	public void addTeam(Team team) {

		teamRepository.addTeam(team);
	}

	@Override
	public TeamForm getTeamFormById(int teamId) {

		return teamRepository.getTeamFormById(teamId);
	}

	@Override
	public void updateTeam(TeamForm teamForm) {
		playerService.updateTeamPlayers(teamForm);

		Team team = new Team();
		team.setId(teamForm.getTeamId());
		team.setName(teamForm.getTeamName());
		team.setTotalPoints(teamForm.getTeamPoints());
		teamRepository.updateTeam(team);
	}

	@Override
	public void addSelection(Selection selection) {
		teamRepository.addSelection(selection, propertiesService.getCurrentGameweek());
	}

	@Override
	public Selection getSelectionFromForm(SelectionForm selectionForm)
			throws MalformedURLException, JSONException, IOException {
		Selection selection = new Selection();
		selection.setTeamId(selectionForm.getTeamId());
		selection.setSelectionGameweek(propertiesService.getCurrentGameweek());
		selection.setLineup(getLineupFromForm(selectionForm));
		selection.setCaptainId(selectionForm.getCaptain());
		selection.setViceCaptainId(selectionForm.getViceCaptain());
		return selection;
	}
	
	private List<Player> getLineupFromForm(SelectionForm selectionForm)
			throws MalformedURLException, JSONException, IOException {
		List<Player> playerLineup = new ArrayList<Player>();
		List<Integer> starters = selectionForm.getStarters();
		List<Integer> subs = getSubs(selectionForm);
		List<Integer> formLineup = new ArrayList<Integer>();
		formLineup.addAll(starters);
		formLineup.addAll(subs);
		for (Integer formPlayer : formLineup) {
			Player player = playerService.getPlayerById(formPlayer);
			playerLineup.add(player);
		}
		return playerLineup;
	}

	private List<Integer> getSubs(SelectionForm selectionForm) {
		List<Integer> subs = new ArrayList<Integer>();
		subs.add(selectionForm.getSub1());
		subs.add(selectionForm.getSub2());
		subs.add(selectionForm.getSub3());
		subs.add(selectionForm.getSub4());
		subs.add(selectionForm.getSub5());
		subs.add(selectionForm.getSub6());
		subs.add(selectionForm.getSub7());
		return subs;
	}


}
