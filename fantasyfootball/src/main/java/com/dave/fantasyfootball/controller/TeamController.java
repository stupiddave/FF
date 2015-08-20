package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.form.validator.SelectionFormValidator;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.service.PropertiesService;
import com.dave.fantasyfootball.service.SelectionFormService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.UserService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Controller
public class TeamController {

	private PlayerService playerService;
	private TeamService teamService;
	private UserService userService;
	private PropertiesService propertiesService;
	private SelectionFormService selectionFormService;
	private SelectionFormValidator selectionFormValidator;

	@Autowired
	private User user;

	@Autowired
	public TeamController(PlayerService playerService, TeamService teamService, UserService userService,
			PropertiesService propertiesService, SelectionFormService selectionFormService, SelectionFormValidator selectionFormValidator) {
		this.playerService = playerService;
		this.teamService = teamService;
		this.userService = userService;
		this.propertiesService = propertiesService;
		this.selectionFormService = selectionFormService;
		this.selectionFormValidator = selectionFormValidator;
	}

	@RequestMapping("/{TeamId}")
	public String listTeam(Model model, @PathVariable("TeamId") int teamId)
			throws MalformedURLException, JSONException, IOException {
		// List<Player> players = PlayerServiceImpl.getPlayersByTeam(teamId);
		Team team = teamService.getTeamById(teamId);
		model.addAttribute("TeamPlayers", team.getSelection());
		return "team";
	}

//	@RequestMapping(value = "/updateTeam/{teamId}", method = RequestMethod.GET)
	public String updateTeam(@PathVariable("teamId") int teamId, Model model) {
		TeamForm teamForm = new TeamForm();
		teamForm = teamService.getTeamFormById(teamId);
		model.addAttribute("teamForm", teamForm);
		return "updateTeam";
	}

//	@RequestMapping(value = "/updateTeam", method = RequestMethod.POST)
	public String processTeamUpdate(@ModelAttribute("teamForm") TeamForm teamForm) {
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
		Team team = getTeamByUser();
		model.addAttribute("team", team);
		SelectionForm selectionForm = new SelectionForm(team, selectionFormService);
		selectionForm.setTeamId(team.getId());
		Collections.sort(team.getSquad(), new PlayerPositionComparator());
		model.addAttribute("selection", selectionForm);
		return "updateLineup";
	}

	private Team getTeamByUser() throws MalformedURLException, IOException {
		return teamService.getTeamById(user.getTeamId());
	}

	@RequestMapping(value = "/updateLineup", method = RequestMethod.POST)
	public String processLineupUpdate(@Valid @ModelAttribute("selection") SelectionForm selectionForm, BindingResult result, Model model)
			throws MalformedURLException, JSONException, IOException {
		selectionFormValidator.validate(selectionForm, result);
		if(result.hasErrors()) {
			Team team = getTeamByUser();
			Collections.sort(team.getSquad(), new PlayerPositionComparator());
			model.addAttribute("team", team);
			return "/updateLineup";
		}
		Selection selection = teamService.getSelectionFromForm(selectionForm);
		teamService.addSelection(selection);
		return "redirect:/";
	}

	@RequestMapping(value = "/myTeam", method = RequestMethod.GET)
	public String myTeam(HttpServletRequest request, Model model)
			throws MalformedURLException, JSONException, IOException {
		if (user.getUsername() == null) {
			userService.buildSessionUser(request.getUserPrincipal().getName());
		}
		Team team = getTeamByUser();
		model.addAttribute("team", team);
		model.addAttribute("user", user);
		return "team";
	}

}
