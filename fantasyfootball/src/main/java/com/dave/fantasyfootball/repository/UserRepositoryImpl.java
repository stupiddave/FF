package com.dave.fantasyfootball.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dave.fantasyfootball.domain.User;

@Repository
public class UserRepositoryImpl implements UserRepository {

//	@Autowired
//	private User user;
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public UserRepositoryImpl(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	private RowMapper<User> userRowMapper = new RowMapper<User>() {
		
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setFirstName(rs.getString("first_name"));
			user.setLastName(rs.getString("last_name"));
			user.setEmail(rs.getString("email"));
			user.setTeamId(rs.getInt("team_id"));
			user.setUserType(rs.getString("user_type"));

			return user;
		}

	};

	@Override
	public void addUser(User user) {
		String sql = "INSERT INTO user_t (username	, password	"
				+ ", first_name	, last_name	, email	, team_id	"
				+ ", user_type	, updt_dtm	) VALUES (:username"
				+ ", :password, :firstName, :lastName, :email"
				+ ", :teamId, :userType, CURRENT_TIMESTAMP)";
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("username", user.getUsername());
		params.addValue("password", user.getPassword());
		params.addValue("firstName", user.getFirstName());
		params.addValue("lastName", user.getLastName());
		params.addValue("email", user.getEmail());
		params.addValue("teamId", user.getTeamId());
		params.addValue("userType", user.getUserType());

		jdbcTemplate.update(sql, params);
	}

	public User getUserByUsername(String username) {
		String sql = "SELECT username, password, first_name"
				+ ", last_name, email, team_id, user_type "
				+ "FROM user_t WHERE username = :username";

		User user = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(
				"username", username), userRowMapper);

		return user;
	}
}
