package com.paulohva.pokertools.dto;

import java.beans.Transient;
import java.io.Serializable;
import java.util.List;

public class EvaluateHandsResultDTO extends PlayerHandDTO implements Serializable {
    private HandRankEnum highRank;

    public EvaluateHandsResultDTO() {
    }

    public EvaluateHandsResultDTO(HandRankEnum highRank) {
        this.highRank = highRank;
    }

    public HandRankEnum getHighRank() {
        return highRank;
    }

    public void setHighRank(HandRankEnum highRank) {
        this.highRank = highRank;
    }
}
