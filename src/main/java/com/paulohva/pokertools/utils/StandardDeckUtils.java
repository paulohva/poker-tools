package com.paulohva.pokertools.utils;

import com.paulohva.pokertools.dto.CardDTO;

import java.util.*;
import java.util.stream.Collectors;

public final class StandardDeckUtils {

    private StandardDeckUtils() {}

/*    private final static Set<CardDTO> STANDARD_DECK = Arrays.asList(
            new CardDTO('2','H'),
            new CardDTO('3','H'),
            new CardDTO('4','H'),
            new CardDTO('5','H'),
            new CardDTO('6','H'),
            new CardDTO('7','H'),
            new CardDTO('8','H'),
            new CardDTO('9','H'),
            new CardDTO('T','H'),
            new CardDTO('J','H'),
            new CardDTO('Q','H'),
            new CardDTO('K','H'),
            new CardDTO('A','H')
    );*/

    private final static Map<String, Integer> CARD_VALUE_TO_RANK = new HashMap<String, Integer>(){{
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

    public static int getCardRankByValue(String value) {

        List<Integer> rankList = CARD_VALUE_TO_RANK.entrySet()
                .stream()
                .filter(i -> i.getKey().equals(value))
                .map(m -> m.getValue()).collect(Collectors.toList());

        if(rankList.size() > 1) {
            //TODO: throw exception
            return 0;
        }

        return rankList.get(0);
    }
}
