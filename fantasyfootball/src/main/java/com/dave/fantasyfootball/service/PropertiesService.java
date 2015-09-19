package com.dave.fantasyfootball.service;

import com.dave.fantasyfootball.domain.Gameweek;

public interface PropertiesService {

	public int incrementGameweek();

	public int decrementGameweek();

	public int getSelectionGameweek();

	public Gameweek getLastGameweek();

	public void addGameweek(Gameweek newGameweek);
}
