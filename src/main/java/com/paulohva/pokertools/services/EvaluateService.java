package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.HandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;

import java.util.List;

public interface EvaluateService {

    HandResultDTO evaluateHands(PlayerHandListDTO playerHandListDTO);
    boolean isCardsValid(List<CardDTO> cards);
}
