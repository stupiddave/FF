package com.dave.fantasyfootball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.repository.PlayerRepository;
import com.dave.fantasyfootball.repository.PlayerRepositoryImpl;
import com.dave.fantasyfootball.service.SelectionServiceImpl;

@RequestMapping("/selection")
@Controller
public class SelectionController {

	@RequestMapping(value="/submit-team", method=RequestMethod.GET)
	public String getSelectionForm(Model model) {
		int teamId = 1;
		
		Team team = new Team();
		team.setName("How I Met Your Mata");
		model.addAttribute(team);
		
		PlayerRepository playerRepo = new PlayerRepositoryImpl();
		model.addAttribute("teamPlayers", playerRepo.getAllPlayers());

		Selection selection = new Selection();
		selection.setTeamId(teamId);
		model.addAttribute("selection", selection);
		return "selection";
		
	}

	@RequestMapping(value="/submit-team", method=RequestMethod.POST)
	public String processSelection(Selection selection) {
		SelectionServiceImpl selectionService = new SelectionServiceImpl();
		selectionService.insertSelection(selection);
		return "redirect:/";
		
	}
//	private List<Player> getTeamPlayers(int teamId) {
//		PlayerRepositoryImpl playerRepo = new PlayerRepositoryImpl()
//		List<Player> players = new ArrayList<Player>();
//	}
}
