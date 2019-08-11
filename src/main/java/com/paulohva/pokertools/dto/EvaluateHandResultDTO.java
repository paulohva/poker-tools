package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class EvaluateHandResultDTO implements Serializable {
    private String winnerPlayer;
    private int handRank;

    public EvaluateHandResultDTO(String winnerPlayer, int handRank) {
        this.winnerPlayer = winnerPlayer;
        this.handRank = handRank;
    }

    public EvaluateHandResultDTO() {
    }

    public int getHandRank() {
        return handRank;
    }

    public void setHandRank(int handRank) {
        this.handRank = handRank;
    }

    public String getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(String winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }
}
