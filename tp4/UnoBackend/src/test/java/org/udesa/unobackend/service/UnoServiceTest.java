package org.udesa.unobackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.udesa.unobackend.model.Card;

import org.udesa.unobackend.model.NumberCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



@SpringBootTest
public class UnoServiceTest {
    @Autowired private UnoService unoService;
    @MockBean Dealer dealer;

    @BeforeEach
    public void setup() {
        List<Card> testDeck = List.of(
                new NumberCard("Red", 5),
                new NumberCard("Blue", 3),
                new NumberCard("Green", 7),
                new NumberCard("Yellow", 2),
                new NumberCard("Red", 1),
                new NumberCard("Blue", 9),
                new NumberCard("Green", 4),
                new NumberCard("Yellow", 6),
                new NumberCard("Red", 0),
                new NumberCard("Blue", 8),
                new NumberCard("Green", 2),
                new NumberCard("Yellow", 3),
                new NumberCard("Red", 4),
                new NumberCard("Blue", 6),
                new NumberCard("Green", 8),
                new NumberCard("Yellow", 5)
        );
        when(dealer.fullDeck()).thenReturn(testDeck);
    }

    @Test public void newMatchTest() {
        UUID id = unoService.newMatch(List.of("Alex", "Martina"));
        assertNotNull(id);
        Mockito.verify(dealer).fullDeck();
        List<Card> hand = unoService.playerHand(id);
        assertFalse(hand.isEmpty());
    }

    @Test
    public void testAccessingUnknownMatchThrowsException() {
        UUID fakeId = UUID.randomUUID();
        RuntimeException ex = assertThrows(RuntimeException.class, () -> unoService.playerHand(fakeId));
        assertEquals("Match with ID " + fakeId + " not found.", ex.getMessage());
    }

    @Test
    public void testMultipleMatchesIndependence() {
        UUID id1 = unoService.newMatch(List.of("Alex", "Martina"));

        List<Card> shuffledDeck = new ArrayList<>(dealer.fullDeck());
        java.util.Collections.shuffle(shuffledDeck);

        when(dealer.fullDeck()).thenReturn(shuffledDeck);

        UUID id2 = unoService.newMatch(List.of("Tiziano", "Mateo"));
        assertNotEquals(id1, id2);
        assertNotNull(id1);
        assertNotNull(id2);

        List<Card> hand1 = unoService.playerHand(id1);
        List<Card> hand2 = unoService.playerHand(id2);
        assertFalse(hand1.isEmpty());
        assertFalse(hand2.isEmpty());
        assertNotEquals(hand1, hand2);
    }

    @Test
    public void testPlayerPlaysCard(){
        List<Card> redDeck = new ArrayList<>();
        // esto es muy villero?
        for (int i = 0; i < 7 * 2 + 1; i++) {redDeck.add(new NumberCard("Red", 0));}
        when(dealer.fullDeck()).thenReturn(redDeck);
        UUID matchId = unoService.newMatch(List.of("Alex", "Martina"));
        assertEquals(7, unoService.playerHand(matchId).size());
        unoService.playCard(matchId, "Alex", new NumberCard("Red", 0));
        assertEquals(7, unoService.playerHand(matchId).size());
        unoService.playCard(matchId, "Martina", new NumberCard("Red", 0));
        assertEquals(6, unoService.playerHand(matchId).size());
    }

    @Test
    public void testPlayerDrawsCard() {
        UUID matchId = unoService.newMatch(List.of("Alex", "Martina"));
        assertEquals(7, unoService.playerHand(matchId).size());
        unoService.drawCard(matchId, "Alex");
        assertEquals(8, unoService.playerHand(matchId).size());
    }

    @Test public void testActiveCard(){
        List<Card> redDeck = new ArrayList<>();
        for (int i = 0; i < 7 * 2 + 1; i++) {redDeck.add(new NumberCard("Red", i));}
        when(dealer.fullDeck()).thenReturn(redDeck);
        UUID matchId = unoService.newMatch(List.of("Alex", "Martina"));

        Card activeCard = unoService.activeCard(matchId);
        assertEquals(new NumberCard("Red", 0), activeCard);

        unoService.playCard(matchId, "Alex", new NumberCard("Red", 1));
        Card newActiveCard = unoService.activeCard(matchId);
        assertNotEquals(activeCard, newActiveCard);
        assertEquals(new NumberCard("Red", 1), newActiveCard);

    }
}
