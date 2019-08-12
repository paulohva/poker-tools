package com.paulohva.pokertools.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class PlayerHandDTO implements Serializable {

    @NotNull
    private String playerName;
    @NotNull
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

}
