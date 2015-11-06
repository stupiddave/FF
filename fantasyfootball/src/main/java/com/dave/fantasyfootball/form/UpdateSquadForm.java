package com.dave.fantasyfootball.form;

import java.util.List;

public class UpdateSquadForm {

	Integer teamId;
	List<Integer> squadIds;

	public Integer getTeamId() {
		return teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public List<Integer> getSquadIds() {
		return squadIds;
	}

	public void setSquadIds(List<Integer> squadIds) {
		this.squadIds = squadIds;
	}
}
