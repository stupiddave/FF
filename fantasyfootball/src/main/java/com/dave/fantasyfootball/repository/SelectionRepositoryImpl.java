//package com.dave.fantasyfootball.repository;
//
//import java.util.List;
//
//import javax.sql.DataSource;
//
//import org.springframework.jdbc.core.JdbcTemplate;
//
//import com.dave.fantasyfootball.domain.Player;
//import com.dave.fantasyfootball.domain.Selection;
//import com.dave.fantasyfootball.utils.SquadListing;
//
//public class SelectionRepositoryImpl implements SelectionRepository {
//
//	private DataSource dataSource;
//	private JdbcTemplate jdbcTemplateObject;
//
//	public void setDataSource(DataSource dataSource) {
//		this.dataSource = dataSource;
//		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
//	}
//
////	@Override
////	public void insertSelection(Selection selection, int selectionGameweek) {
////		StringBuffer sb = new StringBuffer();
////		sb.append("INSERT INTO selection_t(team_id, selection_gameweek,");
////		for (int i = 1; i <= 3; i++) {
////			sb.append("player" + i + "_id, ");
////		}
////		sb.append("captain_id, vice_captain_id, updt_dtm VALUES (?,?,?,?,?,CURRENT_TIMESTAMP)");
//////		Connection conn = null;
////		//			conn = dataSource.getConnection();
//////			PreparedStatement ps = conn.prepareStatement();
////		jdbcTemplateObject.update(sb.toString(), selection.getTeamId(), selectionGameweek, 
////				selection.getSquad().get(SquadListing.PLAYER1),
////		selection.getSquad().get(SquadListing.PLAYER2), selection.getSquad().get(SquadListing.PLAYER3));
//////		selection.getPlayer4().getId(), selection.getPlayer5().getId(),
//////		selection.getPlayer6().getId(), selection.getPlayer7().getId(),
//////		selection.getPlayer8().getId(), selection.getPlayer9().getId(),
//////		selection.getPlayer10().getId(), selection.getPlayer11().getId(),
//////		selection.getPlayer12().getId(), selection.getPlayer13().getId(),
//////		selection.getPlayer14().getId(), selection.getPlayer15().getId(),
//////		selection.getPlayer16().getId(), selection.getPlayer17().getId(),
//////		selection.getPlayer18().getId(), selection.getCaptain().getId(),
//////		selection.getViceCaptain().getId());
////	}
//
//	@Override
//	public List<Player> getLatestLineup(int teamId) {
//		
//		return null	;
//	}
//
//}
