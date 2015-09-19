package com.dave.fantasyfootball.domain;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MatchdayTeam {

	public MatchdayTeam(int teamId, List<Player> starters, List<Player> subs) {
		this.teamId = teamId;
		this.starters = starters;
		this.subs = subs;
	}

	private int teamId;
	private List<Player> starters;
	private List<Player> subs;
	private Player captain;
	private List<Player> replacedStarters;

	public int getTeamId() {
		return teamId;
	}

	public void setTeamId(int teamId) {
		this.teamId = teamId;
	}

	public List<Player> getStarters() {
		return starters;
	}

	public void setStarters(List<Player> starters) {
		this.starters = starters;
	}

	public List<Player> getSubs() {
		return subs;
	}

	public void setSubs(List<Player> subs) {
		this.subs = subs;
	}

	public Player getCaptain() {
		return captain;
	}

	public void setCaptain(Player captain) {
		this.captain = captain;
	}

	public List<Player> getReplacedStarters() {
		return replacedStarters;
	}

	public void setReplacedStarters(List<Player> replacedStarters) {
		this.replacedStarters = replacedStarters;
	}

	public void makeSubstitutions() {
		setReplacedStarters(new ArrayList<Player>());
		List<Player> starters = getStarters();
		List<Player> subs = getSubs();

		for (Player player : starters) {
			if (player.getMinutesPlayed() == 0) {
				swapIfPossible(subs, player);
			}
		}
	}

	public Player swapIfPossible(List<Player> subs, Player player) {
		if ("Goalkeeper".equals(player.getPosition())) {
			return swapGoalkeeperIfPossible(player);
		} else {
			return swapOutfieldPlayerIfPossible(player);
		}

	}

	Player swapOutfieldPlayerIfPossible(Player player) {
		for (Iterator<Player> subsIterator = subs.iterator(); subsIterator.hasNext();) {
			Player subPlayer = subsIterator.next();
			int currentPosition = starters.indexOf(player);
			if (subPlayer.getMinutesPlayed() > 0) {
				starters.set(currentPosition, subPlayer);
				if (isValidFormation()) {
					subsIterator.remove();
					replacedStarters.add(player);
					return subPlayer;
				} else {
					starters.set(currentPosition, player);
				}
			}
		}
		return player;
	}

	Player swapGoalkeeperIfPossible(Player player) {
		for (Iterator<Player> subsIterator = subs.iterator(); subsIterator.hasNext();) {
			Player subPlayer = subsIterator.next();
			if ("Goalkeeper".equals(subPlayer.getPosition()) && subPlayer.getMinutesPlayed() > 0) {
				starters.set(starters.indexOf(player), subPlayer);
				subsIterator.remove();
				replacedStarters.add(player);
				return subPlayer;
			}
		}
		return player;
	}

	private boolean isValidFormation() {
		int goalkeeperCount = 0;
		int defenderCount = 0;
		int midfielderCount = 0;
		int forwardCount = 0;

		for (Player player : starters) {
			if ("Goalkeeper".equals(player.getPosition())) {
				goalkeeperCount++;
			} else if ("Defender".equals(player.getPosition())) {
				defenderCount++;
			} else if ("Midfielder".equals(player.getPosition())) {
				midfielderCount++;
			} else if ("Forward".equals(player.getPosition())) {
				forwardCount++;
			}
		}
		if (goalkeeperCount == 1 && defenderCount > 2 && defenderCount < 6 && midfielderCount > 2 && midfielderCount < 6
				&& forwardCount > 0 && forwardCount < 4 && defenderCount + midfielderCount + forwardCount == 10) {
			return true;
		} else {
			return false;
		}
	}

}
