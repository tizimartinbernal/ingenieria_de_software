package uno;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private LinkedList<Card> cardDeck;
    private LinkedList<Player> players;
    private Card pileCard;

    public Game(List<Card> cards, int numberToDeal, String... playerNames) { // Que no se rompa si no hay cartas ara un jugador o para el pozo
        this.cardDeck = new LinkedList<>(cards); // ¿Chequemos que cards no esta vació, que numberToDeal sea >= 0 y que playerNames no esté vacío?
        this.pileCard = cardDeck.removeFirst();
        this.players = new LinkedList<>();

        for (String name : playerNames) {
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < numberToDeal; i++) {
                hand.add(cardDeck.removeFirst());
            }
            players.add(new Player(name, hand));
        }
    }

    public Card getPileCard() { return pileCard; }

    // ¿No debería haber getters de cardDeck o players?

    public Game playCard(Card card, String playerName) {
        Player currentPlayer = players.getFirst();
        if (!currentPlayer.getName().equals(playerName) || !currentPlayer.getHand().contains(card)) {
            throw new Error("No es tu turno o no tienes esa carta: " + playerName + " / " + card);
        }

        if (card.canStackOn(pileCard)) {
            // La carta es válida: actualiza montón y dispara acción
            pileCard = card;
            currentPlayer.removeCard(card);
            return card.cardAction(this);
        } else {
            throw new Error( "No puedes jugar esa carta: " + card + " / " + pileCard);
        }
    }

    public Game pickCard(String playerName) {
        Player current = players.getFirst();
        if (!current.getName().equals(playerName)) { throw new Error("No es tu turno " + playerName); }
        Card raisedCard = cardDeck.removeFirst();
        if (raisedCard.canStackOn(pileCard)) {
            pileCard = raisedCard;
            return raisedCard.cardAction(this);
        } else {
            current.addCard(raisedCard);
            advanceTurn();
            return this;
        }
    }


    private void advanceTurn() {
        players.addLast(players.removeFirst());
    }

    private void pickCardFromCardDeck(Player player) {
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
        // El siguiente jugador toma dos cartas
        pickCardFromCardDeck(players.getFirst());
        pickCardFromCardDeck(players.getFirst());
        return this;
    }

    public Game skipCardAction() {
        // Salta al siguiente y avanza de nuevo
        advanceTurn();
        advanceTurn();
        return this;
    }

    public Game reverseCardAction() {
        // Invierte el orden de los jugadores
        Collections.reverse(players);
        return this;
    }

    public Game wildCardAction() {
        // Después de jugar comodín, avanza el turno
        advanceTurn();
        return this;
    }


}



// Cosas que faltan:
// implementar que si tiene una carta y no canta uno: se come dos
// implementar cuando uno gana, que se termine el juego
// implementar lo de pickCard, para darle a un jugador una carta del mazo
// implementar atajar si un jugador tira una carta que no tiene

// tests


// DUDAS:
// alguien puede jugar cartas que no tiene? Osea tenemos que hacer el chequeo de que tiene la carta?
// O acaso si no tiene la carta que dice hacemos que se coma una carta del mazo?
// Tenemos que chequear que esté jugando el jugador que le corresponde o lo asumimos?
// Es conceptual esta duda pero la tengo: el advanceTurn() es parte de la dinámica del juego o parte de la acción de la carta que se está jugando?
