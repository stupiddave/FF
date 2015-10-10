package com.dave.fantasyfootball.repository;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.GameweekEvent;
import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.utils.Position;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

	private static final int TOTAL_PLAYERS = 560;

	public PlayerRepositoryImpl() {
	}

	public PlayerRepositoryImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Player> getAllPlayersDetail() {
		System.out.println("Getting all players...");
		List<Player> allPlayers = new ArrayList<Player>();
		for (int i = 1; i < TOTAL_PLAYERS; i++) {
			System.out.println("Getting player with id " + i);
			Player player = null;
			try {
				player = getPlayerDetail(i);
			} catch (Exception e) {
				System.err.println("Error getting player with id " + i);
				break;
			}
			if (player != null) {
				allPlayers.add(player);
			}
		}
		return allPlayers;
	}

	@Override
	public List<Player> getPlayersByClub(String club) {
		List<Player> playersByClub = new ArrayList<Player>();
		for (Player player : getAllPlayers()) {
			if (player.getClub() == club) {
				playersByClub.add(player);
			}
		}
		return playersByClub;
	}

	@Override
	public List<Player> getPlayersByPosition(Position position) {
		List<Player> playersByPosition = new ArrayList<Player>();
		for (Player player : getAllPlayers()) {
			if (player	.getPosition()
						.equals(position)) {
				playersByPosition.add(player);
			}
		}
		return playersByPosition;
	}

	@Override
	public void addPlayer(int playerId, int teamId) {
		String sql = "INSERT INTO player_t (player_id, team_id, updt_dtm) VALUES (:playerId, :teamId, CURRENT_TIMESTAMP)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamId", teamId);
		params.addValue("playerId", playerId);
		jdbcTemplate.update(sql, params);
	}

	@Override
	public void removePlayersFromTeam(int teamId) {
		String sql = "DELETE FROM player_t " + "WHERE team_id = :teamId";

		MapSqlParameterSource params = new MapSqlParameterSource("teamId", teamId);
		jdbcTemplate.update(sql, params);
	}

	@Override
