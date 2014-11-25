package com.dave.fantasyfootball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.utils.Position;

@Controller
public class PlayerController {

	@RequestMapping("/players")
	public String list(Model model) {
		Player player = new Player();
		player.setId(1);
		player.setFirstName("Davy");
		player.setLastName("Miller");
		player.setWebName("DeadlyDavy");
		player.setPosition(Position.DEFENDER);
		player.setClub("Liverpool");
		model.addAttribute("player", player);
		return "players";
	}
}
