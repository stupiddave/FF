package com.dave.fantasyfootball.repository;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSourceUtils;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.EplTeam;
import com.dave.fantasyfootball.domain.Fixture;

@Repository
public class ReferenceRepositoryImpl implements ReferenceRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;
	// private Map<String, URL> teamBadgeMap;

	@Autowired
	public ReferenceRepositoryImpl(
			NamedParameterJdbcTemplate jdbcTemplate/*
													 * , Map<String, URL>
													 * teamBadgeMap
													 */) {
		this.jdbcTemplate = jdbcTemplate;
		// this.teamBadgeMap = teamBadgeMap;
	}

	@Override
	public void updateAllFixtures(List<Fixture> fixtures) {

		jdbcTemplate.update("TRUNCATE TABLE fixture_t", new MapSqlParameterSource());
		String insertSql = "INSERT INTO fixture_t(gameweek, kickoff, home_team, away_team) VALUES(:gameweek, :kickoff, :homeTeam, :awayTeam)";
		List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();
		for (Fixture fixture : fixtures) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("gameweek", fixture.getGameweek());
			params.put("kickoff", fixture.getKickoff());
			params.put("homeTeam", fixture	.getHomeTeam()
											.getName());
			params.put("awayTeam", fixture	.getAwayTeam()
											.getName());
			paramsList.add(params);
		}
		SqlParameterSource[] paramsBatch = SqlParameterSourceUtils.createBatch(getArrayData(paramsList));
		jdbcTemplate.batchUpdate(insertSql, paramsBatch);
	}

	private static Map<String, Object>[] getArrayData(List<Map<String, Object>> list) {
		@SuppressWarnings("unchecked")
		Map<String, Object>[] maps = new HashMap[list.size()];

		Iterator<Map<String, Object>> iterator = list.iterator();
		int i = 0;
		while (iterator.hasNext()) {
			Map<String, Object> map = (Map<String, Object>) iterator.next();
			maps[i++] = map;
		}

		return maps;
	}

	@Override
	public List<Fixture> getFixturesByGameweek(int selectionGameweek) throws MalformedURLException {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		String sql = "SELECT fixture.kickoff AS kickoff, fixture.home_team AS home_team, fixture.away_team AS away_team, "
				+ "homeTeam.badgeUrl AS home_badge, awayTeam.badgeUrl AS away_badge " + "FROM fixture_t fixture "
				+ "LEFT JOIN eplTeam_t homeTeam ON homeTeam.name = fixture.home_team "
				+ "LEFT JOIN eplTeam_t awayTeam ON awayTeam.name = fixture.away_team " + "WHERE gameweek = :gameweek";
		List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql,
				new MapSqlParameterSource("gameweek", selectionGameweek));
		for (Map<String, Object> rsRow : rs) {
			Date kickoff = (Date) rsRow.get("kickoff");
			String homeTeamName = (String) rsRow.get("home_team");
			String homeBadgeUrl = (String) rsRow.get("home_badge");
			String awayTeamName = (String) rsRow.get("away_team");
			String awayBadgeUrl = (String) rsRow.get("away_badge");
			EplTeam homeTeam = new EplTeam(homeTeamName, new URL(homeBadgeUrl));
			EplTeam awayTeam = new EplTeam(awayTeamName, new URL(awayBadgeUrl));
			Fixture fixture = new Fixture(kickoff, homeTeam, awayTeam, selectionGameweek);
			fixtures.add(fixture);
		}
		return fixtures;
	}

	@Override
	public void updateAllEplTeams(List<EplTeam> teams) {
		jdbcTemplate.update("TRUNCATE TABLE eplTeam_t", new MapSqlParameterSource());
		String insertSql = "INSERT INTO eplTeam_t(name, badgeUrl) VALUES(:name, :badgeUrl)";
		List<Map<String, Object>> paramsList = new ArrayList<Map<String, Object>>();
		for (EplTeam team : teams) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("name", team.getName());
			params.put("badgeUrl", team	.getBadgeUrl()
										.toString());
			paramsList.add(params);
		}
		SqlParameterSource[] paramsBatch = SqlParameterSourceUtils.createBatch(getArrayData(paramsList));
		jdbcTemplate.batchUpdate(insertSql, paramsBatch);

	}

}
