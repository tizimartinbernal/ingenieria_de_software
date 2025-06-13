package org.udesa.unobackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.udesa.unobackend.model.*;
import org.udesa.unobackend.service.UnoService;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private UnoService unoService;

    // Auxiliary Functions
    private UUID setupMatchCreation( List<String> players, UUID expectedId ) {
        when( unoService.newMatch( players) ).thenReturn( expectedId );
        return expectedId;
    }

    private void setupMatchCreationException( List<String> players, String errorMessage ) {
        when( unoService.newMatch( players ) ).thenThrow( new IllegalArgumentException( errorMessage ) );
    }

    private UUID setupActiveCard( Card card ) {
        UUID matchId = UUID.randomUUID();
        when( unoService.activeCard( matchId ) ).thenReturn( card );
        return matchId;
    }

    private UUID setupActiveCardException( UUID matchId ) {
        String errorMessage = getMatchNotFoundMessage( matchId );
        when( unoService.activeCard( matchId ) ).thenThrow( new RuntimeException( errorMessage ) );
        return matchId;
    }

    private UUID setupPlayerHand( List<Card> hand ) {
        UUID matchId = UUID.randomUUID();
        when( unoService.playerHand( matchId ) ).thenReturn( hand );
        return matchId;
    }

    private UUID setupPlayerHandException( UUID matchId ) {
        String errorMessage = getMatchNotFoundMessage( matchId );
        when( unoService.playerHand( matchId ) ).thenThrow( new RuntimeException( errorMessage ) );
        return matchId;
    }

    private UUID setupDrawCard( String player ) {
        UUID matchId = UUID.randomUUID();
        doNothing().when( unoService ).drawCard( matchId, player );
        return matchId;
    }

    private UUID setupDrawCardException( String player, Exception exception, UUID matchId ) {
        doThrow( exception ).when( unoService ).drawCard( matchId, player );
        return matchId;
    }

    private UUID setupPlayCard( String player ) {
        UUID matchId = UUID.randomUUID();
        doNothing().when( unoService ).playCard( eq( matchId ), eq( player ), any( Card.class ) );
        return matchId;
    }

    private UUID setupPlayCardException( String player, Exception exception, UUID matchId ) {
        doThrow( exception ).when( unoService ).playCard( eq( matchId ), eq( player ), any( Card.class ) );
        return matchId;
    }

    private ResultActions performNewMatchRequest( String... players ) throws Exception {
        return mockMvc.perform( post("/newmatch" )
                .contentType( MediaType.APPLICATION_FORM_URLENCODED )
                .param( "players", players ) );
    }

    private ResultActions performActiveCardRequest( UUID matchId ) throws Exception {
        return mockMvc.perform( get( "/activecard/" + matchId )
                .contentType( MediaType.APPLICATION_JSON ) );
    }

    private ResultActions performPlayerHandRequest( UUID matchId ) throws Exception {
        return mockMvc.perform( get( "/playerhand/" + matchId )
                .contentType( MediaType.APPLICATION_JSON ) );
    }

    private ResultActions performDrawCardRequest( UUID matchId, String player ) throws Exception {
        return mockMvc.perform(post( "/draw/" + matchId + "/" + player )
                .contentType( MediaType.APPLICATION_JSON ) );
    }

    private ResultActions performPlayCardRequest( UUID matchId, String player, String cardJson ) throws Exception {
        return mockMvc.perform(post( "/play/" + matchId + "/" + player )
                .contentType ( MediaType.APPLICATION_JSON )
                .content( cardJson ) );
    }

    private void expectSuccessWithContent( ResultActions result, String expectedContent ) throws Exception {
        result.andExpect( status().isOk() )
              .andExpect( content().string( expectedContent ) );
    }

    private void expectSuccessWithJson( ResultActions result, String expectedJson ) throws Exception {
        result.andExpect( status().isOk() )
                .andExpect( content().json( expectedJson ) );
    }

    private void expectBadRequestWithMessage( ResultActions result, String expectedMessage ) throws Exception {
        result.andExpect( status().isBadRequest() )
                .andExpect( content().string( "Business Error: " + expectedMessage ) );
    }

    private void expectNotFoundWithMessage( ResultActions result, String expectedMessage ) throws Exception {
        result.andExpect( status().isNotFound() )
                .andExpect( content().string( "Low-Level Error: " + expectedMessage ) );
    }

    private void expectNotFoundContaining( ResultActions result, String substring ) throws Exception {
        result.andExpect( status().isNotFound() )
                .andExpect( content().string(containsString(substring) ) );
    }

    private String getMatchNotFoundMessage( UUID matchId ) {
        return "Match with ID " + matchId + " not found.";
    }

    // Tests
    @Test public void testCreateAUnoMatch() throws Exception {
        UUID matchId = setupMatchCreation( List.of( "Mateo", "Tiziano" ), UUID.randomUUID() );

        ResultActions result = performNewMatchRequest( "Mateo", "Tiziano" );

        expectSuccessWithContent( result, "\"" + matchId.toString() + "\"" );
    }

    @Test public void testCreateNewMatchEmptyPlayersThrowsBadRequest() throws Exception {
        setupMatchCreationException( List.of(), Match.NotEnoughPlayers );

        ResultActions result = performNewMatchRequest( "" );

        expectBadRequestWithMessage( result, Match.NotEnoughPlayers );
    }

    @Test public void testCreateNewMatchWithSinglePlayerThrowsBadRequest() throws Exception {
        setupMatchCreationException( List.of( "Mateo" ), Match.NotEnoughPlayers );

        ResultActions result = performNewMatchRequest( "Mateo" );

        expectBadRequestWithMessage( result, Match.NotEnoughPlayers );
    }

    @Test public void testCreateNewMatchWithEmptyStringPlayerNamesThrowsBadRequest() throws Exception {
        setupMatchCreationException( List.of("Mateo", ""), Match.EmptyOrNullPlayers );

        ResultActions result = performNewMatchRequest( "Mateo", "" );

        expectBadRequestWithMessage( result, Match.EmptyOrNullPlayers );
    }

    @Test public void testCreateNewMatchWithBlankStringPlayerNameThrowsBadRequest() throws Exception {
        setupMatchCreationException( List.of("Mateo", "  "), Match.EmptyOrNullPlayers );

        ResultActions result = performNewMatchRequest( "Mateo", "  " );

        expectBadRequestWithMessage( result, Match.EmptyOrNullPlayers );
    }

    @Test public void testRequestTheActiveCardOfAUnoMatch() throws Exception {
        Card activeCard = new NumberCard( "Red", 5 );
        UUID matchId = setupActiveCard( activeCard );

        ResultActions result = performActiveCardRequest( matchId );

        expectSuccessWithJson( result, "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" );
    }

    @Test public void testRequestActiveCardForNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        setupActiveCardException( matchId );

        ResultActions result = performActiveCardRequest( matchId );

        expectNotFoundWithMessage( result, getMatchNotFoundMessage( matchId ) );
    }

    @Test public void testRequestPlayerHandSuccessfully() throws Exception {
        List<Card> hand = List.of( new NumberCard( "Red", 5 ), new WildCard().asColor( "Blue" ) );
        UUID matchId = setupPlayerHand( hand );

        ResultActions result = performPlayerHandRequest( matchId );

        expectSuccessWithJson( result, "[{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}," +
                                                    "{\"color\":\"Blue\",\"number\":null,\"type\":\"WildCard\",\"shout\":false}]" );
    }

    @Test public void testRequestPlayerHandNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        setupPlayerHandException( matchId );

        ResultActions result = performPlayerHandRequest( matchId );

        expectNotFoundWithMessage( result, getMatchNotFoundMessage( matchId ) );
    }

    @Test public void testDrawCardSuccessfully() throws Exception {
        UUID matchId = setupDrawCard( "Mateo" );

        ResultActions result = performDrawCardRequest( matchId, "Mateo" );

        result.andExpect( status().isOk() );
    }

    @Test public void testDrawCardInvalidTurnThrowsBadRequest() throws Exception {
        UUID matchId = UUID.randomUUID();

        setupDrawCardException( "Mateo", new IllegalArgumentException( Player.NotPlayersTurn + "Mateo" ), matchId );

        ResultActions result = performDrawCardRequest( matchId, "Mateo" );

        expectBadRequestWithMessage( result, Player.NotPlayersTurn + "Mateo" );
    }

    @Test public void testDrawCardNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        setupDrawCardException( "Mateo", new RuntimeException( getMatchNotFoundMessage( matchId ) ), matchId );

        ResultActions result = performDrawCardRequest( matchId, "Mateo" );

        expectNotFoundWithMessage( result, getMatchNotFoundMessage( matchId ) );
    }

    @Test public void testDrawCardWhenTheUnoGameIsOverThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        setupDrawCardException( "Mateo", new RuntimeException( "GameOver" ), matchId );

        ResultActions result = performDrawCardRequest( matchId, "Mateo" );

        expectNotFoundWithMessage( result, "GameOver" );
    }

    @Test public void testPlayCardSuccessfully() throws Exception {
        UUID matchId = setupPlayCard( "Mateo" );

        ResultActions result = performPlayCardRequest( matchId, "Mateo", "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" );

        result.andExpect( status().isOk() );
    }

    @Test public void testPlayCardInvalidTurnThrowsBadRequest() throws Exception {
        UUID matchId = UUID.randomUUID();

        setupPlayCardException( "Mateo", new IllegalArgumentException( Player.NotPlayersTurn + "Mateo" ), matchId );

        ResultActions result = performPlayCardRequest( matchId, "Mateo", "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" );

        expectBadRequestWithMessage( result, Player.NotPlayersTurn + "Mateo" );
    }

    @Test public void testPlayCardNonExistentMatchThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        setupPlayCardException( "Mateo", new RuntimeException(getMatchNotFoundMessage( matchId ) ), matchId );

        ResultActions result = performPlayCardRequest( matchId, "Mateo", "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" );

        expectNotFoundWithMessage( result, getMatchNotFoundMessage( matchId ) );
    }

    @Test public void testPlayCardWithInvalidColorFieldTypeThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":,\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" ;

        ResultActions result = performPlayCardRequest( matchId, "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithInvalidNumberFieldTypeThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Red\",\"number\":\"five\",\"type\":\"NumberCard\",\"shout\":\"true\"}";

        ResultActions result = performPlayCardRequest( matchId, "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithInvalidTypeFieldTypeThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"type\":\"Sword\",\"shout\":\"true\"}";

        ResultActions result = performPlayCardRequest(matchId, "Mateo", invalidJson);

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWithInvalidShoutFieldTypeThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":.}";

        ResultActions result = performPlayCardRequest(matchId, "Mateo", invalidJson);

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithoutColorFieldThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest(matchId, "Mateo", invalidJson);

        expectNotFoundContaining( result, "Low-Level Error: JSON parse error" );
    }

    @Test public void testPlayCardWithoutNumberFieldThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Red\",\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest(matchId, "Mateo", invalidJson);

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWithoutTypeFieldThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"shout\":false}";

        ResultActions result = performPlayCardRequest(matchId, "Mateo", invalidJson);

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWithoutShoutField() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\"}";

        ResultActions result = performPlayCardRequest(matchId, "Mateo", invalidJson);

        result.andExpect( status().isOk() );
    }

    @Test public void testPlayCardWithEmptyJsonCardThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        ResultActions result = performPlayCardRequest(matchId, "Mateo", "{}");

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing");
    }

    @Test public void testPlayCardWithNoJsonCardThrowsNotFound() throws Exception {
        UUID matchId = UUID.randomUUID();

        ResultActions result = performPlayCardRequest(matchId, "Mateo", "");

        expectNotFoundContaining( result, "Low-Level Error: Required request body is missing" );
    }

    @Test public void testPlayCardWithNonExistentCardTypeThrowsBadRequest() throws Exception {
        UUID matchId = UUID.randomUUID();
        String invalidJson = "{\"color\":\"Grey\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}";

        setupPlayCardException( "Mateo", new IllegalArgumentException( Match.NotACardInHand + "Mateo" ), matchId );

        ResultActions result = performPlayCardRequest( matchId, "Mateo", invalidJson );

        expectBadRequestWithMessage( result, "Not a card in hand of " + "Mateo" );
    }

    @Test public void testPlayCardWhenTheUnoGameIsOverThrowsBadRequest() throws Exception {
        UUID matchId = UUID.randomUUID();
        String JsonCard = "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" ;

        setupPlayCardException( "Mateo", new IllegalArgumentException( "GameOver" ), matchId );

        ResultActions result = performPlayCardRequest( matchId, "Mateo", JsonCard );

        expectBadRequestWithMessage( result, "GameOver" );
    }
}