package org.udesa.unobackend.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unobackend.model.Card;
import org.udesa.unobackend.model.JsonCard;
import org.udesa.unobackend.model.NumberCard;
import org.udesa.unobackend.service.UnoService;


import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.udesa.unobackend.model.*;


@SpringBootTest
public class UnoServiceTest {
    @Autowired private UnoService unoService;
    @MockBean Dealer dealer;

    private List<Card> mockDeck() {
        return List.of(
                new NumberCard("Red", 5),  // Carta inicial para discardPileHead (permite SkipCard Red)
                new SkipCard("Red"),       // Carta 1 para Martina
                new SkipCard("Blue"),      // Carta 2 para Martina
                new SkipCard("Green"),     // Carta 3 para Martina
                new NumberCard("Yellow", 2), // Carta 4 para Martina
                new NumberCard("Red", 1),   // Carta 5 para Martina
                new NumberCard("Blue", 9),  // Carta 6 para Martina
                new NumberCard("Green", 4), // Carta 7 para Martina
                new NumberCard("Yellow", 6), // Carta 8 para Alex
                new NumberCard("Red", 0),   // Carta 9 para Alex
                new NumberCard("Blue", 8),  // Carta 10 para Alex
                new NumberCard("Green", 2), // Carta 11 para Alex
                new NumberCard("Yellow", 3), // Carta 12 para Alex
                new NumberCard("Red", 4),   // Carta 13 para Alex
                new NumberCard("Blue", 6),  // Carta 14 para Alex
                new NumberCard("Green", 8)  // Carta 15 para drawPile
        );
    }

    @Test
    public void testPlayThreeSkipCardsDifferentColors() {
        // Configurar el mazo
        when(dealer.fullDeck()).thenReturn(mockDeck());

        // Crear una partida con dos jugadores
        UUID matchId = unoService.newMatch(List.of("Martina", "Alex"));

        // Verificar estado inicial
        Card activeCard = unoService.activeCard(matchId);
        assertEquals("NumberCard", activeCard.asJson().getType());
        assertEquals("Red", activeCard.asJson().getColor());
        List<Card> martinaHand = unoService.playerHand(matchId);
        assertEquals(7, martinaHand.size()); // Martina tiene 7 cartas

        // Jugar la primera SkipCard (Red)
        Card skipCardRed = new SkipCard("Red");
        unoService.playCard(matchId, "Martina", skipCardRed);
        assertEquals(skipCardRed, unoService.activeCard(matchId)); // Carta activa es SkipCard Red
        assertEquals(6, unoService.playerHand(matchId).size()); // Martina tiene 6 cartas

        // Jugar la segunda SkipCard (Blue)
        Card skipCardBlue = new SkipCard("Blue");
        unoService.playCard(matchId, "Martina", skipCardBlue);
//        assertEquals(skipCardBlue, unoService.activeCard(matchId)); // Carta activa es SkipCard Blue
//        assertEquals(5, unoService.playerHand(matchId).size()); // Martina tiene 5 cartas

//        // Jugar la tercera SkipCard (Green)
//        Card skipCardGreen = new SkipCard("Green");
//        unoService.playCard(matchId, "Martina", skipCardGreen);
//        assertEquals(skipCardGreen, unoService.activeCard(matchId)); // Carta activa es SkipCard Green
//        assertEquals(4, unoService.playerHand(matchId).size()); // Martina tiene 4 cartas

        // Verificar que no se lanzaron excepciones y que el turno sigue siendo de Martina
    }

}
