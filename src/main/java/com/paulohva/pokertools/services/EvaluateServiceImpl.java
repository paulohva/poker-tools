package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.HandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;
import com.paulohva.pokertools.utils.StandardDeckUtils;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Override
    public HandResultDTO evaluateHands(PlayerHandListDTO playerHandListDTO) {
        HandResultDTO handResultDTO = new HandResultDTO();

        //TODO completamente errado, arrumar
        //TODO COLOCAR INDEXOUTOFBOUNDS NO EXCEPTION HANDER
        int player1HandRank = getHandRank(playerHandListDTO.getPlayers().get(0));
        int player2HandRank = getHandRank(playerHandListDTO.getPlayers().get(1));

        // less is better;
        if (player1HandRank < player2HandRank) {
            handResultDTO.setWinnerPlayer("Player1");
            handResultDTO.setHandRank(player1HandRank);
            return handResultDTO;
        }
        //todo decidir entre o if e o else
        handResultDTO.setWinnerPlayer("Player2");
        handResultDTO.setHandRank(player2HandRank);
        return handResultDTO;

    }

    //todo fix to return rank + high card 1 and 2 (new dto)
    private int getHandRank(PlayerHandDTO hand) {

        //TODO tentar ordernar o set
        hand.getCards().sort(Comparator.comparing(i -> i.getRank()));

        //Group Kind
        Map<Character, Long> handKindGrouped = hand.getCards().stream().collect(Collectors.groupingBy(CardDTO::getKind, Collectors.counting()));
        //Groun card rank
        Map<Integer, Long> handCardRankGrouped = hand.getCards().stream().collect(Collectors.groupingBy(CardDTO::getRank, Collectors.counting()));

        if (hand.isValuesConsecutive()) {
            if (handKindGrouped.size() == 1) {
                return 1;
            }
            //TODO PROBLEMA DO AS! ele pode techar sequencia atras ou na frente
            return 5;
        }

        if (handKindGrouped.size() == 1) {
            return 4;
        }

        return 9;
    }

    @Override
    public boolean isCardsValid(Set<CardDTO> cards) {

        //todo faltando os comentarios
        if (cards == null || cards.size() != 10) {
            return false;
        }

        //todo:temtar substituir por um stream
        for (CardDTO card : cards) {
            if (!card.isCardValid()) {
                return false;
            }
        }

        Set<CardDTO> cardsSet = new HashSet<>(cards);

        if(cards.size() != 10) {
            return false;
        }

        //TODO FALTOU VERIFICAR CARTA REPETIDA
        return true;
    }
}
