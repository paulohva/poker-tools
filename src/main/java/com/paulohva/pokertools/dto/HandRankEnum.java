package com.paulohva.pokertools.dto;

public enum HandRankEnum {
    STRAIGHT_FLUSH(1),
    FOUR_OF_A_KIND(2),
    FULL_HOUSE(3),
    FLUSH(4),
    STRAIGHT(5),
    THREE_OF_A_KIND(6),
    TWO_PAIR(7),
    ONE_PAIR(8),
    HIGH_CARD(9),
    DRAW(10);

    private int rank;

    HandRankEnum(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }
}
