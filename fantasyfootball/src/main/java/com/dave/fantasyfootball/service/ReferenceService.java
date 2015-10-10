package com.dave.fantasyfootball.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.util.List;

import com.dave.fantasyfootball.domain.Fixture;

public interface ReferenceService {

	void updateAllFixtures() throws IOException, ParseException;

	List<Fixture> getFixturesByGameweek(int selectionGameweek) throws MalformedURLException;

	void updateAllEplTeams() throws IOException, ParseException;

}
