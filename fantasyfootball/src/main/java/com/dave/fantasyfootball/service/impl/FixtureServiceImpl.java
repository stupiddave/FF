package com.dave.fantasyfootball.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.EplTeam;
import com.dave.fantasyfootball.domain.Fixture;
import com.dave.fantasyfootball.repository.FixtureRepository;
import com.dave.fantasyfootball.service.FixtureService;

@Service
public class FixtureServiceImpl implements FixtureService {

	private static final int TOTAL_GAMEWEEKS = 38;
	private FixtureRepository fixtureRepository;

	@Autowired
	public FixtureServiceImpl(FixtureRepository fixtureRepository) {
		this.fixtureRepository = fixtureRepository;
	}

	@Override
	public void updateAllFixtures() throws IOException, ParseException {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		for (int i = 1; i < TOTAL_GAMEWEEKS + 1; i++) {
			Document doc = Jsoup.connect("http://fantasy.premierleague.com/fixtures/" + i)
								.get();
			Elements fixtureTableRows = doc.select("#ismFixtureTable .ismFixture");
			for (Element fixtureTableRow : fixtureTableRows) {
				EplTeam homeTeam = new EplTeam(fixtureTableRow	.select(".ismHomeTeam")
																.text());
				EplTeam awayTeam = new EplTeam(fixtureTableRow	.select(".ismAwayTeam")
																.text());
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm");
				Date kickoff = dateFormat.parse(fixtureTableRow	.select("td")
																	.first()
																	.text());
				int gameweek = i;
				Fixture fixture = new Fixture(kickoff, homeTeam, awayTeam, gameweek);
				fixtures.add(fixture);
			}
		}
		fixtureRepository.updateAllFixtures(fixtures);
	}

	@Override
	public List<Fixture> getFixturesByGameweek(int selectionGameweek) {
		return fixtureRepository.getFixturesByGameweek(selectionGameweek);
	}
}
