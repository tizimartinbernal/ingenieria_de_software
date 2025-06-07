package org.udesa.unobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.unobackend.model.*;

import java.util.*;

@Service
public class UnoService {
    @Autowired private Dealer dealer;
    private Map<UUID, Match> matchSessions = new HashMap<UUID, Match>();

    public UUID newMatch( List<String> players ) {
        UUID matchID = UUID.randomUUID();
        matchSessions.put( matchID, Match.fullMatch( dealer.fullDeck(), players ) );
        return matchID;
    }

    public List<Card> playerHand(UUID matchId) {
        return Optional.ofNullable(matchSessions.get( matchId) )
                .map(Match::playerHand)
                .orElse(null);
    }

}
