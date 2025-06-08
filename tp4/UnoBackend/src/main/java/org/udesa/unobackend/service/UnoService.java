package org.udesa.unobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.unobackend.model.*;

import java.util.*;

@Service
public class UnoService {
    @Autowired private Dealer dealer;
    private Map<UUID, Match> matchSessions = new HashMap<UUID, Match>();

    public UUID newMatch( List<String> players ) { // Debe tener sus errores específicos. Tendríamos que destacarlos?
        UUID matchID = UUID.randomUUID();
        matchSessions.put( matchID, Match.fullMatch( dealer.fullDeck(), players ) );
        return matchID;
    }

    public List<Card> playerHand(UUID matchId) {
        return Optional.ofNullable(matchSessions.get( matchId) )
                .map(Match::playerHand)
                .orElse(null);
    }

    public void playCard(UUID matchId, String player, Card card) {
        Match match = matchSessions.get( matchId );
        if (match == null) {
            throw new RuntimeException( "Match with ID " + matchId + " not found." );
        }
        try {
            match.play( player, card );
        } catch (RuntimeException e) {
            if ( e.getMessage().equals( Player.NotPlayersTurn + player ) ) {
                throw new IllegalStateException( "It is not the turn of player " + player );
            } else if ( e.getMessage().equals( Match.NotACardInHand + player ) ) {
                throw new IllegalArgumentException( "Card is not in the hand of player " + player );
            } else if ( e.getMessage().equals( Match.CardDoNotMatch ) ) {
                throw new IllegalArgumentException( "Card does not match the current card's color, number, or kind" );
            } else {
                throw new RuntimeException( "Failed to play card: " + e.getMessage() );
            }
        }
    }

    public Card activeCard(UUID matchId) {
        Match match = matchSessions.get(matchId);
        if (match == null) {
            throw new RuntimeException("Match with ID " + matchId + " not found.");
        }
        return match.activeCard(); // ¿Manjo de errores?
    }
}
