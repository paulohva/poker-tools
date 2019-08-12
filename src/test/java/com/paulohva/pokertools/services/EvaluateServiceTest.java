package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.EvaluateHandsRequestDTO;
import com.paulohva.pokertools.dto.PlayerHandDTO;
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
        request.setPlayerOne(createPlayerAndHeartsCards("John"));
        request.setPlayerTwo(createPlayerAndHeartsCards("James"));

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
        request.setPlayerOne(createPlayerAndHeartsCards("John"));
        request.setPlayerTwo(createPlayerAndSpacesCards("James"));
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

    private PlayerHandDTO createPlayerAndHeartsCards(String name) {
        PlayerHandDTO player = new PlayerHandDTO();
        player.setPlayerName(name);

        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'H'));
        cards.add(new CardDTO("3", 'H'));
        cards.add(new CardDTO("4", 'H'));
        cards.add(new CardDTO("5", 'H'));
        cards.add(new CardDTO("6", 'H'));

        player.setCards(cards.toArray(new CardDTO[0]));
        return player;
    }

    private PlayerHandDTO createPlayerAndSpacesCards(String name) {
        PlayerHandDTO player = new PlayerHandDTO();
        player.setPlayerName(name);

        List<CardDTO> cards = new ArrayList<>();
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("3", 'S'));
        cards.add(new CardDTO("4", 'S'));
        cards.add(new CardDTO("5", 'S'));
        cards.add(new CardDTO("6", 'S'));

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
        cards.add(new CardDTO("2", 'S'));
        cards.add(new CardDTO("3", 'S'));
        cards.add(new CardDTO("4", 'S'));
        cards.add(new CardDTO("5", 'S'));
        cards.add(new CardDTO("13", 'S'));
        return cards.toArray(new CardDTO[0]);
    }
}
