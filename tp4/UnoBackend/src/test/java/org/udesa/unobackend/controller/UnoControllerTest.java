package org.udesa.unobackend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.udesa.unobackend.model.*;
import org.udesa.unobackend.service.Dealer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.matchesPattern;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UnoControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private Dealer dealer;

    @BeforeEach public void setUp() {
        when( dealer.fullDeck() ).thenReturn( new ArrayList<>( createPredictableDeck() ) );
    }

    private ResultActions performNewMatchRequest( String... players ) throws Exception {
        return mockMvc.perform( post( "/newmatch" )
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
        return mockMvc.perform( post( "/draw/" + matchId + "/" + player )
                .contentType( MediaType.APPLICATION_JSON ) );
    }

    private ResultActions performPlayCardRequest( UUID matchId, String player, String cardJson ) throws Exception {
        return mockMvc.perform( post( "/play/" + matchId + "/" + player )
                .contentType( MediaType.APPLICATION_JSON )
                .content( cardJson ) );
    }

    private ResultActions performFullGameRequest( UUID matchId, String player, String color) throws Exception {
        for (int i = 0; i < 5; i++) {
            performPlayCardRequest( matchId, player, "{\"color\":\"" + color + "\",\"number\":null,\"type\":\"SkipCard\",\"shout\":false}");
        }

        performPlayCardRequest( matchId, player, "{\"color\":\"" + color + "\",\"number\":null,\"type\":\"SkipCard\",\"shout\":true}");

        return performPlayCardRequest( matchId, player, "{\"color\":\"" + color + "\",\"number\":null,\"type\":\"SkipCard\",\"shout\":false}" );
    }

    private void expectSuccessWithUUID( ResultActions result ) throws Exception {
        result.andExpect( status( ).isOk( ) )
              .andExpect( content().string( matchesPattern( "\"[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\"" ) ) );
    }

    private void expectSuccessWithJson( ResultActions result, String expectedJson ) throws Exception {
        result.andExpect( status( ).isOk( ) )
                .andExpect( content( ).json( expectedJson ) );
    }

    private void expectBadRequestWithMessage( ResultActions result, String expectedMessage ) throws Exception {
        result.andExpect( status( ).isBadRequest( ) )
                .andExpect( content( ).string( "Business Error: " + expectedMessage ) );
    }

    private void expectNotFoundWithMessage( ResultActions result, String expectedMessage ) throws Exception {
        result.andExpect( status( ).isNotFound( ) )
                .andExpect( content( ).string( "Low-Level Error: " + expectedMessage ) );
    }

    private void expectNotFoundContaining( ResultActions result, String substring ) throws Exception {
        result.andExpect( status( ).isNotFound( ) )
                .andExpect( content( ).string( containsString( substring ) ) );
    }

    private String extractMatchIdFromResponse( ResultActions result ) throws Exception {
        String response = result.andReturn( ).getResponse( ).getContentAsString( );
        return response.replace( "\"", "" );
    }

    private List<Card> createPredictableDeck( ) {
        List<Card> deck = new ArrayList<>( );

        // Discard pile head
        deck.add( new NumberCard( "Red", 5 ) );

        // First player hand
        deck.add( new SkipCard( "Red" ) );
        deck.add( new ReverseCard( "Red" ) );
        deck.add( new Draw2Card( "Red" ) );
        deck.add( new WildCard() );
        deck.add( new NumberCard( "Red", 6 ) );
        deck.add( new NumberCard( "Red", 7 ) );
        deck.add( new NumberCard( "Red", 8 ) );

        // Second player hand
        deck.add( new SkipCard( "Blue" ) );
        deck.add( new ReverseCard( "Blue" ) );
        deck.add( new Draw2Card( "Blue" ) );
        deck.add( new WildCard() );
        deck.add( new NumberCard( "Blue", 6 ) );
        deck.add( new NumberCard( "Blue", 7 ) );
        deck.add( new NumberCard( "Blue", 8 ) );

        // Extra cards for drawing
        deck.add( new NumberCard( "Red", 0 ) );
        deck.add( new NumberCard( "Red", 1 ) );
        deck.add( new NumberCard( "Red", 2 ) );

        return deck;
    }

    private List<Card> createGameOverDeck() {
        List<Card> deck = new ArrayList<>( );

        // Discard pile head
        deck.add( new NumberCard( "Red", 5 ) );

        // First player hand
        for (int i = 0; i < 7; i++) { deck.add( new SkipCard( "Red" ) ); }

        // Second player hand
        for (int i = 0; i < 7; i++) { deck.add( new NumberCard( "Red", i ) ); }

        // Extra cards for drawing
        deck.add( new ReverseCard( "Blue" ) );
        deck.add( new Draw2Card( "Green" ) );
        deck.add( new WildCard() );

        return deck;
    }

    @Test public void testCreateAUnoMatchSuccessfully() throws Exception {
        ResultActions result = performNewMatchRequest( "Mateo", "Tiziano" );

        expectSuccessWithUUID( result );
    }

    @Test public void testCreateNewMatchEmptyPlayersThrowsBadRequest() throws Exception {
        ResultActions result = performNewMatchRequest( "" );

        expectBadRequestWithMessage( result, Match.NotEnoughPlayers );
    }

    @Test public void testCreateNewMatchWithSinglePlayerThrowsBadRequest() throws Exception {
        ResultActions result = performNewMatchRequest( "Mateo" );

        expectBadRequestWithMessage( result, Match.NotEnoughPlayers );
    }

    @Test public void testCreateNewMatchWithEmptyStringPlayerNamesThrowsBadRequest() throws Exception {
        ResultActions result = performNewMatchRequest( "Mateo", "Tiziano", "" );

        expectBadRequestWithMessage( result, Match.EmptyOrNullPlayers );
    }

    @Test public void testCreateNewMatchWithBlankStringPlayerNameThrowsBadRequest() throws Exception {
        ResultActions result = performNewMatchRequest( "Mateo", "Tiziano", "  " );

        expectBadRequestWithMessage( result, Match.EmptyOrNullPlayers );
    }

    @Test public void testRequestTheActiveCardOfAUnoMatchSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions result = performActiveCardRequest( UUID.fromString( matchId ) );

        expectSuccessWithJson( result, "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" );
    }

    @Test public void testRequestActiveCardForNonExistentMatchThrowsNotFound() throws Exception {
        UUID fakeMatchId = UUID.randomUUID();

        ResultActions result = performActiveCardRequest( fakeMatchId );

        expectNotFoundWithMessage( result, "Match with ID " + fakeMatchId + " not found." );
    }

    @Test public void testRequestPlayerHandSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions result = performPlayerHandRequest( UUID.fromString( matchId ) );

        result.andExpect( status().isOk() )
                .andExpect( jsonPath( "$.length()" ).value( 7 ) );
    }

    @Test public void testRequestPlayerHandNonExistentMatchThrowsNotFound() throws Exception {
        UUID fakeMatchId = UUID.randomUUID();

        ResultActions result = performPlayerHandRequest( fakeMatchId );

        expectNotFoundWithMessage( result, "Match with ID " + fakeMatchId + " not found." );
    }

    @Test public void testDrawCardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions drawCardResult = performDrawCardRequest( UUID.fromString( matchId ), "Mateo" );

        drawCardResult.andExpect( status().isOk() );

        ResultActions checkHandResult = performPlayerHandRequest( UUID.fromString( matchId ) );

        checkHandResult.andExpect( status().isOk() )
                .andExpect( jsonPath( "$.length()" ).value( 8 ) );
    }

    @Test public void testDrawCardInvalidTurnThrowsBadRequest() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions result = performDrawCardRequest( UUID.fromString( matchId ), "Tiziano" );

        expectBadRequestWithMessage( result, Player.NotPlayersTurn + "Tiziano" );
    }

    @Test public void testDrawCardNonExistentMatchThrowsNotFound() throws Exception {
        UUID fakeMatchId = UUID.randomUUID();

        ResultActions result = performDrawCardRequest( fakeMatchId, "Mateo" );

        expectNotFoundWithMessage( result, "Match with ID " + fakeMatchId + " not found." );
    }

    @Test public void testDrawCardWhenTheUnoMatchIsOverThrowsBadRequest() throws Exception {
        when( dealer.fullDeck() ).thenReturn( new ArrayList<>( createGameOverDeck() ) );

        ResultActions createResult = performNewMatchRequest("Mateo", "Tiziano");
        String matchId = extractMatchIdFromResponse(createResult);

        performFullGameRequest( UUID.fromString(matchId), "Mateo", "Red" );

        ResultActions result = performDrawCardRequest( UUID.fromString(matchId), "Mateo");

        expectBadRequestWithMessage(result, "GameOver.");
    }

    @Test public void testPlayNumberCardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions playResult = performPlayCardRequest( UUID.fromString( matchId ), "Mateo",
                "{\"color\":\"Red\",\"number\":6,\"type\":\"NumberCard\",\"shout\":false}" );

        playResult.andExpect( status().isOk() );

        ResultActions activeCardResult = performActiveCardRequest( UUID.fromString( matchId ) );

        expectSuccessWithJson( activeCardResult, "{\"color\":\"Red\",\"number\":6,\"type\":\"NumberCard\",\"shout\":false}" );
    }

    @Test public void testPlaySkipCardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions playResult = performPlayCardRequest( UUID.fromString( matchId ), "Mateo",
                "{\"color\":\"Red\",\"number\":null,\"type\":\"SkipCard\",\"shout\":false}" );

        playResult.andExpect( status().isOk() );

        ResultActions activeCardResult = performActiveCardRequest( UUID.fromString( matchId ) );

        expectSuccessWithJson( activeCardResult, "{\"color\":\"Red\",\"number\":null,\"type\":\"SkipCard\",\"shout\":false}" );

        ResultActions checkHandResult = performPlayerHandRequest( UUID.fromString( matchId ) );

        checkHandResult.andExpect( status().isOk() )
                .andExpect( jsonPath( "$.length()" ).value( 6 ) );
    }

    @Test public void testPlayReverseCardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions playResult = performPlayCardRequest( UUID.fromString( matchId ), "Mateo",
                "{\"color\":\"Red\",\"number\":null,\"type\":\"ReverseCard\",\"shout\":false}" );

        playResult.andExpect( status().isOk() );

        ResultActions activeCardResult = performActiveCardRequest( UUID.fromString( matchId ) );

        expectSuccessWithJson( activeCardResult, "{\"color\":\"Red\",\"number\":null,\"type\":\"ReverseCard\",\"shout\":false}" );
    }

    @Test public void testPlayDraw2CardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions playResult = performPlayCardRequest( UUID.fromString( matchId ), "Mateo",
                "{\"color\":\"Red\",\"number\":null,\"type\":\"Draw2Card\",\"shout\":false}" );

        playResult.andExpect( status().isOk() );

        ResultActions activeCardResult = performActiveCardRequest( UUID.fromString( matchId ) );

        expectSuccessWithJson( activeCardResult, "{\"color\":\"Red\",\"number\":null,\"type\":\"Draw2Card\",\"shout\":false}" );

        ResultActions checkHandResult = performPlayerHandRequest( UUID.fromString( matchId ) );

        checkHandResult.andExpect( status().isOk() )
                .andExpect( jsonPath( "$.length()" ).value( 9 ) );
    }

    @Test public void testPlayWildCardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions playResult = performPlayCardRequest( UUID.fromString( matchId ), "Mateo",
                "{\"color\":\"Blue\",\"number\":null,\"type\":\"WildCard\",\"shout\":false}" );

        playResult.andExpect( status().isOk() );

        ResultActions activeCardResult = performActiveCardRequest( UUID.fromString( matchId ) );

        expectSuccessWithJson( activeCardResult, "{\"color\":\"Blue\",\"number\":null,\"type\":\"WildCard\",\"shout\":false}" );
    }

    @Test public void testPlayCardInvalidTurnThrowsBadRequest( ) throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Tiziano",
                "{\"color\":\"Red\",\"number\":1,\"type\":\"NumberCard\",\"shout\":false}" );

        expectBadRequestWithMessage( result, Player.NotPlayersTurn + "Tiziano" );
    }

    @Test public void testPlayCardNonExistentMatchThrowsNotFound() throws Exception {
        UUID fakeMatchId = UUID.randomUUID();

        ResultActions result = performPlayCardRequest( fakeMatchId, "Mateo",
                "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":false}" );

        expectNotFoundWithMessage( result, "Match with ID " + fakeMatchId + " not found." );
    }

    @Test public void testPlayCardWithInvalidColorFieldTypeThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":,\"number\":5,\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithInvalidNumberFieldTypeThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":\"five\",\"type\":\"NumberCard\",\"shout\":\"true\"}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithInvalidTypeFieldTypeThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"type\":.,\"shout\":\"true\"}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithInvalidShoutFieldTypeThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"type\":\"NumberCard\",\"shout\":.}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithoutColorFieldThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"number\":5,\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithoutNumberFieldThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWithoutTypeFieldThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWithoutShoutFieldWhenIsNotThePenultimateCardSuccessfully() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":6,\"type\":\"NumberCard\"}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        result.andExpect( status().isOk() );
    }

    @Test public void testPlayCardWithEmptyJsonCardThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", "{}" );

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWithNoJsonCardThrowsNotFound() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", "" );

        expectNotFoundContaining( result, "Low-Level Error" );
    }

    @Test public void testPlayCardWithNonExistentColorThrowsBadRequest() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Purple\",\"number\":1,\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectBadRequestWithMessage( result, "Not a card in hand of Mateo" );
    }

    @Test public void testPlayCardWithNonExistentNumberThrowsBadRequest() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":99,\"type\":\"NumberCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectBadRequestWithMessage( result, "Not a card in hand of Mateo" );
    }

    @Test public void testPlayCardWithNonExistentCardTypeThrowsBadRequest() throws Exception {
        ResultActions createResult = performNewMatchRequest( "Mateo", "Tiziano" );
        String matchId = extractMatchIdFromResponse( createResult );

        String invalidJson = "{\"color\":\"Red\",\"number\":5,\"type\":\"NoneTypeCard\",\"shout\":false}";

        ResultActions result = performPlayCardRequest( UUID.fromString( matchId ), "Mateo", invalidJson );

        expectNotFoundWithMessage( result, "JSON parse error: The `number` or `type` value is incorrect or missing" );
    }

    @Test public void testPlayCardWhenTheUnoMatchIsOverThrowsBadRequest() throws Exception {
        when( dealer.fullDeck() ).thenReturn( new ArrayList<>( createGameOverDeck() ) );

        ResultActions createResult = performNewMatchRequest("Mateo", "Tiziano");
        String matchId = extractMatchIdFromResponse(createResult);

        performFullGameRequest( UUID.fromString(matchId), "Mateo", "Red" );

        ResultActions result = performPlayCardRequest(UUID.fromString(matchId), "Mateo",
                "{\"color\":\"Red\",\"number\":8,\"type\":\"NumberCard\",\"shout\":false}");

        expectBadRequestWithMessage(result, "GameOver.");
    }

}