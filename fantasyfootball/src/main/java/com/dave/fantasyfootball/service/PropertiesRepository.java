package com.dave.fantasyfootball.service;

public interface PropertiesRepository {

	int incrementGameweek();
	int decrementGameweek();
	int getCurrentGameweek();

}
