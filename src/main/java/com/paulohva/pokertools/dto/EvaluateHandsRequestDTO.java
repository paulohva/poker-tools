package com.paulohva.pokertools.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class EvaluateHandsRequestDTO implements Serializable {

    @NotNull
    private PlayerHandDTO playerOne;
    @NotNull
    private PlayerHandDTO playerTwo;

    public EvaluateHandsRequestDTO() {
    }

    public EvaluateHandsRequestDTO(PlayerHandDTO playerOne, PlayerHandDTO playerTwo) {
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
