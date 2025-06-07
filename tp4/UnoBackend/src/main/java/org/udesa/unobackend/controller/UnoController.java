package org.udesa.unobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.udesa.unobackend.service.UnoService;

import java.util.List;

@Controller
public class UnoController {
    @Autowired UnoService unoService;

    @PostMapping("newmatch") public ResponseEntity newMatch( @RequestParam List<String> players ) {
        return ResponseEntity.ok( unoService.newMatch( players ) );
    }

//    @PostMapping("play/{matchId}/{player}") public ResponseEntity play(@PathVariable UUID matchId, @PathVariable String player, @RequestBody JsonCard card ) {}
//    @PostMapping("draw/{matchId}/{player}") public ResponseEntity drawCard( @PathVariable UUID matchId, @RequestParam String player ) {}
//    @GetMapping("activecard/{matchId}") public ResponseEntity activeCard(@PathVariable UUID matchId ) {}
//    @GetMapping("playerhand/{matchId}") public ResponseEntity playerHand( @PathVariable UUID matchId ) {}
}