package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.HandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;

import java.util.List;
import java.util.Set;

public interface EvaluateService {

    HandResultDTO evaluateHands(PlayerHandListDTO playerHandListDTO);
    boolean isCardsValid(Set<CardDTO> cards);
}
