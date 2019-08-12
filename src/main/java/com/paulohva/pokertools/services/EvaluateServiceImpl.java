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
        }
        if (player1HandRank.getRank() > player2HandRank.getRank()) {
            handResultDTO.setWinnerPlayer(evaluateHandsRequestDTO.getPlayerTwo());
            handResultDTO.setRank(player2HandRank);
            return handResultDTO;
        }

        // deal with draw
        // high card situation (extract method)
        for (int index = 0; index < PokerGameUtils.NUMBER_OF_CARDS_IN_HAND; index++) {
            if (evaluateHandsRequestDTO.getPlayerOne().getCards()[index].getRank() > evaluateHandsRequestDTO.getPlayerTwo().getCards()[index].getRank()) {
                handResultDTO.setWinnerPlayer(evaluateHandsRequestDTO.getPlayerOne());
                handResultDTO.setRank(player1HandRank);
                return handResultDTO;
            }
            if (evaluateHandsRequestDTO.getPlayerOne().getCards()[index].getRank() < evaluateHandsRequestDTO.getPlayerTwo().getCards()[index].getRank()) {
                handResultDTO.setWinnerPlayer(evaluateHandsRequestDTO.getPlayerTwo());
                handResultDTO.setRank(player2HandRank);
                return handResultDTO;
            }
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
                throw new InvalidRequestException(String.format("Invalid card: %s", card));
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

        // two card ranked grouped means full house or a four in a kind
        if (handCardRankGrouped.size() == 2) {
            // condition to be a four in a kind
            if (handCardRankGrouped.values().stream().anyMatch(i -> i == 4L)) {
                return HandRankEnum.FOUR_OF_A_KIND;
            }
            return HandRankEnum.FULL_HOUSE;
        }

        // three group of cards means minimum two pairs
        if (handCardRankGrouped.size() == 3) {
            if (handCardRankGrouped.values().stream().filter(i -> i == 2L).count() == 2) {
                return HandRankEnum.TWO_PAIR;
            }
            if (handCardRankGrouped.values().stream().filter(i -> i == 3L).count() == 1) {
                return HandRankEnum.THREE_OF_A_KIND;
            }
        }

        if (handCardRankGrouped.values().stream().filter(i -> i == 2L).count() == 1) {
            return HandRankEnum.ONE_PAIR;
        }

        return HandRankEnum.HIGH_CARD;
    }
}
