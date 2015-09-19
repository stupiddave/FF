package com.dave.fantasyfootball.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Gameweek;
import com.dave.fantasyfootball.service.PropertiesRepository;

@Repository
public class PropertiesRepositoryImpl implements PropertiesRepository {

	NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public PropertiesRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public int incrementGameweek() {

		String sql = "UPDATE current_gameweek_t " + "SET current_gameweek = current_gameweek + 1"
				+ ", updt_dtm = CURRENT_TIMESTAMP";
		jdbcTemplate.update(sql, new MapSqlParameterSource());

		return getCurrentGameweek();
	}

	@Override
	public int decrementGameweek() {

		String sql = "UPDATE current_gameweek_t " + "SET current_gameweek = current_gameweek - 1"
				+ ", updt_dtm = CURRENT_TIMESTAMP";
		jdbcTemplate.update(sql, new MapSqlParameterSource());
		return getCurrentGameweek();
	}

	@Override
	public int getCurrentGameweek() {
		String sql = "SELECT current_gameweek " + "FROM current_gameweek_v";
		Integer currentGameweek = 1;

		try {
			currentGameweek = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					int currentGameweek = rs.getInt("current_gameweek");
					return currentGameweek;
				}
			});
		} catch (EmptyResultDataAccessException e) {
			// do nothing
		}
		return currentGameweek;
	}

	@Override
	public void initializeGameweek() {
		String sql = "INSERT INTO current_gameweek_t (current_gameweek, updt_dtm)" + "values(1, CURRENT_TIMESTAMP)";
		jdbcTemplate.update(sql, new MapSqlParameterSource());
	}

	@Override
	public void addGameweek(Gameweek gameweek) {
		String sql = "INSERT INTO gameweek_t(gameweek, start_dtm, end_dtm, updt_dtm) VALUES (:gameweek, :start_dtm, :end_dtm, CURRENT_TIMESTAMP)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("gameweek", gameweek.getGameweek());
		params.addValue("start_dtm", gameweek.getStart());
		params.addValue("end_dtm", gameweek.getEnd());
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Gameweek getLastGameweek() {
		String sql = "SELECT gameweek, start_dtm, end_dtm FROM gameweek_t ORDER BY updt_dtm DESC LIMIT 1";
		Gameweek lastGameweek = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(),
				new RowMapper<Gameweek>() {
					@Override
					public Gameweek mapRow(ResultSet rs, int rowNum) throws SQLException {
						int gameweek = rs.getInt("gameweek");
						Timestamp start = rs.getTimestamp("start_dtm");
						Timestamp end = rs.getTimestamp("end_dtm");
						return new Gameweek(gameweek, start, end);
					}

				});
		return lastGameweek;
	}
}
