package com.dave.fantasyfootball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dave.fantasyfootball.domain.Player;

@Controller
public class PlayerController {

	@RequestMapping("/players")
	public String list(Model model) {
		Player player = new Player();
		player.setId(1);
		player.setFirstName("Davy");
		player.setLastName("Miller");
		player.setWebName("DeadlyDavy");
		player.setPosition("Defender");
		player.setClub("Liverpool");
		model.addAttribute("player", player);
		return "players";
	}
}
