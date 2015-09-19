package com.dave.fantasyfootball.repository;

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
public class FixtureRepositoryImpl implements FixtureRepository {

	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public FixtureRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
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
			params.put("homeTeam", fixture.getHomeTeam().getName());
			params.put("awayTeam", fixture.getAwayTeam().getName());
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
	public List<Fixture> getFixturesByGameweek(int selectionGameweek) {
		List<Fixture> fixtures = new ArrayList<Fixture>();
		String sql = "SELECT kickoff, home_team, away_team FROM fixture_t WHERE gameweek = :gameweek";
		List<Map<String, Object>> rs = jdbcTemplate.queryForList(sql,
				new MapSqlParameterSource("gameweek", selectionGameweek));
		for (Map<String, Object> rsRow : rs) {
			Date kickoff = (Date) rsRow.get("kickoff");
			EplTeam homeTeam = new EplTeam((String) rsRow.get("home_team"));
			EplTeam awayTeam = new EplTeam((String) rsRow.get("away_team"));
			Fixture fixture = new Fixture(kickoff, homeTeam, awayTeam, selectionGameweek);
			fixtures.add(fixture);
		}
		return fixtures;
	}

}
