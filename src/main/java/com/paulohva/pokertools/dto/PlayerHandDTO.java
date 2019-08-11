package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class PlayerHandDTO implements Serializable {

    private String playerName;
    private CardDTO[] cards;

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

    //todo checa o estado atual
    public boolean isValuesConsecutive() {
        for(int index = 0; index < cards.length; index++) {
            if(index == cards.length - 1) {
                continue;
            }
            if(cards[index + 1].getRank() - cards[index].getRank() != 1) {
                return false;
            }
        }
        return true;
    }

    public List<CardDTO> getCardsOrdened
}
