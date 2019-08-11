package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    //todo checa o estado atual
    public boolean isValuesConsecutive() {
        Integer[] cardRankArray = cards.stream().map(i -> i.getRank()).toArray(Integer[]::new);

        for(int index = 0; index < cardRankArray.length; index++) {
            if(index == cardRankArray.length - 1) {
                continue;
            }
            if(cardRankArray[index + 1] - cardRankArray[index] != 1) {
                return false;
            }
        }

        return true;
    }
}
