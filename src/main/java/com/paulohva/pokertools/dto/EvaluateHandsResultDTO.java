package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class EvaluateHandsResultDTO implements Serializable {
    private String playerName;
    private HandRankEnum highRank;

    public EvaluateHandsResultDTO() {
    }

    public EvaluateHandsResultDTO(String playerName, HandRankEnum highRank) {
        this.playerName = playerName;
        this.highRank = highRank;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public HandRankEnum getHighRank() {
        return highRank;
    }

    public void setHighRank(HandRankEnum highRank) {
        this.highRank = highRank;
    }
}
