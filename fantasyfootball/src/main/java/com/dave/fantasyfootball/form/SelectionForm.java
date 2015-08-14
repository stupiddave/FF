package com.dave.fantasyfootball.form;

import java.util.ArrayList;
import java.util.List;

import com.dave.fantasyfootball.domain.Team;
import com.dave.fantasyfootball.service.SelectionFormService;

public class SelectionForm {

	SelectionFormService selectionFormPlayerService;

	private List<Integer> starters = new ArrayList<Integer>();
	private int sub1;
	private int sub2;
	private int sub3;
	private int sub4;
	private int sub5;
	private int sub6;
	private int sub7;
	private int captain;
	private int viceCaptain;

	public SelectionForm() {
	}

	public SelectionForm(Team team, SelectionFormService selectionFormPlayerService) {
		this.selectionFormPlayerService = selectionFormPlayerService;
		this.starters = selectionFormPlayerService.getStartersFromTeam(team);
		this.sub1 = selectionFormPlayerService.getSubsFromTeam(team).get(0);
		this.sub2 = selectionFormPlayerService.getSubsFromTeam(team).get(1);
		this.sub3 = selectionFormPlayerService.getSubsFromTeam(team).get(2);
		this.sub4 = selectionFormPlayerService.getSubsFromTeam(team).get(3);
		this.sub5 = selectionFormPlayerService.getSubsFromTeam(team).get(4);
		this.sub6 = selectionFormPlayerService.getSubsFromTeam(team).get(5);
		this.sub7 = selectionFormPlayerService.getSubsFromTeam(team).get(6);
		this.captain = team.getSelection().getCaptainId();
		this.viceCaptain = team.getSelection().getViceCaptainId();
	}

	public SelectionFormService getSelectionFormPlayerService() {
		return selectionFormPlayerService;
	}

	public void setSelectionFormPlayerService(SelectionFormService selectionFormPlayerService) {
		this.selectionFormPlayerService = selectionFormPlayerService;
	}

	public List<Integer> getStarters() {
		return starters;
	}

	public void setStarters(List<Integer> starters) {
		this.starters = starters;
	}

	public int getSub1() {
		return sub1;
	}

	public void setSub1(int sub1) {
		this.sub1 = sub1;
	}

	public int getSub2() {
		return sub2;
	}

	public void setSub2(int sub2) {
		this.sub2 = sub2;
	}

	public int getSub3() {
		return sub3;
	}

	public void setSub3(int sub3) {
		this.sub3 = sub3;
	}

	public int getSub4() {
		return sub4;
	}

	public void setSub4(int sub4) {
		this.sub4 = sub4;
	}

	public int getSub5() {
		return sub5;
	}

	public void setSub5(int sub5) {
		this.sub5 = sub5;
	}

	public int getSub6() {
		return sub6;
	}

	public void setSub6(int sub6) {
		this.sub6 = sub6;
	}

	public int getSub7() {
		return sub7;
	}

	public void setSub7(int sub7) {
		this.sub7 = sub7;
	}

	public int getCaptain() {
		return captain;
	}

	public void setCaptain(int captain) {
		this.captain = captain;
	}

	public int getViceCaptain() {
		return viceCaptain;
	}

	public void setViceCaptain(int viceCaptain) {
		this.viceCaptain = viceCaptain;
	}
}
