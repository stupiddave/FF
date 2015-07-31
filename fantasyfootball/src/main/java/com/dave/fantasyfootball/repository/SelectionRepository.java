package com.dave.fantasyfootball.repository;

import java.util.List;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;

public interface SelectionRepository {

	public void insertSelection(Selection selection, int selectionGameweek);
	public List<Player> getLatestLineup(int teamId);
}
