package org.udesa.unobackend.model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unobackend.service.UnoService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private UnoService unoService; // ¿Esta bien que sea @MockBean?

    @Test public void test00CreateAUnoMAtch()  throws Exception {
        UUID generatedMatchId = UUID.randomUUID();

        when(unoService.newMatch(List.of("Mateo", "Tiziano"))).thenReturn(generatedMatchId);

        mockMvc.perform(post("/newmatch")
                            .param("players", "Mateo", "Tiziano")
                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                )
                .andExpect(status().isOk())
                .andExpect(content().string("\"" + generatedMatchId.toString() + "\""));

    }

    @Test public void test01GetPlayerHand() throws Exception {
        UUID matchId = UUID.randomUUID();

        Card dummyCard = Mockito.mock(Card.class);
        JsonCard jsonCard = new JsonCard("Red", 5, "NumberCard", false);
        when(dummyCard.asJson()).thenReturn(jsonCard);

        when(unoService.playerHand(matchId)).thenReturn(List.of(dummyCard));

        mockMvc.perform(get("/playerhand/" + matchId))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"color\": \"Red\", \"number\": 5, \"type\": \"NumberCard\", \"shout\": false}]"));
    }

    @Test public void test02GetPlayerHandButTheMatchDoesNotExist() throws Exception {
        UUID matchId = UUID.randomUUID();

        when(unoService.playerHand(matchId)).thenReturn(null);

        mockMvc.perform(get("/playerhand/" + matchId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Match with ID " + matchId + " not found."));
    }
}
