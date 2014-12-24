//package com.dave.fantasyfootball.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
//import com.dave.fantasyfootball.domain.Selection;
//import com.dave.fantasyfootball.domain.Team;
//import com.dave.fantasyfootball.service.SelectionServiceImpl;
//import com.dave.fantasyfootball.service.TeamService;
//
//
//@RequestMapping("/selection")
//@Controller
//public class SelectionController {
//
//	private TeamService teamService;
//	
//	@Autowired
//	public SelectionController(TeamService teamService) {
//		this.teamService = teamService;
//	}
//	
//	@RequestMapping(value="/submit-team/{teamId}", method=RequestMethod.GET)
//	public String getSelectionForm(@PathVariable("teamId") int teamId, Model model) {
//		
//		Team team = teamService.getTeamById(teamId);
//		team.getSelection();
//		model.addAttribute(team);
//		
////		PlayerRepository playerRepo = new PlayerRepositoryImpl();
////		model.addAttribute("teamPlayers", playerRepo.getAllPlayers());
//
//		Selection selection = new Selection();
////		selection.setTeamId(teamId);
//		model.addAttribute("selection", selection);
//		return "selection";
//		
//	}
//
//	@RequestMapping(value="/submit-team", method=RequestMethod.POST)
//	public String processSelection(Selection selection) {
//		SelectionServiceImpl selectionService = new SelectionServiceImpl();
//		selectionService.insertSelection(selection);
//		return "redirect:/";
//		
//	}
////	private List<Player> getTeamPlayers(int teamId) {
////		PlayerRepositoryImpl playerRepo = new PlayerRepositoryImpl()
////		List<Player> players = new ArrayList<Player>();
////	}
//}
