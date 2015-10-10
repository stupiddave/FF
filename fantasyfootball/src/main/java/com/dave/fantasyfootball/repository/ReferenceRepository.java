package com.dave.fantasyfootball.repository;

import java.net.MalformedURLException;
import java.util.List;

import com.dave.fantasyfootball.domain.EplTeam;
import com.dave.fantasyfootball.domain.Fixture;

public interface ReferenceRepository {

	void updateAllFixtures(List<Fixture> fixtures);

	List<Fixture> getFixturesByGameweek(int selectionGameweek) throws MalformedURLException;

	void updateAllEplTeams(List<EplTeam> teams);

}
