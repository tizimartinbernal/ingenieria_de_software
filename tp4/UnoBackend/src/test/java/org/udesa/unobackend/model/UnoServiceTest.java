package org.udesa.unobackend.model;

import org.junit.jupiter.api.Test;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoServiceTest {
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

    
}
