package com.paulohva.pokertools.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class PlayerHandDTO implements Serializable {

    @NotNull
    private String playerName;
    @NotNull
    private CardDTO[] cards;

    @JsonIgnore
    private HandRankEnum handRank;

    public PlayerHandDTO(String playerName, CardDTO[] cards) {
        this.playerName = playerName;
        this.cards = cards;
    }

    public PlayerHandDTO() {
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public CardDTO[] getCards() {
        return cards;
    }

    public void setCards(CardDTO[] cards) {
        this.cards = cards;
    }

    public HandRankEnum getHandRank() {
        return handRank;
    }

    public void setHandRank(HandRankEnum handRank) {
        this.handRank = handRank;
    }
}
