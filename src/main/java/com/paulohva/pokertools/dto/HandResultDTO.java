package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class HandResultDTO implements Serializable {
    private String winnerPlayer;

    private List<String> teste;

    public List<String> getTeste() {
        return teste;
    }

    public void setTeste(List<String> teste) {
        this.teste = teste;
    }

    public HandResultDTO() {
    }

    public HandResultDTO(String winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }

    public String getWinnerPlayer() {
        return winnerPlayer;
    }

    public void setWinnerPlayer(String winnerPlayer) {
        this.winnerPlayer = winnerPlayer;
    }
}
