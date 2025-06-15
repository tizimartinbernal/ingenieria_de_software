package org.udesa.unobackend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.udesa.unobackend.model.Card;

import org.udesa.unobackend.model.NumberCard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@SpringBootTest
public class UnoServiceTest {
    @Autowired private UnoService unoService;
    @MockBean Dealer dealer;

    private List<Card> redDeck;
    private UUID id;

    @BeforeEach
    public void setup() {
        redDeck = new ArrayList<>();
        for (int i = 0; i < 7 * 2 + 2; i++) {redDeck.add(new NumberCard("Red", i));}
        when(dealer.fullDeck()).thenReturn(redDeck);
        id = unoService.newMatch(List.of("Alex", "Martina"));

    }

    @Test public void createsNewMatchSuccessfully() {
        assertNotNull(id);
        verify(dealer).fullDeck();
    }

    @Test public void playerHandNotEmptyAtBeginning() {
        List<Card> hand = unoService.playerHand(id);
        assertFalse(hand.isEmpty());
    }

    @Test public void playerHasSevenCardsAtStart() {
        assertEquals(7, unoService.playerHand(id).size());
    }

    @Test
    public void accessingUnknownMatchThrowsException() {
        UUID fakeId = UUID.randomUUID();
        RuntimeException ex = assertThrows(RuntimeException.class, () -> unoService.playerHand(fakeId));
        assertEquals("Match with ID " + fakeId + " not found.", ex.getMessage());
    }

    @Test
    public void multipleMatchesAreIndependent() {
        List<Card> shuffledDeck = new ArrayList<>(redDeck);
        java.util.Collections.shuffle(shuffledDeck);
        when(dealer.fullDeck()).thenReturn(shuffledDeck);
        UUID id2 = unoService.newMatch(List.of("Tiziano", "Mateo"));
        assertNotEquals(id, id2);
        assertNotEquals(unoService.playerHand(id), unoService.playerHand(id2));
    }

    @Test
    public void playingCardReducesPlayerHand() {
        unoService.playCard(id, "Alex", new NumberCard("Red", 1));
        unoService.playCard(id, "Martina", new NumberCard("Red", 13));
        assertEquals(6, unoService.playerHand(id).size());
    }

    @Test
    public void drawingCardIncreasesPlayerHand() {
        unoService.drawCard(id, "Alex");
        assertEquals(8, unoService.playerHand(id).size());
    }

    @Test public void activeCardIsInitiallyRedZero() {
        assertEquals(new NumberCard("Red", 0), unoService.activeCard(id));
    }

    @Test public void activeCardChangesAfterPlaying() {
        Card r1 = new NumberCard("Red", 1);
        Card activeCard = unoService.activeCard(id);
        unoService.playCard(id, "Alex", r1);
        Card newActiveCard = unoService.activeCard(id);
        assertNotEquals(activeCard, newActiveCard);
        assertEquals(r1, newActiveCard);
    }
}
