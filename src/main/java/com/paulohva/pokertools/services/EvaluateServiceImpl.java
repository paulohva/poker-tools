package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.EvaluateHandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Override
    public EvaluateHandResultDTO evaluateHands(PlayerHandListDTO playerHandListDTO) {
        EvaluateHandResultDTO handResultDTO = new EvaluateHandResultDTO();

        //TODO completamente errado, arrumar
        //TODO COLOCAR INDEXOUTOFBOUNDS NO EXCEPTION HANDER
        int player1HandRank = getHandRank(playerHandListDTO.getPlayerOne());
        int player2HandRank = getHandRank(playerHandListDTO.getPlayerTwo());

        // less is better;
        if (player1HandRank < player2HandRank) {

            //todo method to set winner
            handResultDTO.setWinnerPlayer("Player1");
            handResultDTO.setHandRank(player1HandRank);
            return handResultDTO;
        } else if (player1HandRank > player2HandRank) {
            handResultDTO.setWinnerPlayer("Player2");
            handResultDTO.setHandRank(player2HandRank);
            return handResultDTO;
        }


        handResultDTO.setWinnerPlayer("Draw");
        handResultDTO.setHandRank(0);
        return handResultDTO;
        // draw
        //todo


    }

/*    private HandResultDTO evaluateDraw(PlayerHandListDTO players, int handRank) {
        //TODO transformar o handRank em enumerator, tambem qualquer string que tiver
        if(handRank == 1 || handRank == 4 || handRank == 5) {
            for (CardDTO card: players.get
                 ) {

            }
        }
    }*/

    //todo fix to return rank + high card 1 and 2 (new dto)
    private int getHandRank(PlayerHandDTO hand) {

        //TODO tentar ordernar o set
//TODO fazer a ordenacao antes, na etapa de validacao e ja deixar no objeto da lista de cartas
        

        hand.setCards(Arrays.sort(hand.getCards());

        hand.getCards().sort(Comparator.comparing(i -> i.getRank()));

        //Group Kind
        Map<Character, Long> handKindGrouped = hand.getCards().stream().collect(Collectors.groupingBy(CardDTO::getKind, Collectors.counting()));
        //Group card rank
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
    public boolean isAllCardsValid(Set<CardDTO> cards) {

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

        if (cards.size() != 10) {
            return false;
        }

        //TODO FALTOU VERIFICAR CARTA REPETIDA
        return true;
    }
}
