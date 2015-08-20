package com.dave.fantasyfootball.form.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.dave.fantasyfootball.domain.Player;
import com.dave.fantasyfootball.form.SelectionForm;
import com.dave.fantasyfootball.repository.PlayerRepository;

@Component
public class SelectionFormValidator implements Validator {

	private PlayerRepository playerRepository;

	@Autowired
	public SelectionFormValidator(PlayerRepository playerRepository) {
		this.playerRepository = playerRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return SelectionForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		SelectionForm selectionForm = (SelectionForm) target;
		List<Player> starters = playerRepository.getPlayersByIdList(selectionForm.getStarters());
		if (!isValidStartingLineup(starters)) {
			errors.reject("Invalid Formation", "Invalid Formation");
		}

		if (duplicatePlayerExists(selectionForm)) {
			errors.reject("Duplicate player selected (check a player isn't selected as both a starter and a sub).",
					"Duplicate player selected (check a player isn't selected as both a starter and a sub).");
		}
	}

	private boolean duplicatePlayerExists(SelectionForm selectionForm) {
		boolean dupeExists = false;
		List<Integer> startersAndSubsList = new ArrayList<Integer>(selectionForm.getStarters());
		startersAndSubsList.add(selectionForm.getSub1());
		startersAndSubsList.add(selectionForm.getSub2());
		startersAndSubsList.add(selectionForm.getSub3());
		startersAndSubsList.add(selectionForm.getSub4());
		startersAndSubsList.add(selectionForm.getSub5());
		startersAndSubsList.add(selectionForm.getSub6());
		startersAndSubsList.add(selectionForm.getSub7());
		Set<Integer> startersAndSubsSet = new HashSet<Integer>(startersAndSubsList);
		
		if(startersAndSubsSet.size() < startersAndSubsList.size()) {
			dupeExists = true;
		}
		return dupeExists;
	}

	private boolean isValidStartingLineup(List<Player> starters) {

		boolean isvalid = true;
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
			isvalid = true;
		} else {
			isvalid = false;
		}
		return isvalid;
	}
}