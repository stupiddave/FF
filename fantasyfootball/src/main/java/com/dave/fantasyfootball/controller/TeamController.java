package com.dave.fantasyfootball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.NewTeamForm;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.service.TeamService;

@Controller
@RequestMapping("/team")
public class TeamController {

	@Autowired
	private TeamService teamService;

	@RequestMapping("/{TeamId}")
	public String listTeam(Model model, @PathVariable("TeamId") int teamId) {
//		List<Player> players = PlayerServiceImpl.getPlayersByTeam(teamId);
		Team team = teamService.getTeamById(teamId);
		model.addAttribute("TeamPlayers", team.getSelection());
		return "team";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public String addTeam(@ModelAttribute("newTeam") NewTeamForm newTeam) {
		return "addTeam";
		
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String processNewTeam(@ModelAttribute("newTeam") NewTeamForm newTeam) {
		System.out.println("New team name is: " + newTeam.getTeamName());
		return "redirect:/team/add";
	}
	
}
