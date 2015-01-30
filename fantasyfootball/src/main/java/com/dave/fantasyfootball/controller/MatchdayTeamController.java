package com.dave.fantasyfootball.controller;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.MatchdayTeam;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.domain.User;
import com.dave.fantasyfootball.service.MatchdayTeamService;
import com.dave.fantasyfootball.service.TeamService;
import com.dave.fantasyfootball.service.UserService;

@Controller
public class MatchdayTeamController {

	private UserService userService;
	private TeamService teamService;
	private MatchdayTeamService matchdayTeamService;

	@Autowired
	private User user;

	@Autowired
	public MatchdayTeamController(TeamService teamService, UserService userService, MatchdayTeamService matchdayTeamService) {
		this.teamService = teamService;
		this.userService = userService;
		this.matchdayTeamService = matchdayTeamService;
	}
	
	@RequestMapping(value = "/latestPoints", method = RequestMethod.GET)
	public String latestPoints(HttpServletRequest request, Model model)
			throws MalformedURLException, JSONException, IOException {
		if (user.getUsername() == null) {
			userService.buildSessionUser(request.getUserPrincipal().getName());
		}
		Team team = teamService.getTeamById(user.getTeamId());
		MatchdayTeam matchdayTeam = matchdayTeamService.buildMatchDayTeam(team);
		model.addAttribute("matchdayTeam", matchdayTeam);
		model.addAttribute("team", team);
		return "points";
	}

}
