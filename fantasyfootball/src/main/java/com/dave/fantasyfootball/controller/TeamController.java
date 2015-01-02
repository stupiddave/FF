package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;

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

import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.UserService;

@Controller
public class TeamController {

	private PlayerService playerService;
	private TeamService teamService;
	private UserService userService;
	private PropertiesService propertiesService;

	@Autowired
	private User user;

	@Autowired
	public TeamController(PlayerService playerService, TeamService teamService,
			UserService userService, PropertiesService propertiesService) {
		this.playerService = playerService;
		this.teamService = teamService;
		this.userService = userService;
		this.propertiesService = propertiesService;
	}

	@RequestMapping("/{TeamId}")
	public String listTeam(Model model, @PathVariable("TeamId") int teamId)
			throws MalformedURLException, JSONException, IOException {
		// List<Player> players = PlayerServiceImpl.getPlayersByTeam(teamId);
		Team team = teamService.getTeamById(teamId);
		model.addAttribute("TeamPlayers", team.getSelection());
		return "team";
	}

	@RequestMapping(value = "/addTeam", method = RequestMethod.GET)
	public String addTeam(@ModelAttribute("newTeam") TeamForm newTeam) {
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
		return "redirect:/";
	}

	@RequestMapping(value = "/updateTeam/{teamId}", method = RequestMethod.GET)
	public String updateTeam(@PathVariable("teamId") int teamId, Model model) {
		TeamForm teamForm = new TeamForm();
		teamForm = teamService.getTeamFormById(teamId);
		model.addAttribute("teamForm", teamForm);
		return "updateTeam";
	}

	@RequestMapping(value = "/updateTeam", method = RequestMethod.POST)
	public String processTeamUpdate(
			@ModelAttribute("teamForm") TeamForm teamForm) {
		teamService.updateTeam(teamForm);
		return "redirect:/";
	}

	@RequestMapping(value = "/updateLineup", method = RequestMethod.GET)
	public String getLineupForm(HttpServletRequest request, Model model)
			throws MalformedURLException, JSONException, IOException {
		if (user.getUsername() == null) {
			userService.buildSessionUser(request.getUserPrincipal().getName());
		}
		System.out.println("Current user is: " + user.getFirstName());
		Team team = teamService.getTeamById(user.getTeamId());
		model.addAttribute("team", team);
		Selection selection = new Selection();
		selection.setSelectionGameweek(propertiesService.getCurrentGameweek());
		model.addAttribute("selection", new Selection());
		return "updateLineup";
	}

	@RequestMapping(value = "/updateLineup", method = RequestMethod.POST)
	public String processLineupUpdate(
			@ModelAttribute("selection") Selection selection) {
		teamService.addSelection(selection, user.getTeamId());
		return "redirect:/";
	}

	@RequestMapping(value = "/myTeam", method = RequestMethod.GET)
	public String myTeam(HttpServletRequest request, Model model)
			throws MalformedURLException, JSONException, IOException {
		if (user.getUsername() == null) {
			userService.buildSessionUser(request.getUserPrincipal().getName());
		}
		Team team = teamService.getTeamById(user.getTeamId());
		model.addAttribute("team", team);
		model.addAttribute("user", user);
		return "team";
	}
}
