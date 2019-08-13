package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.*;
import com.paulohva.pokertools.services.exception.InvalidRequestException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.*;

public class EvaluateServiceTest {

    private EvaluateService evaluateService;

    @Before
    public void setUp() throws Exception {
        evaluateService = new EvaluateServiceImpl();
    }

    @Test
    public void testVerifyAllCardsValid_CardMissingOrDuplicated() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createPlayerAndHighCard("James"));

        //when
        try {
            evaluateService.verifyAllCardsValid(request);
            fail();
        } catch (InvalidRequestException e) {
            //then
            assertEquals("Card missing or duplicated", e.getMessage());
        }

        //when
        request.getPlayerOne().setCards(createMissingCardsHand());
        try {
            evaluateService.verifyAllCardsValid(request);
            fail();
        } catch (InvalidRequestException e) {
            //then
            assertEquals("Card missing or duplicated", e.getMessage());
        }
    }

    @Test
    public void testVerifyAllCardsValid_InvalidCard() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerTwo().setCards(createInvalidCardsHand());

        //when
        try {
            evaluateService.verifyAllCardsValid(request);
            fail();
        } catch (InvalidRequestException e) {
            //then
            assertTrue(e.getMessage().contains("Invalid card"));
        }
    }

    @Test
    public void testVerifyAllCardsValid_Valid() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));

        //when
        boolean result = evaluateService.verifyAllCardsValid(request);

        //then
        assertTrue(result);
    }

    @Test
    public void testSortPlayersHand_Valid() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));

        //when
        evaluateService.sortPlayersHand(request);

        //then
        assertTrue(true);
    }

    @Test
    public void testEvaluateHands_Valid() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));


        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertTrue(true);
    }

    @Test
    public void testEvaluateHands_StraightFlush() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createStraightFlush());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.STRAIGHT_FLUSH);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_Flush() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerTwo().setCards(createFlush());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.FLUSH);
        assertEquals(result.getPlayerName(), request.getPlayerTwo().getPlayerName());
    }

    @Test
    public void testEvaluateHands_FullHouse() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createFullHouse());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.FULL_HOUSE);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_StraightFiveHigh() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createStraightFiveHigh());
        request.getPlayerTwo().setCards(createStraightAceHigh());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result = evaluateService.getWinningHandRank(request);
        assertEquals(result.getHighRank(), HandRankEnum.DRAW);
        result = evaluateService.tryResolveDraw(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.STRAIGHT);
        assertEquals(result.getPlayerName(), request.getPlayerTwo().getPlayerName());
    }

    @Test
    public void testEvaluateHands_InvertedStraightFiveHigh() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createStraightAceHigh());
        request.getPlayerTwo().setCards(createStraightFiveHigh());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);
        assertEquals(result.getHighRank(),HandRankEnum.DRAW);
        result = evaluateService.tryResolveDraw(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.STRAIGHT);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_FourInAKind() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createFourInAKind());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.FOUR_OF_A_KIND);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_OnePair() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createOnePair());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.ONE_PAIR);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_TwoPair() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createTwoPair());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.TWO_PAIR);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_ThreeInAKind() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createThreeInAKind());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.THREE_OF_A_KIND);
        assertEquals(result.getPlayerName(), request.getPlayerOne().getPlayerName());
    }

    @Test
    public void testEvaluateHands_Draw() throws Exception{
        //given
        EvaluateHandsRequestDTO request = new EvaluateHandsRequestDTO();
        request.setPlayerOne(createPlayerAndHighCard("John"));
        request.setPlayerTwo(createOtherPlayerAndHighCard("James"));
        request.getPlayerOne().setCards(createStraightAceHigh());
        request.getPlayerTwo().setCards(createOtherStraightAceHigh());

        //when
        request = evaluateService.sortPlayersHand(request);
        request = evaluateService.getHandRanks(request);
        EvaluateHandsResultDTO result =evaluateService.getWinningHandRank(request);

        //then
        assertEquals(result.getHighRank(), HandRankEnum.DRAW);
        assertNull(result.getPlayerName());
    }

    private PlayerHandDTO createPlayerAndHighCard(String name) {
        PlayerHandDTO player = new PlayerHandDTO();
        player.setPlayerName(name);

        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("4", 'C'));
        cards.add(new CardDTO("6", 'S'));
        cards.add(new CardDTO("8", 'C'));
        cards.add(new CardDTO("10", 'S'));

        player.setCards(cards.toArray(new CardDTO[0]));
        return player;
    }

    private PlayerHandDTO createOtherPlayerAndHighCard(String name) {
        PlayerHandDTO player = new PlayerHandDTO();
        player.setPlayerName(name);

        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("3", 'H'));
        cards.add(new CardDTO("5", 'D'));
        cards.add(new CardDTO("7", 'H'));
        cards.add(new CardDTO("9", 'D'));
        cards.add(new CardDTO("J", 'H'));

        player.setCards(cards.toArray(new CardDTO[0]));
        return player;
    }

    private CardDTO[] createMissingCardsHand() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("3", 'S'));
        cards.add(new CardDTO("4", 'S'));
        cards.add(new CardDTO("5", 'S'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createInvalidCardsHand() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("19", 'S'));
        cards.add(new CardDTO("12", 'S'));
        cards.add(new CardDTO("17", 'S'));
        cards.add(new CardDTO("15", 'S'));
        cards.add(new CardDTO("13", 'S'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createStraightFlush() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("3", 'S'));
        cards.add(new CardDTO("4", 'S'));
        cards.add(new CardDTO("5", 'S'));
        cards.add(new CardDTO("6", 'S'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createFlush() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("4", 'S'));
        cards.add(new CardDTO("6", 'S'));
        cards.add(new CardDTO("8", 'S'));
        cards.add(new CardDTO("10", 'S'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createFullHouse() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("J", 'S'));
        cards.add(new CardDTO("J", 'H'));
        cards.add(new CardDTO("Q", 'S'));
        cards.add(new CardDTO("Q", 'H'));
        cards.add(new CardDTO("Q", 'D'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createStraightFiveHigh() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("A", 'H'));
        cards.add(new CardDTO("3", 'S'));
        cards.add(new CardDTO("4", 'H'));
        cards.add(new CardDTO("5", 'S'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createStraightAceHigh() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("K", 'D'));
        cards.add(new CardDTO("A", 'C'));
        cards.add(new CardDTO("Q", 'D'));
        cards.add(new CardDTO("J", 'C'));
        cards.add(new CardDTO("10", 'D'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createOtherStraightAceHigh() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("K", 'S'));
        cards.add(new CardDTO("A", 'H'));
        cards.add(new CardDTO("Q", 'S'));
        cards.add(new CardDTO("J", 'H'));
        cards.add(new CardDTO("10", 'S'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createFourInAKind() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("K", 'D'));
        cards.add(new CardDTO("K", 'C'));
        cards.add(new CardDTO("K", 'H'));
        cards.add(new CardDTO("K", 'S'));
        cards.add(new CardDTO("10", 'D'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createOnePair() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("K", 'D'));
        cards.add(new CardDTO("K", 'C'));
        cards.add(new CardDTO("9", 'H'));
        cards.add(new CardDTO("8", 'S'));
        cards.add(new CardDTO("10", 'D'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createTwoPair() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("K", 'D'));
        cards.add(new CardDTO("K", 'C'));
        cards.add(new CardDTO("Q", 'H'));
        cards.add(new CardDTO("Q", 'S'));
        cards.add(new CardDTO("10", 'D'));
        return cards.toArray(new CardDTO[0]);
    }

    private CardDTO[] createThreeInAKind() {
        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("K", 'D'));
        cards.add(new CardDTO("K", 'C'));
        cards.add(new CardDTO("K", 'H'));
        cards.add(new CardDTO("9", 'S'));
        cards.add(new CardDTO("10", 'D'));
        return cards.toArray(new CardDTO[0]);
    }
}
