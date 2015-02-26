package com.dave.fantasyfootball.service;

import java.util.List;

import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.SelectionFormPlayer;

public interface SelectionFormService {
	public List<SelectionFormPlayer> getSelectionFormPlayersFromTeam(Team team);
}
