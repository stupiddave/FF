package com.dave.fantasyfootball.service;

import com.dave.fantasyfootball.domain.Gameweek;

public interface PropertiesRepository {

	int incrementGameweek();

	int decrementGameweek();

	int getCurrentGameweek();

	void initializeGameweek();

	Gameweek getLastGameweek();

	void addGameweek(Gameweek gameweek);

}
