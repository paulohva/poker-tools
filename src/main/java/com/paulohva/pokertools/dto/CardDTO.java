package com.paulohva.pokertools.dto;

import com.paulohva.pokertools.utils.StandardDeckUtils;

import java.io.Serializable;
import java.util.Objects;

public class CardDTO implements Serializable {
    private String value;
    private char kind;

    public CardDTO() {
    }

    public CardDTO(String value, char kind) {
        this.value = value;
        this.kind = kind;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public char getKind() {
        return kind;
    }

    public void setKind(char kind) {
        this.kind = kind;
    }

    public int getRank() {
        if(this.value == null ) {
            //todo verify this

            return 0;
        }
        return StandardDeckUtils.getCardRankByValue(this.value);
    }
}
