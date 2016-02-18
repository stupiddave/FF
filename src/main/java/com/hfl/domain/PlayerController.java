package com.hfl.domain;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class PlayerController {

	@RequestMapping(path="/players/{id}", method=RequestMethod.GET)
	public Player getPlayer(@PathVariable("id") int id) {
		RestTemplate restTemplate = new RestTemplate();
		Player player = restTemplate.getForObject("http://fantasy.premierleague.com/web/api/elements/" + id, Player.class);
		return player;
	}
}
