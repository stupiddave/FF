package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.service.MatchdayTeamService;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.UserService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private PropertiesService propertiesService;
	private UserService userService;
	private PlayerService playerService;
	private TeamService teamService;
	private MatchdayTeamService matchdayTeamService;

	@Autowired
	public AdminController(PropertiesService propertiesService,
			UserService userService, PlayerService playerService, TeamService teamService, MatchdayTeamService matchdayTeamService) {
		this.propertiesService = propertiesService;
		this.userService = userService;
		this.playerService = playerService;
		this.teamService = teamService;
		this.matchdayTeamService = matchdayTeamService;
	}

	@RequestMapping
	public String admin(Model model, HttpServletRequest request) {
		if (!model.containsAttribute("user")) {
			model.addAttribute("user", userService.getUserByUsername(request
					.getUserPrincipal().getName()));
		}
		model.addAttribute("currentGameweek",
				propertiesService.getCurrentGameweek());

		return "admin";
	}

	@RequestMapping(value = "/incrementGameweek")
	public String incrementGameweek() {
		propertiesService.incrementGameweek();
		return "redirect:/admin";
	}

	@RequestMapping(value = "/decrementGameweek")
	public String decrementGameweek() {
		propertiesService.decrementGameweek();
		return "redirect:/admin";
	}
	
	@RequestMapping("/reloadPlayers")
	public String reload() {
		playerService.reloadPlayerInfo();
		return "redirect:/admin";
	}
	
	@RequestMapping(value = "/addInitialSelection/{teamId}", method = RequestMethod.GET)
	public String getInitialSelectionForm(@PathVariable("teamId") int teamId, Model model) throws MalformedURLException, JSONException, IOException {
		Team team = teamService.getTeamById(teamId);
		SelectionForm selectionForm = new SelectionForm();
		selectionForm.setTeamId(team.getId());
		Collections.sort(team.getSquad(), new PlayerPositionComparator());
		model.addAttribute("team", team);
		model.addAttribute("selection", selectionForm);
		return "updateLineup";
	}
	
	@RequestMapping(value = "/commitWeeklyScore", method = RequestMethod.GET)
	public String commitWeeklyScore() throws MalformedURLException, JSONException, IOException {
		for (int i = 1; i < 7; i++) {
			Team team = teamService.getTeamById(i);
			System.out.println("Committing score for " + team.getName());
			matchdayTeamService.commitWeeklyScores(team);
		}
		return "redirect:/admin";
	}

	@RequestMapping(value = "/addTeam", method = RequestMethod.GET)
	public String addTeam(Model model, @ModelAttribute("newTeam") TeamForm newTeam) {
		List<Player> allPlayersInfo = playerService.getAllPlayersInfo();
		model.addAttribute("allPlayers", allPlayersInfo);
		return "addTeam";

	}

	@Transactional
	@RequestMapping(value = "/addTeam", method = RequestMethod.POST)
	public String processNewTeam(@ModelAttribute("newTeam") TeamForm newTeam) {
		System.out.println("New team name is: " + newTeam.getTeamName());

		playerService.addTeamPlayers(newTeam);

		Team team = new Team();
		team.setId(newTeam.getTeamId());
		team.setName(newTeam.getTeamName());
		team.setTotalPoints(newTeam.getTeamPoints());
		teamService.addTeam(team);
		return "redirect:/admin/addInitialSelection/" + team.getId();
	}

	@RequestMapping(value="/addUser", method=RequestMethod.GET)
	public String addUserForm(@ModelAttribute("newUser") User newUser) {
		return "addUser";
	}
	
	@RequestMapping(value="/addUser", method=RequestMethod.POST)
	public String processNewUser(@ModelAttribute("newUser") User newUser) {
		userService.addUser(newUser);
		return "redirect:/admin";
	}

}
