package com.paulohva.pokertools.services;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.HandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluateServiceImpl implements EvaluateService{
    @Override
    public HandResultDTO evaluateHands(PlayerHandListDTO playerHandListDTO) {
        return null;
    }

    @Override
    public boolean isCardsValid(List<CardDTO> cards) {



        return false;
    }
}
