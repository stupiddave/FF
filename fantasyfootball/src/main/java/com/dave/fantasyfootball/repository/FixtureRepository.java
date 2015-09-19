package com.dave.fantasyfootball.repository;

import java.util.List;

import com.dave.fantasyfootball.domain.Fixture;

public interface FixtureRepository {

	void updateAllFixtures(List<Fixture> fixtures);

	List<Fixture> getFixturesByGameweek(int selectionGameweek);

}
