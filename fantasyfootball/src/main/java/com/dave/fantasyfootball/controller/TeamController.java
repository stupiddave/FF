package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.Fixture;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.form.validator.SelectionFormValidator;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.ReferenceService;
import com.dave.fantasyfootball.service.SelectionFormService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Controller
public class TeamController {

	private TeamService teamService;
	private SelectionFormService selectionFormService;
	private SelectionFormValidator selectionFormValidator;
	private PropertiesService propertiesService;
	private ReferenceService fixtureService;

	Logger log = Logger.getLogger(TeamController.class);

	@Autowired
	public TeamController(TeamService teamService, SelectionFormService selectionFormService,
			SelectionFormValidator selectionFormValidator, PropertiesService propertiesService,
			ReferenceService fixtureService) {
		this.teamService = teamService;
		this.selectionFormService = selectionFormService;
		this.selectionFormValidator = selectionFormValidator;
		this.propertiesService = propertiesService;
		this.fixtureService = fixtureService;
	}

	@RequestMapping("/{TeamId}")
	public String listTeam(Model model, @PathVariable("TeamId") int teamId)
			throws MalformedURLException, JSONException, IOException {
		// List<Player> players = PlayerServiceImpl.getPlayersByTeam(teamId);
		Team team = teamService.getTeamById(teamId);
		model.addAttribute("TeamPlayers", team.getSelection());
		return "team";
	}

	@RequestMapping(value = "/updateLineup", method = RequestMethod.GET)
	public String getLineupForm(Model model, @AuthenticationPrincipal User user)
			throws MalformedURLException, JSONException, IOException {
		log.info("Current user is: " + user.getFirstName());
		int teamId = user.getTeamId();
		Team team = teamService.getTeamById(teamId);
		Collections.sort(team.getSquad(), new PlayerPositionComparator());
		int selectionGameweek = propertiesService.getSelectionGameweek();
		team.setSelection(teamService.getTeamSelection(teamId, selectionGameweek));
		SelectionForm selectionForm = new SelectionForm(team, selectionFormService);
		List<Fixture> fixtures = fixtureService.getFixturesByGameweek(selectionGameweek);

		model.addAttribute("currentGameweek", selectionGameweek);
		model.addAttribute("team", team);
		model.addAttribute("selection", selectionForm);
		model.addAttribute("fixtures", fixtures);
		return "updateLineup";
	}

	private Team getTeamByUser(User user) throws MalformedURLException, IOException {
		return teamService.getTeamById(user.getTeamId());
	}

	@RequestMapping(value = "/updateLineup", method = RequestMethod.POST)
	public String processLineupUpdate(@Valid @ModelAttribute("selection") SelectionForm selectionForm,
			BindingResult result, Model model, @AuthenticationPrincipal User user)
					throws MalformedURLException, JSONException, IOException {
		selectionFormValidator.validate(selectionForm, result);
		if (result.hasErrors()) {
			Team team = getTeamByUser(user);
			Collections.sort(team.getSquad(), new PlayerPositionComparator());
			model.addAttribute("team", team);
			return "/updateLineup";
		}
		Selection selection = teamService.getSelectionFromForm(selectionForm);
		teamService.addSelection(selection);
		return "redirect:/updateLineup";
	}

	@RequestMapping(value = "/myTeam", method = RequestMethod.GET)
	public String myTeam(Model model, @AuthenticationPrincipal User user)
			throws MalformedURLException, JSONException, IOException {
		Team team = getTeamByUser(user);
		model.addAttribute("currentGameweek", propertiesService.getSelectionGameweek() - 1);
		model.addAttribute("team", team);
		model.addAttribute("user", user);
		return "team";
	}

	@RequestMapping(value = "/standings", method = RequestMethod.GET)
	public String getStandings(Model model) {
		List<Team> teams = teamService.getTeamStandings();
		model.addAttribute("teams", teams);
		model.addAttribute("currentGameweek", propertiesService.getSelectionGameweek() - 1);
		return "league";
	}

}