//	@Cacheable("playerCache")
	public Player getPlayerById(int id) {

		String sql = "SELECT id, first_name, second_name, web_name, position, club FROM player_info_t WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", id);
		Player player = jdbcTemplate.queryForObject(sql, params, new RowMapper<Player>() {
			public Player mapRow(ResultSet rs, int rowNum) throws SQLException {
				Player player = new Player();
				player.setId(id);
				player.setFirstName(rs.getString("first_name"));
				player.setLastName(rs.getString("second_name"));
				player.setWebName(rs.getString("web_name"));
				player.setClub(rs.getString("club"));
				player.setPosition(rs.getString("position"));
				return player;
			}
		});
		return player;
	}

	@Override
	public List<Player> getPlayersByIdList(List<Integer> ids) {
		List<Player> players = new ArrayList<Player>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, first_name, second_name, web_name, position, club FROM player_info_t WHERE id IN (:ids) ");
		MapSqlParameterSource params = new MapSqlParameterSource();
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.toString(), Collections.singletonMap("ids", ids));
		for (Map<String, Object> row : rows) {
			Player player = new Player();
			player.setId((Integer) row.get("id"));
			player.setFirstName((String) row.get("first_name"));
			player.setLastName((String) row.get("second_name"));
			player.setWebName((String) row.get("web_name"));
			player.setClub((String) row.get("club"));
			player.setPosition((String) row.get("position"));
			players.add(player);
		}
		return players;
	}

	public Player getPlayerDetail(int id) throws JSONException, IOException {
		Player player = new Player();

		JSONObject playerJson = getPlayerJson(id);

		JSONArray gameweekEvent = playerJson.getJSONArray("event_explain");

		player.setId(id);
		player.setFirstName(playerJson.getString("first_name"));
		player.setLastName(playerJson.getString("second_name"));
		player.setWebName(playerJson.getString("web_name"));
		player.setPosition(playerJson.getString("type_name"));
		player.setClub(playerJson.getString("team_name"));
		player.setImageFile(playerJson.getString("photo"));
		player.setGameweekEvent(mapGameweekEvent(gameweekEvent));
		player.setMinutesPlayed();
		player.setGameweekPoints(playerJson.getInt("event_total"));
		player.setNextOpposition(playerJson.getString("current_fixture"));
		return player;
	}

	public JSONObject getPlayerJson(int id) throws JSONException, IOException {
		JSONObject playerJson;
		try {
			playerJson = new JSONObject(IOUtils.toString(
					new URL("http://fantasy.premierleague.com/web/api/elements/" + id).openStream(), "UTF-8"));
		} catch (IOException e) {
			try {
				playerJson = new JSONObject(IOUtils.toString(
						new URL("http://fantasy.premierleague.com/web/api/elements/" + id).openStream(), "UTF-8"));
			} catch (IOException e1) {
				try {
					playerJson = new JSONObject(IOUtils.toString(
							new URL("http://fantasy.premierleague.com/web/api/elements/" + id).openStream(), "UTF-8"));
				} catch (IOException e2) {
					e2.printStackTrace();
					throw new IOException(
							"Could not connect to Fantasy Premier League whilst trying to retrieve player " + id);
				}
			}
		}
		return playerJson;
	}

	@Override
	public void deleteAllPlayerInfo() {
		String sql = "DELETE FROM player_info_t";
		jdbcTemplate.update(sql, new MapSqlParameterSource());
	}

	@Override
	public void addPlayerInfo(Player player) {
		System.out.println("Adding player info for " + player.getWebName());
		String sql = "INSERT INTO player_info_t (id , first_name , second_name , web_name , position , club) VALUES (:id, :firstName, :secondName , :webName , :position , :club)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("id", player.getId());
		params.addValue("firstName", player.getFirstName());
		params.addValue("secondName", player.getLastName());
		params.addValue("webName", player.getWebName());
		params.addValue("position", player.getPosition());
		params.addValue("club", player.getClub());
		jdbcTemplate.update(sql, params);
	}

	@Override
	@Cacheable("allPlayerCache")
	public List<Player> getAllPlayers() {
		List<Player> playersInfo = new ArrayList<Player>();
		String sql = "SELECT id, first_name, second_name, web_name, position, club FROM player_info_t";
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, new MapSqlParameterSource());
		for (Map<String, Object> row : rows) {
			Player player = new Player();
			player.setId((Integer) row.get("id"));
			player.setFirstName((String) row.get("first_name"));
			player.setLastName((String) row.get("second_name"));
			player.setWebName((String) row.get("web_name"));
			player.setClub((String) row.get("club"));
			player.setPosition((String) row.get("position"));
			playersInfo.add(player);
		}
		return playersInfo;
	}

	@Override
	public Player getPlayerDetail(Player player) throws JSONException, IOException {
		JSONObject playerJson = getPlayerJson(player.getId());
		player.setGameweekEvent(mapGameweekEvent(playerJson.getJSONArray("event_explain")));
		player.setMinutesPlayed();
		player.setGameweekPoints(playerJson.getInt("event_total"));
		player.setNextOpposition(playerJson.getString("current_fixture"));
		return player;
	}

	private List<GameweekEvent> mapGameweekEvent(JSONArray eventExplain) {
		List<GameweekEvent> gameweekEvents = new ArrayList<GameweekEvent>();
		for (int i = 0; i < eventExplain.length(); i++) {
			JSONArray eventItem = eventExplain.getJSONArray(i);
			GameweekEvent gameweekEvent = new GameweekEvent(eventItem.getString(0), eventItem.getInt(1),
					eventItem.getInt(2));
			gameweekEvents.add(gameweekEvent);
		}
		return gameweekEvents;
	}

	@Override
	public List<Player> getPlayersByTeamId(int teamId) {
		List<Player> playersInfo = new ArrayList<Player>();
		String sql = "SELECT id, first_name, second_name, web_name, position, club FROM player_info_t pi INNER JOIN player_t p ON p.player_id = pi.id WHERE p.team_id = :teamId";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("teamId", teamId);
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, params);
		for (Map<String, Object> row : rows) {
			Player player = new Player();
			player.setId((Integer) row.get("id"));
			player.setFirstName((String) row.get("first_name"));
			player.setLastName((String) row.get("second_name"));
			player.setWebName((String) row.get("web_name"));
			player.setClub((String) row.get("club"));
			player.setPosition((String) row.get("position"));
			playersInfo.add(player);
		}
		return playersInfo;
	}
}
