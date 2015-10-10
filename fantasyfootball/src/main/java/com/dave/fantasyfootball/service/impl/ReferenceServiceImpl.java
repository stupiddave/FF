package com.dave.fantasyfootball.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.EplTeam;
import com.dave.fantasyfootball.domain.Fixture;
import com.dave.fantasyfootball.repository.ReferenceRepository;
import com.dave.fantasyfootball.service.ReferenceService;

@Service
public class ReferenceServiceImpl implements ReferenceService {

	private static final int TOTAL_GAMEWEEKS = 38;
	private static final Logger log = Logger.getLogger(ReferenceService.class);
	private ReferenceRepository referenceRepository;

	@Autowired
	public ReferenceServiceImpl(ReferenceRepository referenceRepository) {
		this.referenceRepository = referenceRepository;
	}

	@Override
	public void updateAllFixtures() throws IOException, ParseException {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		for (int i = 1; i < TOTAL_GAMEWEEKS + 1; i++) {
			log.info("Retrieving fixtures for week " + i + "...");
			Document doc = Jsoup.connect("http://fantasy.premierleague.com/fixtures/" + i)
								.timeout(0)
								.get();
			Elements fixtureTableRows = doc.select("#ismFixtureTable .ismFixture");
			for (Element fixtureTableRow : fixtureTableRows) {
				String homeBadge = fixtureTableRow	.select("td")
													.get(2)
													.select("img")
													.get(0)
													.attr("src");
				String awayBadge = fixtureTableRow	.select("td")
													.get(4)
													.select("img")
													.get(0)
													.attr("src");
				EplTeam homeTeam = new EplTeam(fixtureTableRow	.select(".ismHomeTeam")
																.text(),
						new URL(homeBadge));
				EplTeam awayTeam = new EplTeam(fixtureTableRow	.select(".ismAwayTeam")
																.text(),
						new URL(awayBadge));
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM HH:mm");
				Date kickoff = dateFormat.parse(fixtureTableRow	.select("td")
																.first()
																.text());
				int gameweek = i;
				Fixture fixture = new Fixture(kickoff, homeTeam, awayTeam, gameweek);
				fixtures.add(fixture);
			}
		}
		referenceRepository.updateAllFixtures(fixtures);
	}

	@Override
	public List<Fixture> getFixturesByGameweek(int selectionGameweek) throws MalformedURLException {
		return referenceRepository.getFixturesByGameweek(selectionGameweek);
	}

	@Override
	public void updateAllEplTeams() throws IOException, ParseException {
		Document doc = Jsoup.connect("http://fantasy.premierleague.com/fixtures/1")
							.timeout(0)
							.get();
		Elements fixtureTableRows = doc.select("#ismFixtureTable .ismFixture");
		List<EplTeam> teams = new ArrayList<EplTeam>();
		for (Element fixtureTableRow : fixtureTableRows) {
			String homeBadge = fixtureTableRow	.select("td")
												.get(2)
												.select("img")
												.get(0)
												.attr("src");
			String awayBadge = fixtureTableRow	.select("td")
												.get(4)
												.select("img")
												.get(0)
												.attr("src");
			EplTeam homeTeam = new EplTeam(fixtureTableRow	.select(".ismHomeTeam")
															.text(),
					new URL(homeBadge));
			EplTeam awayTeam = new EplTeam(fixtureTableRow	.select(".ismAwayTeam")
															.text(),
					new URL(awayBadge));
			teams.add(homeTeam);
			teams.add(awayTeam);
		}
		referenceRepository.updateAllEplTeams(teams);
	}
}
