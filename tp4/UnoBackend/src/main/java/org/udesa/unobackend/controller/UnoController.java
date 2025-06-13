package org.udesa.unobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unobackend.model.Card;
import org.udesa.unobackend.model.JsonCard;
import org.udesa.unobackend.service.UnoService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

@Controller
public class UnoController {
    @Autowired UnoService unoService;

    @ExceptionHandler( RuntimeException.class ) public ResponseEntity<String> handleRuntime( RuntimeException exception ) {
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Low-Level Error: " + exception.getMessage() );
    }

    @ExceptionHandler( IllegalArgumentException.class ) public ResponseEntity<String> handleIllegal( IllegalArgumentException exception ) {
        return ResponseEntity.status( HttpStatus.BAD_REQUEST ).body( "Business Error: " + exception.getMessage() );
    }

    @ExceptionHandler({ClassNotFoundException.class, NoSuchMethodException.class, InvocationTargetException.class})
    public ResponseEntity<String> handleReflectionExceptions(Exception exception) {
        return ResponseEntity.status( HttpStatus.NOT_FOUND ).body( "Low-Level Error: JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @PostMapping("newmatch") public ResponseEntity newMatch( @RequestParam List<String> players ) {
        return ResponseEntity.ok( unoService.newMatch( players ) );
    }

    @PostMapping("play/{matchId}/{player}") public ResponseEntity play( @PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {
        unoService.playCard( matchId, player, card.asCard() );
        return ResponseEntity.ok().build();
    }

    @PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @PathVariable String player ) {
        unoService.drawCard( matchId, player );
        return ResponseEntity.ok().build();
    }

    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard( @PathVariable UUID matchId ) {
        return ResponseEntity.ok( unoService.activeCard(matchId).asJson() ); // ¿Debemos devolver la carta como Json desde el service o lo dejamos así?
    }

    @GetMapping("playerhand/{matchId}") public ResponseEntity playerHand( @PathVariable UUID matchId ) {
        return ResponseEntity.ok( unoService.playerHand(matchId).stream().map(Card::asJson).toList() );
    }
}