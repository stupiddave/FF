package com.dave.fantasyfootball.repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.domain.Selection;
import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.form.TeamForm;
import com.dave.fantasyfootball.service.PlayerService;
import com.dave.fantasyfootball.utils.PlayerPositionComparator;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

	private static final String NO_TEAM_MSG = "No team available for id ";
	private static final Logger log = Logger.getLogger(TeamRepository.class);

	@Autowired
	public TeamRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate, PlayerService playerService) {
		this.jdbcTemplate = jdbcTemplate;
		this.playerService = playerService;
	}

	NamedParameterJdbcTemplate jdbcTemplate;
	PlayerService playerService;
	List<Team> teams = new ArrayList<Team>();

	RowMapper<Team> teamRowMapper = new RowMapper<Team>() {
		public Team mapRow(ResultSet rs, int rownum) throws SQLException {
			Team team = new Team();
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
				List<Integer> playerIds = new ArrayList<Integer>();
				for (Integer i = 1; i <= 18; i++) {
					playerIds.add(rs.getInt("player" + i + "_id"));
				}
				List<Player> players = playerService.getSquadPlayersByPlayerIdList(playerIds);
				lineup.addAll(players);
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
			selection.setLineup(lineup);

			return selection;
		}

	};

	@Override
	public Team getTeamInfoById(int id) {
		Team team;
		String teamSql = "SELECT team_id" + ", team_name" + ", team_total_points" + " FROM team_t"
				+ " WHERE team_id = :id";
		try {
			team = jdbcTemplate.queryForObject(teamSql, new MapSqlParameterSource("id", id), teamRowMapper);
		} catch (EmptyResultDataAccessException e) {
			System.out.println(NO_TEAM_MSG + id);
			team = null;
		}

		return team;
	}

	public List<Player> getPlayersByTeam(int teamId) {
		List<Player> players = new ArrayList<Player>();
		String teamPlayersSql = "SELECT info.id AS playerId, info.web_name AS webName, info.club, info.position FROM player_info_t info LEFT JOIN player_t player ON info.id = player.player_id WHERE player.team_id = :id";
		List<Map<String,Object>> rs = jdbcTemplate.queryForList(teamPlayersSql, new MapSqlParameterSource("id", teamId));
		for(Map<String,Object> row : rs) {
			Player player = new Player();
			player.setId((Integer) row.get("playerId"));
			player.setWebName((String) row.get("webName"));
			player.setPosition((String) row.get("position"));
			player.setClub((String) row.get("club"));
			players.add(player);
		}
		players.sort(new PlayerPositionComparator());
		return players;
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
		String teamSql = "SELECT team_id" + ", team_name" + ", team_total_points" + " FROM team_t"
				+ " WHERE team_id = :id";

		TeamForm teamForm = jdbcTemplate.queryForObject(teamSql, new MapSqlParameterSource("id", id),
				teamFormRowMapper);
		String teamPlayersSql = "SELECT player_id " + "FROM player_t " + "WHERE team_id = :id";

		List<String> playerIds = jdbcTemplate.queryForList(teamPlayersSql, new MapSqlParameterSource("id", id),
				String.class);
		teamForm.setPlayerIds(playerIds);
		return teamForm;
	}

	@Override
	public void updateTeam(Team team) {
		String sql = "UPDATE team_t " + "SET team_name = :teamName, " + "team_total_points = :teamTotalPoints, "
				+ "updt_dtm = CURRENT_TIMESTAMP " + "WHERE team_id = :id ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamName", team.getName());
		params.addValue("teamTotalPoints", team.getTotalPoints());
		params.addValue("id", team.getId());

		jdbcTemplate.update(sql, params);
	}

	@Override
	public Selection getTeamSelection(int teamId, int gameweek) {
		Selection selection = null;
		String sql = "SELECT selection_gameweek" + ", player1_id " + ", player2_id" + ", player3_id" + ", player4_id"
				+ ", player5_id" + ", player6_id" + ", player7_id" + ", player8_id" + ", player9_id" + ", player10_id"
				+ ", player11_id" + ", player12_id" + ", player13_id" + ", player14_id" + ", player15_id"
				+ ", player16_id" + ", player17_id" + ", player18_id" + ", captain_id" + ", vice_captain_id "
				+ "FROM selection_t " + "WHERE team_id = :teamId AND selection_gameweek <= :gameweek "
				+ "ORDER BY updt_dtm DESC LIMIT 1";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamId", teamId);
		params.addValue("gameweek", gameweek);

		try {
			selection = jdbcTemplate.queryForObject(sql, params, selectionRowMapper);
		} catch (EmptyResultDataAccessException e) {
			log.info(NO_TEAM_MSG + teamId);
		}
		return selection;
	}

	@Override
	public void addSelection(Selection selection, int selectionGameweek) {
		String sql = "INSERT INTO selection_t (team_id " + ", selection_gameweek" + ", player1_id" + ", player2_id"
				+ ", player3_id" + ", player4_id" + ", player5_id" + ", player6_id" + ", player7_id" + ", player8_id"
				+ ", player9_id" + ", player10_id" + ", player11_id" + ", player12_id" + ", player13_id"
				+ ", player14_id" + ", player15_id" + ", player16_id" + ", player17_id" + ", player18_id"
				+ ", captain_id" + ", vice_captain_id" + ", updt_dtm ) VALUES" + " (:team_id" + ", :selection_gameweek"
				+ ", :player1_id" + ", :player2_id" + ", :player3_id" + ", :player4_id" + ", :player5_id"
				+ ", :player6_id" + ", :player7_id" + ", :player8_id" + ", :player9_id" + ", :player10_id"
				+ ", :player11_id" + ", :player12_id" + ", :player13_id" + ", :player14_id" + ", :player15_id"
				+ ", :player16_id" + ", :player17_id" + ", :player18_id" + ", :captain_id" + ", :vice_captain_id"
				+ ", CURRENT_TIMESTAMP) ";

		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("team_id", selection.getTeamId());
		for (int i = 1; i < 19; i++) {
			params.addValue("player" + i + "_id", selection	.getLineup()
															.get(i - 1)
															.getId());
		}
		params.addValue("captain_id", selection.getCaptainId());
		params.addValue("vice_captain_id", selection.getViceCaptainId());
		params.addValue("selection_gameweek", selectionGameweek);

		int rowNum = jdbcTemplate.update(sql, params);
		System.out.println("Number of rows affected: " + rowNum);
	}

	@Override
	public void commitTeamScore(int teamId, int teamScore) {
		String sql = "UPDATE team_t SET team_total_points = team_total_points + :teamScore, team_gameweek_points = :teamScore WHERE team_id = :teamId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamId", teamId);
		params.addValue("teamScore", teamScore);
		jdbcTemplate.update(sql, params);
	}

	@Override
	public List<Team> getTeamStandings() {
		List<Team> teams = new ArrayList<Team>();
		String sql = "SELECT team.team_id AS id, team.team_name AS name, team.team_gameweek_points AS gameweek_points, "
				+ "team.team_total_points AS total_points, CONCAT(user.first_name, ' ', user.last_name) AS manager "
				+ "FROM team_t team INNER JOIN user_t user ON team.team_id = user.team_id ORDER BY team.team_total_points DESC";
		List<Map<String, Object>> rsMap = jdbcTemplate.queryForList(sql, new MapSqlParameterSource());
		for (Map<String, Object> row : rsMap) {
			Team team = new Team();
			team.setId((Integer) row.get("id"));
			team.setName((String) row.get("name"));
			team.setManager((String) row.get("manager"));
			team.setGameweekPoints((Integer) row.get("gameweek_points"));
			team.setTotalPoints((Integer) row.get("total_points"));
			teams.add(team);
		}
		return teams;
	}
}