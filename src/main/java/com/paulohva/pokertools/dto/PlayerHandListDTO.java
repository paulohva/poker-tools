package com.paulohva.pokertools.dto;

import java.io.Serializable;
import java.util.List;

public class PlayerHandListDTO implements Serializable {

    private List<PlayerHandDTO> players;

    public PlayerHandListDTO() {
    }

    public PlayerHandListDTO(List<PlayerHandDTO> players) {
        this.players = players;
    }

    public List<PlayerHandDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerHandDTO> players) {
        this.players = players;
    }
}
