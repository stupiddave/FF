package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.Gameweek;
import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.form.UpdateSquadForm;
import com.dave.fantasyfootball.service.MatchdayTeamService;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.ReferenceService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.UserService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Controller
@RequestMapping("/admin")
public class AdminController {

	Logger log = Logger.getLogger(AdminController.class);
	private PropertiesService propertiesService;
	private UserService userService;
	private PlayerService playerService;
	private TeamService teamService;
	private MatchdayTeamService matchdayTeamService;
	private ReferenceService fixtureService;

	@Autowired
	public AdminController(PropertiesService propertiesService, UserService userService, PlayerService playerService,
			TeamService teamService, MatchdayTeamService matchdayTeamService, ReferenceService fixtureService) {
		this.propertiesService = propertiesService;
		this.userService = userService;
		this.playerService = playerService;
		this.teamService = teamService;
		this.matchdayTeamService = matchdayTeamService;
		this.fixtureService = fixtureService;
	}

	@RequestMapping()
	public String admin(Model model, HttpServletRequest request, @AuthenticationPrincipal User user) {
		Gameweek lastGameweek = propertiesService.getLastGameweek();
		Calendar calendarEnd = Calendar.getInstance();
		calendarEnd.setTime(lastGameweek.getEnd());
		calendarEnd.add(Calendar.DAY_OF_WEEK, 7);
		Calendar calendarStart = Calendar.getInstance();
		calendarStart.setTime(lastGameweek.getEnd());
		calendarStart.add(Calendar.SECOND, 1);
		Timestamp newEndTimestamp = new Timestamp(calendarEnd	.getTime()
																.getTime());
		Timestamp newStartTimestamp = new Timestamp(calendarStart	.getTime()
																	.getTime());

		model.addAttribute("user", user);
		model.addAttribute("currentGameweek", propertiesService.getSelectionGameweek());
		model.addAttribute("lastGameweek", lastGameweek);
		model.addAttribute("newGameweek",
				new Gameweek(lastGameweek.getGameweek() + 1, newStartTimestamp, newEndTimestamp));
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

	@RequestMapping(value = "/addGameweek", method = RequestMethod.GET)
	public String addGameweekForm(Model model) {

		return "addGameweek";
	}

	@RequestMapping(value = "/addGameweek", method = RequestMethod.POST)
	public String processAddGameweek(@ModelAttribute Gameweek newGameweek) {
		propertiesService.addGameweek(newGameweek);
		return "redirect:/admin";
	}

	@RequestMapping("/reloadPlayers")
	public String reload() {
		playerService.reloadPlayerInfo();
		return "redirect:/admin";
	}

	@RequestMapping(value = "/addInitialSelection/{teamId}", method = RequestMethod.GET)
	public String getInitialSelectionForm(@PathVariable("teamId") int teamId, Model model)
			throws MalformedURLException, JSONException, IOException {
		Team team = teamService.getTeamById(teamId);
		SelectionForm selectionForm = new SelectionForm();
		selectionForm.setTeamId(team.getId());
		Collections.sort(team.getSquad(), new PlayerPositionComparator());
		model.addAttribute("team", team);
		model.addAttribute("selection", selectionForm);
		return "updateLineup";
	}

	@RequestMapping(value = "/checkWeeklyScores", method = RequestMethod.GET)
	public String commitWeeklyScore(Model model) throws MalformedURLException, JSONException, IOException {
		int weekToScore = propertiesService.getSelectionGameweek() - 1;
		List<Team> teams = matchdayTeamService.getNewTeamScores();

		model.addAttribute("teams", teams);
		model.addAttribute("gameweek", weekToScore);
		return "/checkWeeklyScores";
	}

	@RequestMapping(value = "/confirmWeeklyScores")
	public String confirmWeeklyScores() throws MalformedURLException, IOException {
		for (Team team : matchdayTeamService.getNewTeamScores()) {
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

	@RequestMapping(value = "/addUser", method = RequestMethod.GET)
	public String addUserForm(@ModelAttribute("newUser") User newUser) {
		return "addUser";
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String processNewUser(@ModelAttribute("newUser") User newUser) {
		userService.addUser(newUser);
		return "redirect:/admin";
	}

	@RequestMapping(value = "/updateFixtures", method = RequestMethod.GET)
	public String updateFixtures() throws IOException, ParseException {
		fixtureService.updateAllFixtures();
		return "redirect:/admin";
	}

	@RequestMapping(value = "/updateEplTeams", method = RequestMethod.GET)
	public String updateEplTeams() throws IOException, ParseException {
		fixtureService.updateAllEplTeams();
		return "redirect:/admin";
	}

	// @RequestMapping(value = "/updateTeam/{teamId}", method =
	// RequestMethod.GET)
	// public String updateTeam(@PathVariable("teamId") int teamId, Model model)
	// {
	// TeamForm teamForm = new TeamForm();
	// teamForm = teamService.getTeamFormById(teamId);
	// model.addAttribute("teamForm", teamForm);
	// return "updateTeam";
	// }
	//
	// @RequestMapping(value = "/updateTeam", method = RequestMethod.POST)
	// public String processTeamUpdate(@ModelAttribute("teamForm") TeamForm
	// teamForm) {
	// teamService.updateTeam(teamForm);
	// return "redirect:/";
	// }

	@RequestMapping(value = "/updateSquad/{teamId}", method = RequestMethod.GET)
	public String processTeamUpdate(@PathVariable("teamId") int teamId, Model model,
			@ModelAttribute("newSquad") UpdateSquadForm newSquad) {
		Team team = teamService.getTeamBrief(teamId);
		model.addAttribute(team);
		model.addAttribute("allPlayers", playerService.getAllPlayersInfo());
		return "updateSquad";
	}
}
