package com.dave.fantasyfootball.repository;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.utils.Position;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

	public PlayerRepositoryImpl() {
	}

	public PlayerRepositoryImpl(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	@Override
	public List<Player> getAllPlayers() {
		return null;
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
			if (player.getPosition().equals(position)) {
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
	public List<Integer> getPlayersByTeam(int teamId) {
		return null;
	}

	@Override
	public void removePlayersFromTeam(int teamId) {
		String sql = "DELETE FROM player_t " + "WHERE team_id = :teamId";

		MapSqlParameterSource params = new MapSqlParameterSource("teamId",
				teamId);
		jdbcTemplate.update(sql, params);
	}

	@Override
	public Player getPlayerById(int id) throws JSONException, IOException {
		Player player = new Player();
		int minutesPlayed;

		JSONObject playerJson = retrievePlayerJson(id);

		JSONArray gameweekEvent = playerJson.getJSONArray("event_explain");
		for (int i = 0; i < gameweekEvent.length(); i++) {
			JSONArray eventArray = gameweekEvent.getJSONArray(i);
			if (eventArray.getString(0) == "Minutes played") {
				minutesPlayed = eventArray.getInt(2);
			}
		}
		
		player.setId(id);
		player.setFirstName(playerJson.getString("first_name"));
		player.setLastName(playerJson.getString("second_name"));
		player.setWebName(playerJson.getString("web_name"));
		player.setPosition(playerJson.getString("type_name"));
		player.setGameweekPoints(playerJson.getInt("event_total"));
		player.setGameweekEvent(gameweekEvent);
		player.setClub(playerJson.getString("team_name"));
		player.setImageFile(playerJson.getString("photo"));
		player.setMinutesPlayed();
		return player;
	}

	private JSONObject retrievePlayerJson(int id)
			throws JSONException, IOException {
		JSONObject playerJson;
		try {
			playerJson = new JSONObject(IOUtils.toString(new URL(
					"http://fantasy.premierleague.com/web/api/elements/" + id)
					.openStream()));
		} catch (IOException e) {
			try {
				playerJson = new JSONObject(IOUtils.toString(new URL(
						"http://fantasy.premierleague.com/web/api/elements/"
								+ id).openStream()));
			} catch (IOException e1) {
				try {
					playerJson = new JSONObject(IOUtils.toString(new URL(
							"http://fantasy.premierleague.com/web/api/elements/"
									+ id).openStream()));
				} catch (IOException e2) {
					e2.printStackTrace();
					throw new IOException("Could not connest to Fantasy Premier League whilst trying to retrieve player " + id);
				}
			}
		}
		return playerJson;
	}
}
