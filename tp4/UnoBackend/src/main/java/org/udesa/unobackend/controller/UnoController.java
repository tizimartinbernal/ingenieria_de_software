package org.udesa.unobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unobackend.model.Card;
import org.udesa.unobackend.model.JsonCard;
import org.udesa.unobackend.service.UnoService;

import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    @Autowired UnoService unoService;

    @PostMapping("newmatch") public ResponseEntity newMatch( @RequestParam List<String> players ) {
        return ResponseEntity.ok( unoService.newMatch( players ) );
    }

    @PostMapping("play/{matchId}/{player}") public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        try {
            Card playableCard = card.asCard();
            unoService.playCard( matchId, player, playableCard );
            return ResponseEntity.ok().build();
        } catch ( RuntimeException e ) {
            return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( e.getMessage() );
        }
    }

//    @PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @RequestParam String player ) {}
    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard(@PathVariable UUID matchId ) {
        try {
            Card activeCard = unoService.activeCard(matchId);
            return ResponseEntity.ok(activeCard.asJson());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("playerhand/{matchId}") public ResponseEntity playerHand( @PathVariable UUID matchId ) {
        System.out.println("hola");
        List<Card> hand = unoService.playerHand( matchId );

        if (hand == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body("Match with ID " + matchId + " not found.");
        }

        return ResponseEntity.ok(hand.stream().map(Card::asJson).toList());
    }
}