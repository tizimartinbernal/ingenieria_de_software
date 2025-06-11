package org.udesa.unobackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.udesa.unobackend.model.*;
import org.udesa.unobackend.service.UnoService;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private UnoService unoService;

    @Test public void testCreateAUnoMatch()  throws Exception {
        UUID generatedMatchId = UUID.randomUUID();

        when( unoService.newMatch( List.of( "Mateo", "Tiziano" ) ) ).thenReturn( generatedMatchId );

        mockMvc.perform( post( "/newmatch" )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "players", "Mateo", "Tiziano" ) )
                .andExpect( status().isOk() )
                .andExpect( content().string( "\"" + generatedMatchId.toString() + "\"" ) );
    }

    @Test public void testCreateNewMatchEmptyPlayersThrowsBadRequest() throws Exception {
        when( unoService.newMatch( List.of() ) ).thenThrow( new IllegalArgumentException( Match.NotEnoughPlayers ) );

        mockMvc.perform( post( "/newmatch" )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "players", "" ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "Business Error: " + Match.NotEnoughPlayers ) );
    }

    @Test public void testCreateNewMatchWithSinglePlayerThrowsBadRequest() throws Exception {
        when( unoService.newMatch( List.of( "Mateo" ) ) ).thenThrow( new IllegalArgumentException( Match.NotEnoughPlayers ) );

        mockMvc.perform(post( "/newmatch" )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "players", "Mateo" ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "Business Error: " + Match.NotEnoughPlayers ) );
    }

    @Test public void testCreateNewMatchWithEmptyStringPlayerNamesThrowsBadRequest() throws Exception {
        when( unoService.newMatch( List.of( "Mateo", "" ) ) ).thenThrow( new IllegalArgumentException( Match.EmptyOrNullPlayers ) );

        mockMvc.perform( post( "/newmatch" )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "players", "Mateo", "" ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "Business Error: " + Match.EmptyOrNullPlayers ) );
    }

    @Test public void testCreateNewMatchWithBlankStringPlayerNameThrowsBadRequest() throws Exception {
        when( unoService.newMatch( List.of( "Mateo", "  " ) ) ).thenThrow( new IllegalArgumentException( Match.EmptyOrNullPlayers ) );

        mockMvc.perform( post("/newmatch" )
                            .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                            .param( "players", "Mateo", "  " ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "Business Error: " + Match.EmptyOrNullPlayers ) );
    }

    @Test public void testRequestTheActiveCardOfAUnoMatch() throws Exception {
        UUID matchId = UUID.randomUUID();
        Card activeCard = new NumberCard( "Red", 5 );

        when( unoService.activeCard( matchId) ).thenReturn( activeCard );

        mockMvc.perform( get("/activecard/" + matchId )
                            .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().json( "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" ) );
    }

    @Test public void testRequestActiveCardForNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        when( unoService.activeCard( matchId ) ).thenThrow( new RuntimeException("Match with ID " + matchId + " not found." ) );

        mockMvc.perform( get( "/activecard/" + matchId )
                            .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().string( "Low-Level Error: Match with ID " + matchId + " not found." ) );
    }

    @Test public void testRequestPlayerHandSuccessfully() throws Exception {
        UUID matchId = UUID.randomUUID();
        List<Card> hand = List.of( new NumberCard( "Red", 5 ), new WildCard().asColor( "Blue" ) );

        when( unoService.playerHand( matchId ) ).thenReturn( hand );

        mockMvc.perform( get( "/playerhand/" + matchId )
                            .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() )
                .andExpect( content().json( "[{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}," +
                        "{\"color\":\"Blue\",\"number\":null,\"type\":\"WildCard\",\"shout\":false}]" ) );
    }

    @Test public void testRequestPlayerHandNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        when( unoService.playerHand( matchId ) ).thenThrow( new RuntimeException( "Match with ID " + matchId + " not found.") );

        mockMvc.perform( get( "/playerhand/" + matchId )
                            .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().string( "Low-Level Error: Match with ID " + matchId + " not found." ) );
    }

    @Test public void testDrawCardSuccessfully() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Mateo";

        doNothing().when( unoService ).drawCard( matchId, player );

        mockMvc.perform( post( "/draw/" + matchId + "/" + player )
                            .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isOk() );
    }

    @Test public void testDrawCardInvalidTurnThrowsBadRequest() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Mateo";

        doThrow( new IllegalArgumentException(Player.NotPlayersTurn + player ) )
                .when( unoService ).drawCard( matchId, player );

        mockMvc.perform( post( "/draw/" + matchId + "/" + player )
                        .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isBadRequest() )
                .andExpect( content().string( "Business Error: " + Player.NotPlayersTurn + player ) );
    }

    @Test public void testDrawCardNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Mateo";

        doThrow( new RuntimeException( "Match with ID " + matchId + " not found." ) )
                .when( unoService ).drawCard( matchId, player );

        mockMvc.perform( post( "/draw/" + matchId + "/" + player )
                        .contentType( MediaType.APPLICATION_JSON ) )
                .andExpect( status().isNotFound() )
                .andExpect( content().string( "Low-Level Error: Match with ID " + matchId + " not found." ) );
    }

    @Test
    public void testPlayCardSuccessfully() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Mateo";
        JsonCard jsonCard = new JsonCard("Red", 5, "NumberCard", false);
        doNothing().when(unoService).playCard(eq(matchId), eq(player), any(Card.class));
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testPlayCardInvalidTurnThrowsBadRequest() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Mateo";
        JsonCard jsonCard = new JsonCard("Red", 5, "NumberCard", false);
        doThrow(new IllegalArgumentException(Player.NotPlayersTurn + player))
                .when(unoService).playCard(eq(matchId), eq(player), any(Card.class));
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Business Error: " + Player.NotPlayersTurn + player));
    }

    @Test
    public void testPlayCardNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String player = "Mateo";
        JsonCard jsonCard = new JsonCard("Red", 5, "NumberCard", false);
        doThrow(new RuntimeException("Match with ID " + matchId + " not found."))
                .when(unoService).playCard(eq(matchId), eq(player), any(Card.class));
        mockMvc.perform(post("/play/" + matchId + "/" + player)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Low-Level Error: Match with ID " + matchId + " not found."));
    }

}
