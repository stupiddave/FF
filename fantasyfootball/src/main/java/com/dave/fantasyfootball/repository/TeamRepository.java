package com.dave.fantasyfootball.repository;

import com.dave.fantasyfootball.domain.Team;

public interface TeamRepository {

	Team getTeamById(int id);
}
