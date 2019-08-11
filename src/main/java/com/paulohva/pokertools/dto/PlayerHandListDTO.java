package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class PlayerHandListDTO implements Serializable {

    private PlayerHandDTO playerOne;
    private PlayerHandDTO playerTwo;

    public PlayerHandListDTO() {
    }

    public PlayerHandListDTO(PlayerHandDTO playerOne, PlayerHandDTO playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public PlayerHandDTO getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(PlayerHandDTO playerOne) {
        this.playerOne = playerOne;
    }

    public PlayerHandDTO getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(PlayerHandDTO playerTwo) {
        this.playerTwo = playerTwo;
    }
}
