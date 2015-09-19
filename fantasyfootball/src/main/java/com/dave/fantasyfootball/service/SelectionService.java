package com.dave.fantasyfootball.service;

import java.util.List;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;

public interface SelectionService {

	public void insertSelection(Selection selection);

	public List<Player> getLatestLineup(int teamId);
}
