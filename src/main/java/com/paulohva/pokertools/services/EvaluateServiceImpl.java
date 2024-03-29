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
    public boolean verifyAllCardsValid(EvaluateHandsRequestDTO request) {
        CardDTO[] playerOneCards = request.getPlayerOne().getCards();
        CardDTO[] playerTwoCards = request.getPlayerTwo().getCards();

        // all cards concatenation with distinct. CardDTO equals method uses 'kind' and 'value' properties.
        CardDTO[] allCardsDistinct = Stream.concat(Arrays.stream(playerOneCards), Arrays.stream(playerTwoCards)).distinct().toArray(CardDTO[]::new);

        // verify if it matches the number of unique cards
        if (allCardsDistinct.length != (PokerGameUtils.NUMBER_OF_CARDS_IN_HAND * 2)) {
            throw new InvalidRequestException("Card missing or duplicated");
        }
        for (CardDTO card : allCardsDistinct) {
            // isCardValid verify all cards using the enumerator constant called: PokerGameUtils.CARD_VALUE_TO_RANK
            if (!card.isCardValid()) {
                throw new InvalidRequestException(String.format("Invalid card: %s", card));
            }
        }
        return true;
    }

    @Override
    public EvaluateHandsRequestDTO sortPlayersHand(EvaluateHandsRequestDTO request) {

        // update the cards order through the sortHand method
        request.getPlayerOne().setCards(sortHand(request.getPlayerOne().getCards()));
        request.getPlayerTwo().setCards(sortHand(request.getPlayerTwo().getCards()));
        return request;
    }

    @Override
    public EvaluateHandsRequestDTO getHandRanks(EvaluateHandsRequestDTO request) {
        CardDTO[] playerOneCards = request.getPlayerOne().getCards();
        CardDTO[] playerTwoCards = request.getPlayerTwo().getCards();

        HandRankEnum playerOneHandRank = getHandRank(playerOneCards);
        HandRankEnum playerTwoHandRank = getHandRank(playerTwoCards);

        // store the hand rank result in a transient property to be used to check who wins
        request.getPlayerOne().setHandRank(playerOneHandRank);
        request.getPlayerTwo().setHandRank(playerTwoHandRank);

        return request;
    }

    @Override
    public EvaluateHandsResultDTO getWinningHandRank(EvaluateHandsRequestDTO request) {

        PlayerHandDTO playerOne = request.getPlayerOne();
        PlayerHandDTO playerTwo = request.getPlayerTwo();

        // verify who wins.
        // as poker rules described in: https://en.wikipedia.org/wiki/List_of_poker_hands, less rank number is better
        if (playerOne.getHandRank().getRank() < request.getPlayerTwo().getHandRank().getRank()) {
            return createResultEvaluateHands(playerOne.getHandRank(), playerOne.getPlayerName(), playerOne.getCards());
        }
        if (playerOne.getHandRank().getRank() > request.getPlayerTwo().getHandRank().getRank()) {
            return createResultEvaluateHands(playerTwo.getHandRank(), playerTwo.getPlayerName(), playerTwo.getCards());
        }
        return createDrawResultEvaluateHands();
    }



    @Override
    public EvaluateHandsResultDTO tryResolveDraw(EvaluateHandsRequestDTO request) {
        PlayerHandDTO playerOne = request.getPlayerOne();
        PlayerHandDTO playerTwo = request.getPlayerTwo();

        CardDTO[] playerOneCards = playerOne.getCards();
        CardDTO[] playerTwoCards = playerTwo.getCards();

        // fix the ace card rank for ace-low in straights
        fixAceToFiveStraight(playerOneCards, playerOne.getHandRank());
        fixAceToFiveStraight(playerTwoCards, playerTwo.getHandRank());

        // assuming all cards sorted (even by group strengths like pairs and three in kind) verify who wins
        // as poker rules described in: https://en.wikipedia.org/wiki/List_of_poker_hands, less rank number is better
        for (int index = 0; index < PokerGameUtils.NUMBER_OF_CARDS_IN_HAND; index++) {
            if (playerOneCards[index].getRank() > playerTwoCards[index].getRank()) {
                return createResultEvaluateHands(playerOne.getHandRank(), playerOne.getPlayerName(), playerOneCards);
            }
            if (playerOneCards[index].getRank() < playerTwoCards[index].getRank()) {
                return createResultEvaluateHands(playerTwo.getHandRank(), playerTwo.getPlayerName(), playerTwoCards);
            }
        }
        return createDrawResultEvaluateHands();
    }

    private void fixAceToFiveStraight(CardDTO[] cards, HandRankEnum handRank) {
        if (handRank.equals(HandRankEnum.STRAIGHT) || handRank.equals(HandRankEnum.STRAIGHT_FLUSH)) {
            // trick to fix a five-high straight
            if (cards[0].getRank() == 14 && cards[1].getRank() == 5) {
                CardDTO auxCard = cards[0];
                System.arraycopy(cards, 1, cards, 0, 4);
                cards[4] = auxCard;
            }
        }
    }

    private CardDTO[] sortHand(CardDTO[] cards) {
        // group by card rank to order repeated ranks
        Map<Integer, List<CardDTO>> cardsGroupByRank = Arrays.stream(cards).collect(Collectors.groupingBy(CardDTO::getRank));

        // algorithm to sort cards putting stronger games (pair, three in a kind, etc) being first elements
        // TODO: improve this algorithm
        List<CardDTO> cardsSorted = new ArrayList<>();
        for (int index = 4; index > 0; index--) {
            int indexValue = index;
            List<CardDTO> cardsFilteredByGroupSize = cardsGroupByRank.values().stream().filter(i -> i.size() == indexValue).flatMap(List::stream).collect(Collectors.toList());
            cardsFilteredByGroupSize.sort(Comparator.comparing(i -> i.getRank(), Comparator.reverseOrder()));
            cardsSorted.addAll(cardsFilteredByGroupSize);
        }
        return cardsSorted.toArray(new CardDTO[0]);
    }

    private HandRankEnum getHandRank(CardDTO[] playerCards) {
        // group by card kind
        Map<Character, Long> handKindGrouped = Arrays.stream(playerCards).collect(Collectors.groupingBy(CardDTO::getKind, Collectors.counting()));
        // group by card rank
        Map<Integer, Long> handCardRankGrouped = Arrays.stream(playerCards).collect(Collectors.groupingBy(CardDTO::getRank, Collectors.counting()));
        if (isHandCardsRankConsecutive(playerCards)) {
            // all cards are same kind
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
        // three group of cards means three in kind or two pairs
        if (handCardRankGrouped.size() == 3) {
            if (handCardRankGrouped.values().stream().filter(i -> i == 2L).count() == 2) {
                return HandRankEnum.TWO_PAIR;
            }
            if (handCardRankGrouped.values().stream().filter(i -> i == 3L).count() == 1) {
                return HandRankEnum.THREE_OF_A_KIND;
            }
        }
        // means one pair
        if (handCardRankGrouped.values().stream().filter(i -> i == 2L).count() == 1) {
            return HandRankEnum.ONE_PAIR;
        }
        return HandRankEnum.HIGH_CARD;
    }

    private boolean isHandCardsRankConsecutive(CardDTO[] cards) {
        // algorithm to check of a ordered array of cards is consecutive, including ace a low card into a straight
        for (int index = 0; index < cards.length; index++) {
            if (index == cards.length - 1) {
                continue;
            }
            // trick to ace-low as consecutive part of a straight
            if (index == 0 && cards[index].getRank() == 14 && cards[index + 1].getRank() == 5) {
                continue;
            }
            if (cards[index].getRank() - cards[index + 1].getRank() != 1) {
                return false;
            }
        }
        return true;
    }

    private EvaluateHandsResultDTO createResultEvaluateHands(HandRankEnum handRankEnum, String playerName, CardDTO[] cards) {
        EvaluateHandsResultDTO result = new EvaluateHandsResultDTO();
        result.setPlayerName(playerName);
        result.setHighRank(handRankEnum);
        result.setCards(cards);
        return result;
    }

    private EvaluateHandsResultDTO createDrawResultEvaluateHands() {
        EvaluateHandsResultDTO result = new EvaluateHandsResultDTO();
        result.setHighRank(HandRankEnum.DRAW);
        return result;
    }
}
