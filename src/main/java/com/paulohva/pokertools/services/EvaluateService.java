package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.EvaluateHandsResultDTO;
import com.paulohva.pokertools.dto.EvaluateHandsRequestDTO;

import java.util.Set;

public interface EvaluateService {

    /**
     *
     * @param evaluateHandsRequestDTO
     * @return
     */
    void verifyAllCardsValid(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     *
     * @param evaluateHandsRequestDTO
     * @return
     */
    EvaluateHandsRequestDTO orderAndSortCards(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     *
     * @param evaluateHandsRequestDTO
     * @return
     */
    EvaluateHandsResultDTO evaluateHands(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

}
