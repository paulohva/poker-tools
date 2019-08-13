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
    boolean verifyAllCardsValid(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     *
     * @param evaluateHandsRequestDTO
     * @return
     */
    EvaluateHandsRequestDTO sortPlayersHand(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     *
     * @param evaluateHandsRequestDTO
     * @return
     */
    EvaluateHandsResultDTO tryResolveDraw(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     *
     * @param request
     * @return
     */
    EvaluateHandsRequestDTO getHandRanks(EvaluateHandsRequestDTO request);

    /**
     *
     * @param request
     * @return
     */
    EvaluateHandsResultDTO getWinningHandRank(EvaluateHandsRequestDTO request);

}
