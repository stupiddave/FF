package com.dave.fantasyfootball.form;


public class SelectionForm {

	private int selectionGameweek;
	private String[] lineup = new String[18];
	private int captainId;
	private int viceCaptainId;

	public int getSelectionGameweek() {
		return selectionGameweek;
	}

	public void setSelectionGameweek(int selectionGameweek) {
		this.selectionGameweek = selectionGameweek;
	}

	public String[] getLineup() {
		return lineup;
	}

	public void setLineup(String[] lineup) {
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