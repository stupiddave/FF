package com.dave.fantasyfootball.form;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.service.SelectionFormService;

public class SelectionForm {

	SelectionFormService selectionFormPlayerService;

	private List<SelectionFormPlayer> lineup = new ArrayList<SelectionFormPlayer>();
	private Player captain;
	private Player viceCaptain;

	public SelectionForm(Team team, SelectionFormService selectionFormPlayerService) {
		this.selectionFormPlayerService = selectionFormPlayerService;
		this.lineup = selectionFormPlayerService.getSelectionFormPlayersFromTeam(team);
	}

	public List<SelectionFormPlayer> getLineup() {
		return lineup;
	}

	public void setLineup(List<SelectionFormPlayer> lineup) {
		this.lineup = lineup;
	}

	public Player getCaptain() {
		return captain;
	}

	public void setCaptain(Player captain) {
		this.captain = captain;
	}

	public Player getViceCaptain() {
		return viceCaptain;
	}

	public void setViceCaptain(Player viceCaptain) {
		this.viceCaptain = viceCaptain;
	}
}
