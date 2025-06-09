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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
public class UnoServiceTest {
    @Autowired private UnoService unoService;
    @MockBean Dealer dealer;

    @Test
    public void newMatchTest() {
        // quiero que cuando llame a dealer.fullDeck() retorne un mazo con 3 cartas
        when(dealer.fullDeck()).thenReturn(List.of(
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
                new NumberCard("Yellow", 1)
        ));
        UUID id = unoService.newMatch(List.of("Martina", "Alex"));
        assertNotNull(id);
    }

}
