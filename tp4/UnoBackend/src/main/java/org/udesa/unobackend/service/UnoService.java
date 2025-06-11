package org.udesa.unobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.udesa.unobackend.model.*;

import java.util.*;

@Service
public class UnoService {
    @Autowired private Dealer dealer;

    private final Map<UUID, Match> matchSessions = new HashMap<UUID, Match>();

    public UUID newMatch( List<String> players ) {
        UUID matchID = UUID.randomUUID();
        matchSessions.put( matchID, Match.fullMatch( dealer.fullDeck(), players ) );
        return matchID;
    }

    public void playCard(UUID matchId, String player, Card card) {
        getMatch(matchId).play( player, card);
    }

    public void drawCard(UUID matchId, String player) {
        getMatch(matchId).drawCard(player);
    }

    public Card activeCard(UUID matchId) {
        return getMatch(matchId).activeCard();
    }

    public List<Card> playerHand(UUID matchId) {
        return getMatch(matchId).playerHand();
    }

    private Match getMatch(UUID matchId) {
        Match match = matchSessions.get(matchId);
        if (match == null) {
            throw new RuntimeException( "Match with ID " + matchId + " not found." );
        }
        return match;
    }
}
