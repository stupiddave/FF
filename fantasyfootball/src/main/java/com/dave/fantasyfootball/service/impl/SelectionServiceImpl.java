package com.dave.fantasyfootball.service.impl;
//package com.dave.fantasyfootball.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.dave.fantasyfootball.domain.Player;
//import com.dave.fantasyfootball.domain.Selection;
//import com.dave.fantasyfootball.repository.SelectionRepository;
//import com.dave.fantasyfootball.repository.SelectionRepositoryImpl;
//
//@Service
//public class SelectionServiceImpl implements SelectionService {
//
//	@Autowired
//	private PropertiesService propertiesService;
//	@Autowired 
//	private SelectionRepository selectionRepository;
//	
//	
//	
//	public void setSelectionRepository(SelectionRepository selectionRepository) {
//		this.selectionRepository = selectionRepository;
//	}
//
//	@Override
//	public void insertSelection(Selection selection) {
////		int selectionGameweek = propertiesService.getSelectionGameweek();
//		SelectionRepository selectionRepository = new SelectionRepositoryImpl();
//		selectionRepository.insertSelection(selection, 5);
//	}
//
//	@Override
//	public List<Player> getLatestLineup(int teamId) {
//		return selectionRepository.getLatestLineup(teamId);
//	}
//
//}
