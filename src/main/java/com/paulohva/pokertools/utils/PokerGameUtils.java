package com.paulohva.pokertools.utils;

import java.util.*;

/**
 * Constants from a poker game:
 * 1 - Number of possible cards in hand
 * 2 - Map from card Value to a Ranked number
 * 3 - Map to evaluate possible kinds
 */
public final class PokerGameUtils {

    private PokerGameUtils() {
    }

    public final static int NUMBER_OF_CARDS_IN_HAND = 5;

    public final static Map<String, Integer> CARD_VALUE_TO_RANK = new HashMap<String, Integer>() {{
        put("2", 2);
        put("3", 3);
        put("4", 4);
        put("5", 5);
        put("6", 6);
        put("7", 7);
        put("8", 8);
        put("9", 9);
        put("10", 10);
        put("J", 11);
        put("Q", 12);
        put("K", 13);
        put("A", 14);
    }};

    public final static Set<Character> CARD_KIND_SET = new HashSet<Character>() {{
        add('H');
        add('S');
        add('C');
        add('D');
    }};


}
