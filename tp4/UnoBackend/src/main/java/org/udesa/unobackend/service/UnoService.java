package org.udesa.unobackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.udesa.unobackend.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class UnoService {
    @Autowired private Dealer dealer;
    private Map<UUID, Match> matchSessions = new HashMap<UUID, Match>();

    public UUID newMatch( List<String> players ) {
        UUID matchID = UUID.randomUUID();
        matchSessions.put( matchID, Match.fullMatch( dealer.fullDeck(), players ) );
        return matchID;
    }
}
