package com.dave.fantasyfootball.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

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
		String sql = "SELECT current_gameweek " + "FROM current_gameweek_t";

		try {
			Integer currentGameweek = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), new RowMapper<Integer>() {
				@Override
				public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
					int newGameweek = rs.getInt("current_gameweek");
					return newGameweek;
				}
			});
			return currentGameweek;
		} catch (EmptyResultDataAccessException e) {
			initializeGameweek();
		}
		return getCurrentGameweek();
	}

	@Override
	public void initializeGameweek() {
		String sql = "INSERT INTO current_gameweek_t (current_gameweek, updt_dtm)" + "values(1, CURRENT_TIMESTAMP)";
		jdbcTemplate.update(sql, new MapSqlParameterSource());
	}
}
