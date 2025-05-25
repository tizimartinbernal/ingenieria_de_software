package uno;

import java.util.*;

public class Game {
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

    public Game playCard(Card card, String playerName) {
        Player currentPlayer = getPlayerAndState(playerName);

        if (!currentPlayer.hasCard(card)) {throw new Error("No tienes esa carta: " + card);}
        if (!card.canStackOn(pileCard)) {throw new Error( "No puedes jugar esa carta: " + card + " / " + pileCard);}

        updatePileCard(card, currentPlayer);

        if (!currentPlayer.mustSayUno() && card.getUnoState()) { throw new Error( "No se puede cantar Uno en este momento de la partida" ); }
        if (currentPlayer.mustSayUno() && !card.getUnoState()) {
            pickCardFromCardDeck(currentPlayer);
            pickCardFromCardDeck(currentPlayer);
        }
        if (currentPlayer.hasWon()) { gameIsClosed = true; }

        return card.cardAction(this);

    }

    public Game pickCard(String playerName) {
        Player currentPlayer = getPlayerAndState(playerName);
        Card raisedCard = pickCardFromCardDeck(currentPlayer);

        if (!raisedCard.canStackOn(pileCard)) {
            advanceTurn();
            return this;
        }
        updatePileCard(raisedCard, currentPlayer);
        return raisedCard.cardAction(this);
    }

    private Player getPlayerAndState(String playerName) {
        if (gameIsClosed) { throw new Error("El juego ya terminó"); }

        Player currentPlayer = players.getFirst();

        if (!currentPlayer.getName().equals(playerName)) {throw new Error("No es tu turno: " + playerName);}
        return currentPlayer;
    }

    private Card pickCardFromCardDeck(Player player) {
        if (cardDeck.isEmpty()) {throw new Error("No quedan cartas en el mazo");}
        Card raisedCard = cardDeck.removeFirst();
        player.addCard(raisedCard);
        return raisedCard;

    }

    private void updatePileCard(Card card, Player currentPlayer) {
        pileCard = card;
        currentPlayer.removeCard(card);
    }

    private void advanceTurn() {players.addLast(players.removeFirst());}

    public Card getPileCard() { return pileCard; }

    public Game numberedCardAction() {
        advanceTurn();
        return this;
    }

    public Game drawTwoCardAction() {
        advanceTurn();
        pickCardFromCardDeck(players.getFirst());
        pickCardFromCardDeck(players.getFirst());
        advanceTurn();
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