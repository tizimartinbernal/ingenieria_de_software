package uno;

import java.util.*;

public class Game {
    private final String errorClosedGame = "El juego ya terminó";
    private final String nonExistentPlayer = "Jugador inexistente: ";

    private boolean gameIsClosed = false;
    private final LinkedList<Card> cardDeck = new LinkedList<>();
    private final LinkedList<Player> players = new LinkedList<>();
    private Card pileCard;

    public Game(List<Card> cards, int numberToDeal, String... playerNames) {
        cardDeck.addAll(cards);
        this.pileCard = cardDeck.removeFirst();

        this.players.addAll(
                Arrays.stream(playerNames)
                        .map(name -> {
                            List<Card> hand = new ArrayList<>(cardDeck.subList(0, numberToDeal));
                            cardDeck.subList(0, numberToDeal).clear();
                            return new Player(name, hand);
                        })
                        .toList()
        );
    }

    public Card getPileCard() { return pileCard; }

    public Game playCard(Card card, String playerName) {
        if (gameIsClosed) { throw new Error(errorClosedGame); }

        Player currentPlayer = players.getFirst();

        if (!currentPlayer.getName().equals(playerName) || !currentPlayer.hasCard(card)) {throw new Error("No es tu turno o no tienes esa carta: " + playerName);}

        if (card.canStackOn(pileCard)) {
            pileCard = card;
            currentPlayer.removeCard(card);

            if (card.getUnoState() && currentPlayer.getHand().size() > 1) { throw new Error( "No se puede cantar Uno en este momento de la partida" ); }

            if (!card.getUnoState() && currentPlayer.getHand().size() == 1) {
                pickCardFromCardDeck(currentPlayer);
                pickCardFromCardDeck(currentPlayer);
            }

            if (currentPlayer.getHand().isEmpty()) { gameIsClosed = true; }

            return card.cardAction(this);

        }

        throw new Error( "No puedes jugar esa carta: " + card + " / " + pileCard);
    }

    public Game pickCard(String playerName) { // Pude faltar implementar
        if (gameIsClosed) { throw new Error(errorClosedGame); }

        Player currentPlayer = players.getFirst();
        Player player = players.stream()
                .filter(p -> p.getName().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new Error(nonExistentPlayer + playerName));

        if (player != currentPlayer) { throw new Error("No es tu turno: " + playerName); }

        Card raisedCard = cardDeck.removeFirst();
        if (raisedCard.canStackOn(pileCard)) {
            pileCard = raisedCard;
            return raisedCard.cardAction(this);
        } else {
            currentPlayer.addCard(raisedCard);
            advanceTurn();
            return this;
        }
    }


    private void advanceTurn() {
        players.addLast(players.removeFirst());
    }

    private void pickCardFromCardDeck(Player player) { // Creo que no hace falta
        if (cardDeck.isEmpty()) {
            throw new Error("No quedan cartas en el mazo");
        }
        player.addCard(cardDeck.removeFirst());
    }

    public Game numberedCardAction() {
        advanceTurn();
        return this;
    }

    public Game drawTwoCardAction() {
        advanceTurn();
        pickCardFromCardDeck(players.getFirst());
        pickCardFromCardDeck(players.getFirst());
        return this;
    }

    public Game skipCardAction() {
        advanceTurn();
        advanceTurn();
        return this;
    }

    public Game reverseCardAction() {
        Collections.reverse(players);
        return this;
    }

    public Game wildCardAction() {
        advanceTurn();
        return this;
    }

}

// ¿Las acciones deben ser públicas? ¿Hay que chequear if (gameIsClosed) { throw new Error("El juego ya terminó"); }?
// Corregir lo de comerse 2 cartas. Hay que saltear al otro.
// revisar el codigo repetido en agarrar o tirar.
// errores en lo de color, signo, etc.
// revisar lo de UNO de tatha
// Revisada general