package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class PlayerHandDTO implements Serializable {

    private String playerName;
    private List<CardDTO> cards;

    public PlayerHandDTO(String playerName, List<CardDTO> cards) {
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

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }
}
