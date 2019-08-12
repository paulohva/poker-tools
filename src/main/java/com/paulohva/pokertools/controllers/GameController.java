package com.paulohva.pokertools.controllers;


import com.paulohva.pokertools.dto.EvaluateHandsResultDTO;
import com.paulohva.pokertools.dto.EvaluateHandsRequestDTO;
import com.paulohva.pokertools.services.EvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/game")
public class GameController {

    private EvaluateService evaluateService;

    @Autowired
    public GameController(EvaluateService evaluateService) {
        this.evaluateService = evaluateService;
    }

    @PostMapping(path = "/evaluatehands")
    public ResponseEntity evaluateHands(@RequestBody @Valid EvaluateHandsRequestDTO evaluateHandsRequestDTO){
        // exception handler catch if anything wrong
        evaluateService.verifyAllCardsValid(evaluateHandsRequestDTO);
        evaluateHandsRequestDTO = evaluateService.orderAndSortCards(evaluateHandsRequestDTO);

        EvaluateHandsResultDTO handResultDTO = evaluateService.evaluateHands(evaluateHandsRequestDTO);

        return new ResponseEntity<>(handResultDTO, HttpStatus.OK);
    }
}
