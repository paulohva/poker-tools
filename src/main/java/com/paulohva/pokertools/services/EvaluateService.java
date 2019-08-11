package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.EvaluateHandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;

import java.util.Set;

public interface EvaluateService {

    EvaluateHandResultDTO evaluateHands(PlayerHandListDTO playerHandListDTO);
    boolean isAllCardsValid(Set<CardDTO> cards);
}
