package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.*;
import com.paulohva.pokertools.services.exception.InvalidRequestException;
import com.paulohva.pokertools.utils.PokerGameUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class EvaluateServiceImpl implements EvaluateService {
    @Override
    public EvaluateHandsResultDTO evaluateHands(EvaluateHandsRequestDTO evaluateHandsRequestDTO) {
        EvaluateHandsResultDTO handResultDTO = new EvaluateHandsResultDTO();

        //TODO completamente errado, arrumar
        //TODO COLOCAR INDEXOUTOFBOUNDS NO EXCEPTION HANDER
        HandRankEnum player1HandRank = getHandRank(evaluateHandsRequestDTO.getPlayerOne());
        HandRankEnum player2HandRank = getHandRank(evaluateHandsRequestDTO.getPlayerTwo());

        // less is better;
        // TODO: expain this rule
        if (player1HandRank.getRank() < player2HandRank.getRank()) {
            //todo method to set winner
            handResultDTO.setWinnerPlayer(evaluateHandsRequestDTO.getPlayerOne());
            handResultDTO.setRank(player1HandRank);
            return handResultDTO;
        } else if (player1HandRank.getRank() > player2HandRank.getRank()) {
            handResultDTO.setWinnerPlayer(evaluateHandsRequestDTO.getPlayerTwo());
            handResultDTO.setRank(player2HandRank);
            return handResultDTO;
        }

        //TODO pensar no draw / fazer um metodo pra isso
        handResultDTO.setRank(HandRankEnum.DRAW);
        return handResultDTO;
    }

    @Override
    public void verifyAllCardsValid(EvaluateHandsRequestDTO evaluateHandsRequestDTO) {
        CardDTO[] playerOneCards = evaluateHandsRequestDTO.getPlayerOne().getCards();
        CardDTO[] playerTwoCards = evaluateHandsRequestDTO.getPlayerTwo().getCards();

        //TODO: entender melhor esse method reference
        // all cards concatenation with distinct. CardDTO equals method uses 'kind' and 'value' properties.
        CardDTO[] allCardsDistinct = Stream.concat(Arrays.stream(playerOneCards), Arrays.stream(playerTwoCards)).distinct().toArray(CardDTO[]::new);

        if (allCardsDistinct.length != (PokerGameUtils.NUMBER_OF_CARDS_IN_HAND * 2)) {
            throw new InvalidRequestException("Card missing or duplicated");
        }
        //todo:temtar substituir por um stream
        for (CardDTO card : allCardsDistinct) {
            if (!card.isCardValid()) {
               throw new InvalidRequestException(String.format("Invalid card: %s",card));
            }
        }
    }

    @Override
    public EvaluateHandsRequestDTO orderAndSortCards(EvaluateHandsRequestDTO evaluateHandsRequestDTO) {
        evaluateHandsRequestDTO.getPlayerOne().setCards(orderAndSortHand(evaluateHandsRequestDTO.getPlayerOne().getCards()));
        evaluateHandsRequestDTO.getPlayerTwo().setCards(orderAndSortHand(evaluateHandsRequestDTO.getPlayerTwo().getCards()));
        return evaluateHandsRequestDTO;
    }

    private CardDTO[] orderAndSortHand(CardDTO[] cards) {
        return (CardDTO[]) Arrays.stream(cards).sorted(Comparator.comparing(i -> i.getRank())).distinct().toArray();
    }

    private HandRankEnum getHandRank(PlayerHandDTO playerHandDTO) {
        CardDTO[] playerCards = playerHandDTO.getCards();
        // group by card kind
        Map<Character, Long> handKindGrouped = Arrays.stream(playerCards).collect(Collectors.groupingBy(CardDTO::getKind, Collectors.counting()));
        // group by card rank
        Map<Integer, Long> handCardRankGrouped = Arrays.stream(playerCards).collect(Collectors.groupingBy(CardDTO::getRank, Collectors.counting()));
        if (playerHandDTO.isValuesConsecutive()) {
            // all cards are same kind
            //TODO: check minimal value for straight sequence
            if (handKindGrouped.size() == 1) {
                return HandRankEnum.STRAIGHT_FLUSH;
            }
            return HandRankEnum.STRAIGHT;
        }
        // all cards are same kind
        if (handKindGrouped.size() == 1) {
            return HandRankEnum.FLUSH;
        }
        return HandRankEnum.HIGH_CARD;
    }
}
