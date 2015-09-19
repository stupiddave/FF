package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import com.dave.fantasyfootball.domain.Fixture;

public interface FixtureService {

	void updateAllFixtures() throws IOException, ParseException;

	List<Fixture> getFixturesByGameweek(int selectionGameweek);

}
