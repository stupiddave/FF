package com.dave.fantasyfootball.service;

import java.util.List;

import com.dave.fantasyfootball.domain.Team;

public interface SelectionFormService {
	public List<Integer> getStartersFromTeam(Team team);

	public List<Integer> getSubsFromTeam(Team team);
}
