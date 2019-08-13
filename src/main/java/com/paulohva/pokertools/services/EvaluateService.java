package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.EvaluateHandsResultDTO;
import com.paulohva.pokertools.dto.EvaluateHandsRequestDTO;

import java.util.Set;

public interface EvaluateService {

    /**
     * Verify cards constraints based on a poker game.
     *
     * @param evaluateHandsRequestDTO
     * @return True when everything is ok
     */
    boolean verifyAllCardsValid(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     * Sort the players hand to prepare objects to be evaluated.
     *
     * @param evaluateHandsRequestDTO
     * @return EvaluateHandsRequestDTO updated with the sorted cards
     */
    EvaluateHandsRequestDTO sortPlayersHand(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

    /**
     * Get the hand rank for each player and set the value in a transient property
     * to be used for evaluation
     *
     * @param request
     * @return EvaluateHandsRequestDTO updated with the hand ranks for each player
     */
    EvaluateHandsRequestDTO getHandRanks(EvaluateHandsRequestDTO request);

    /**
     * Evaluate a game to decide the winner applying poker rules.
     *
     * @param request
     * @return EvaluateHandsResultDTO result object containing the winner name and the hand rank
     * defined by a Enumerator
     */
    EvaluateHandsResultDTO getWinningHandRank(EvaluateHandsRequestDTO request);

    /**
     * Try to resolve a Draw result from the getWinningHandRank service
     *
     * @param evaluateHandsRequestDTO
     * @return a new EvaluateHandsResultDTO result object containing the winner name and the hand rank
     * defined by a Enumerator
     */
    EvaluateHandsResultDTO tryResolveDraw(EvaluateHandsRequestDTO evaluateHandsRequestDTO);

}
