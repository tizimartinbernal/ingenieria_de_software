package uno;

import java.util.*;

public class Game {
    public static String ErrorCardNotInHand = "Error. The card cannot be played since it is not in the player's hand.";
    public static String ErrorCardNotMatch = "Error. The card cannot be played since it does not match the top card of the pile.";
    public static String ErrorEndGame = "Error. The game has already ended.";
    public static String ErrorPlayerTurn = "Error. The player does not exist or it is not his turn to play.";

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
        Player currentPlayer = getPlayerAndState(playerName);

        if (!currentPlayer.hasCard(card)) { throw new Error(ErrorCardNotInHand); }

        if (!card.canStackOn(pileCard)) { throw new Error(ErrorCardNotMatch); }

        updatePileCard(card, currentPlayer);

        if (currentPlayer.mustSayUno() != card.getUnoState()) {
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
        if (gameIsClosed) { throw new Error(ErrorEndGame); }

        Player currentPlayer = players.getFirst();

        if (!currentPlayer.getName().equals(playerName)) { throw new Error(ErrorPlayerTurn); }

        return currentPlayer;
    }

    private Card pickCardFromCardDeck(Player player) {
        Card raisedCard = cardDeck.removeFirst();
        player.addCard(raisedCard);
        return raisedCard;
    }

    private void updatePileCard(Card card, Player currentPlayer) {
        pileCard = card;
        currentPlayer.removeCard(card);
    }

    private void advanceTurn() { players.addLast(players.removeFirst()); }

    private void reverseTurn() { Collections.reverse(players); }

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
        reverseTurn();
        return this;
    }

    public Game wildCardAction() {
        advanceTurn();
        return this;
    }
}