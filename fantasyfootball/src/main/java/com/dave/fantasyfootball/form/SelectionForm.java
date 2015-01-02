package com.dave.fantasyfootball.form;

import java.util.ArrayList;

public class SelectionForm {

	private int selectionGameweek;
	private ArrayList<String> lineup;
	private int captainId;
	private int viceCaptainId;

	public int getSelectionGameweek() {
		return selectionGameweek;
	}

	public void setSelectionGameweek(int selectionGameweek) {
		this.selectionGameweek = selectionGameweek;
	}

	public ArrayList<String> getLineup() {
		return lineup;
	}

	public void setLineup(ArrayList<String> lineup) {
		this.lineup = lineup;
	}

	public int getCaptainId() {
		return captainId;
	}

	public void setCaptainId(int captainId) {
		this.captainId = captainId;
	}

	public int getViceCaptainId() {
		return viceCaptainId;
	}

	public void setViceCaptainId(int viceCaptainId) {
		this.viceCaptainId = viceCaptainId;
	}
}