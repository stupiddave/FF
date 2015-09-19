package com.dave.fantasyfootball.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dave.fantasyfootball.domain.Gameweek;
import com.dave.fantasyfootball.service.PropertiesRepository;
import com.dave.fantasyfootball.service.PropertiesService;

@Service
public class PropertiesServiceImpl implements PropertiesService {

	private PropertiesRepository propertiesRepository;

	@Autowired
	public PropertiesServiceImpl(PropertiesRepository propertiesRepository) {
		this.propertiesRepository = propertiesRepository;
	}

	@Override
	public int incrementGameweek() {
		return propertiesRepository.incrementGameweek();
	}

	@Override
	public int decrementGameweek() {
		return propertiesRepository.decrementGameweek();
	}

	@Override
	public int getSelectionGameweek() {
		return propertiesRepository.getCurrentGameweek();
	}

	@Override
	public void addGameweek(Gameweek gameweek) {
		propertiesRepository.addGameweek(gameweek);
	}

	@Override
	public Gameweek getLastGameweek() {
		return propertiesRepository.getLastGameweek();
	}

}
