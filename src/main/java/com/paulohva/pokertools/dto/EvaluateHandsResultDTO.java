package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class EvaluateHandsResultDTO implements Serializable {
    private PlayerHandDTO winnerPlayer;
    private HandRankEnum rank;

    public EvaluateHandsResultDTO() {
    }

    public EvaluateHandsResultDTO(PlayerHandDTO winnerPlayer, HandRankEnum rank) {
        this.winnerPlayer = winnerPlayer;
        this.rank = rank;
    }

    public PlayerHandDTO getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(PlayerHandDTO winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    public HandRankEnum getRank() {
        return rank;
    }

    public void setRank(HandRankEnum rank) {
        this.rank = rank;
    }
}
