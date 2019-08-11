package com.paulohva.pokertools.controllers;

import com.paulohva.pokertools.dto.CardDTO;
import com.paulohva.pokertools.dto.HandResultDTO;
import com.paulohva.pokertools.dto.PlayerHandListDTO;
import com.paulohva.pokertools.services.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/game")
public class GameController {

    private EvaluateService evaluateService;

    @Autowired
    public GameController(EvaluateService evaluateService) {
        this.evaluateService = evaluateService;
    }

    @PostMapping(path = "/evaluatehands")
    public ResponseEntity evaluateHands(@RequestBody PlayerHandListDTO playerHandListDTO){

        List<CardDTO> allSentCards = playerHandListDTO.getPlayers()
                .stream()
                .map(x -> x.getCards())
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());

        if(!evaluateService.isCardsValid(allSentCards)) {
            // todo:return error;

            System.out.println(allSentCards.get(0).getRank());

            return  ResponseEntity.badRequest().build();
        }

        HandResultDTO handResultDTO = evaluateService.evaluateHands(playerHandListDTO);





        return new ResponseEntity<>(handResultDTO,
                HttpStatus.OK);
    }
}
