package com.dave.fantasyfootball.repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.form.SelectionFormPlayer;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.service.PlayerService;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

	@Autowired
	public TeamRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, PlayerService playerService) {
		this.jdbcTemplate = jdbcTemplate;
		this.playerService = playerService;
	}

	NamedParameterJdbcTemplate jdbcTemplate;
	PlayerService playerService;
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
			
			
			List<Player> lineup = new ArrayList<Player>();
			try {
			lineup.add(playerService.getPlayerById(rs.getInt("player1_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player2_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player3_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player4_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player5_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player6_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player7_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player8_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player9_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player10_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player11_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player12_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player13_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player14_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player15_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player16_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player17_id")));
			lineup.add(playerService.getPlayerById(rs.getInt("player18_id")));
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
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
		String teamSql = "SELECT team_id" + ", team_name"
				+ ", team_total_points" + " FROM team_t"
				+ " WHERE team_id = :id";

		TeamForm teamForm = jdbcTemplate.queryForObject(teamSql, new MapSqlParameterSource("id", id),
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
				+ "ORDER BY updt_dtm DESC LIMIT 1";
		
		MapSqlParameterSource params = new MapSqlParameterSource("teamId", teamId);
		
		return jdbcTemplate.queryForObject(sql, params, selectionRowMapper);
	}

	@Override
	public void addSelection(SelectionForm selectionForm, int teamId) {
		String sql = "INSERT INTO selection_t (team_id "
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
		for (int i = 0; i < 18; i++) {
			SelectionFormPlayer currentPlayer = null;
			for(SelectionFormPlayer player : selectionForm.getLineup()) {
				if (player.getIsStarter()) {
					params.addValue("player" + i + "_id", player.getId());
					currentPlayer = player;
				}
			} 
			selectionForm.getLineup().remove(currentPlayer);
		}
		params.addValue("captain_id", selectionForm.getCaptain().getId());
		params.addValue("vice_captain_id", selectionForm.getViceCaptain().getId());
		
		int rowNum = jdbcTemplate.update(sql, params);
		System.out.println("Number of rows affected: " + rowNum);
	}
}