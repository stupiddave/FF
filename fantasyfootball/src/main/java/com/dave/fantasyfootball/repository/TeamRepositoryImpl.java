package com.dave.fantasyfootball.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.TeamForm;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

	@Autowired
	public TeamRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	NamedParameterJdbcTemplate jdbcTemplate;
	List<Team> teams = new ArrayList<Team>();

	RowMapper<Team> teamRowMapper = new RowMapper<Team>() {
		Team team = new Team();

		public Team mapRow(ResultSet rs, int rownum) throws SQLException {
			team.setId(rs.getInt("team_id"));
			team.setName(rs.getString("team_name"));
			team.setTotalPoints(rs.getInt("team_total_points"));
			return team;
		}
	};

	RowMapper<TeamForm> teamFormRowMapper = new RowMapper<TeamForm>() {
		public TeamForm mapRow(ResultSet rs, int rownum) throws SQLException {
			TeamForm teamForm = new TeamForm();
			teamForm.setTeamId(rs.getInt("team_id"));
			teamForm.setTeamName(rs.getString("team_name"));
			teamForm.setTeamPoints(rs.getInt("team_total_points"));
			return teamForm;
		}
	};

	RowMapper<Selection> selectionRowMapper = new RowMapper<Selection>() {

		@Override
		public Selection mapRow(ResultSet rs, int rowNum) throws SQLException {
			Selection selection = new Selection();
			selection.setSelectionGameweek(rs.getInt("selection_gameweek"));
			selection.setCaptainId(rs.getInt("captain_id"));
			selection.setViceCaptainId(rs.getInt("vice_captain_id"));
			ArrayList<String> lineup = new ArrayList<String>();
			lineup.add(rs.getString("player1_id"));
			lineup.add(rs.getString("player2_id"));
			lineup.add(rs.getString("player3_id"));
			lineup.add(rs.getString("player4_id"));
			lineup.add(rs.getString("player5_id"));
			lineup.add(rs.getString("player6_id"));
			lineup.add(rs.getString("player7_id"));
			lineup.add(rs.getString("player8_id"));
			lineup.add(rs.getString("player9_id"));
			lineup.add(rs.getString("player10_id"));
			lineup.add(rs.getString("player11_id"));
			lineup.add(rs.getString("player12_id"));
			lineup.add(rs.getString("player13_id"));
			lineup.add(rs.getString("player14_id"));
			lineup.add(rs.getString("player15_id"));
			lineup.add(rs.getString("player16_id"));
			lineup.add(rs.getString("player17_id"));
			lineup.add(rs.getString("player18_id"));
			selection.setLineup(lineup);
			
			return selection;
		}

	};

	@Override
	public Team getTeamInfoById(int id) {
		String teamSql = "SELECT team_id" + ", team_name"
				+ ", team_total_points" + " FROM team_t"
				+ " WHERE team_id = :id";

		Team team = jdbcTemplate.queryForObject(teamSql,
				new MapSqlParameterSource("id", id), teamRowMapper);

		return team;
	}

	public List<Integer> getPlayerIdsByTeam(int teamId) {
		String teamPlayersSql = "SELECT player_id " + "FROM player_t "
				+ "WHERE team_id = :id";

		return jdbcTemplate.queryForList(teamPlayersSql, new MapSqlParameterSource(
				"id", teamId), Integer.class);
	}

	@Override
	public void addTeam(Team team) {
		String sql = "INSERT INTO team_t(team_id, team_name,"
				+ " team_total_points, updt_dtm) VALUES (:teamId, :teamName, :teamTotalPoints, CURRENT_TIMESTAMP)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamId", team.getId());
		params.addValue("teamName", team.getName());
		params.addValue("teamTotalPoints", team.getTotalPoints());
		jdbcTemplate.update(sql, params);
	}

	@Override
	public TeamForm getTeamFormById(int id) {
		TeamForm teamForm = new TeamForm();
		String teamSql = "SELECT team_id" + ", team_name"
				+ ", team_total_points" + " FROM team_t"
				+ " WHERE team_id = :id";

		jdbcTemplate.query(teamSql, new MapSqlParameterSource("id", id),
				teamFormRowMapper);
		String teamPlayersSql = "SELECT player_id " + "FROM player_t "
				+ "WHERE team_id = :id";

		List<String> playerIds = jdbcTemplate.queryForList(teamPlayersSql,
				new MapSqlParameterSource("id", id), String.class);
		teamForm.setPlayerIds(playerIds);
		return teamForm;
	}

	@Override
	public void updateTeam(Team team) {
		String sql = "UPDATE team_t " + "SET team_name = :teamName, "
				+ "team_total_points = :teamTotalPoints, "
				+ "updt_dtm = CURRENT_TIMESTAMP " + "WHERE team_id = :id ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamName", team.getName());
		params.addValue("teamTotalPoints", team.getTotalPoints());
		params.addValue("id", team.getId());

		jdbcTemplate.update(sql, params);
	}

	@Override
	public Selection getTeamSelection(int teamId) {
		String sql = "SELECT selection_gameweek"
				+ ", player1_id "
				+ ", player2_id"
				+ ", player3_id"
				+ ", player4_id"
				+ ", player5_id"
				+ ", player6_id"
				+ ", player7_id"
				+ ", player8_id"
				+ ", player9_id"
				+ ", player10_id"
				+ ", player11_id"
				+ ", player12_id"
				+ ", player13_id"
				+ ", player14_id"
				+ ", player15_id"
				+ ", player16_id"
				+ ", player17_id"
				+ ", player18_id"
				+ ", captain_id"
				+ ", vice_captain_id "
				+ "FROM selection_t "
				+ "WHERE team_id = :teamId "
				+ "ORDER BY updt_dtm DESC FETCH FIRST ROW ONLY";
		
		MapSqlParameterSource params = new MapSqlParameterSource("teamId", teamId);
		
		return jdbcTemplate.queryForObject(sql, params, selectionRowMapper);
	}

	@Override
	public void addSelection(Selection selection, int teamId) {
		String sql = "INSERT INTO selection_t (team_id "
				+ ", selection_gameweek"
				+ ", player1_id"
				+ ", player2_id"
				+ ", player3_id"
				+ ", player4_id"
				+ ", player5_id"
				+ ", player6_id"
				+ ", player7_id"
				+ ", player8_id"
				+ ", player9_id"
				+ ", player10_id"
				+ ", player11_id"
				+ ", player12_id"
				+ ", player13_id"
				+ ", player14_id"
				+ ", player15_id"
				+ ", player16_id"
				+ ", player17_id"
				+ ", player18_id"
				+ ", captain_id"
				+ ", vice_captain_id"
				+ ", updt_dtm ) VALUES"
				+ " (:team_id"
				+ ", :selection_gameweek"
				+ ", :player1_id"
				+ ", :player2_id"
				+ ", :player3_id"
				+ ", :player4_id"
				+ ", :player5_id"
				+ ", :player6_id"
				+ ", :player7_id"
				+ ", :player8_id"
				+ ", :player9_id"
				+ ", :player10_id"
				+ ", :player11_id"
				+ ", :player12_id"
				+ ", :player13_id"
				+ ", :player14_id"
				+ ", :player15_id"
				+ ", :player16_id"
				+ ", :player17_id"
				+ ", :player18_id"
				+ ", :captain_id"
				+ ", :vice_captain_id"
				+ ", CURRENT_TIMESTAMP) ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("team_id", teamId);
		params.addValue("selection_gameweek", selection.getSelectionGameweek());
		for(Integer i = 0; i < selection.getLineup().size(); i++) {
			int j = i + 1;
			params.addValue("player" + j + "_id", selection.getLineup().get(i));
		}
		params.addValue("captain_id", selection.getCaptainId());
		params.addValue("vice_captain_id", selection.getViceCaptainId());
		
		int rowNum = jdbcTemplate.update(sql, params);
		System.out.println("Number of rows affected: " + rowNum);
	}
}