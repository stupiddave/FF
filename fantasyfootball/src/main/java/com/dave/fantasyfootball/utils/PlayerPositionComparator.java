package com.dave.fantasyfootball.utils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.dave.fantasyfootball.domain.Player;

public class PlayerPositionComparator implements Comparator<Player> {

	private static final Map<String, Integer> POSITION_MAP = createMap();

	@Override
	public int compare(Player player1, Player player2) {
		return POSITION_MAP.get(player1.getPosition())
				- POSITION_MAP.get(player2.getPosition());
	}

	private static Map<String, Integer> createMap() {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("Goalkeeper", 0);
		map.put("Defender", 1);
		map.put("Midfielder", 2);
		map.put("Forward", 3);
		return map;
	}
}
