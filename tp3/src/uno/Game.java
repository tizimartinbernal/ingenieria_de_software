package uno;

import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private LinkedList<Card> cardDeck;
    private LinkedList<Player> players;
    private Card pileCard;

    public Game(List<Card> cards, int numberToDeal, String... playerNames) {
        // Inicializa la baraja como LinkedList para operaciones O(1) en extremos
        this.cardDeck = new LinkedList<>(cards);
        // Saca la carta inicial para el montón
        this.pileCard = cardDeck.removeFirst();

        // Crea la lista de jugadores como LinkedList
        this.players = new LinkedList<>();
        for (String name : playerNames) {
            // Reparte numberToDeal cartas a cada jugador
            List<Card> hand = new ArrayList<>();
            for (int i = 0; i < numberToDeal; i++) {
                hand.add(cardDeck.removeFirst());
            }
            players.add(new Player(name, hand));
        }
    }

    public Card getPileCard() {
        if (pileCard == null) {
            throw new IllegalStateException("No hay carta en el montón");
        }
        return pileCard;
    }

    // Mueve el primer jugador al final de la lista para avanzar el turno
    private void advanceTurn() {
        players.addLast(players.removeFirst());
    }

    // El jugador toma una carta del mazo
    public void pickCard(Player player) {
        if (cardDeck.isEmpty()) {
            throw new IllegalStateException("No quedan cartas en el mazo");
        }
        player.addCard(cardDeck.removeFirst());
    }

    // Acciones según tipo de carta
    public Game numberedCardAction() {
        advanceTurn();
        return this;
    }

    public Game drawTwoCardAction() {
        advanceTurn();
        // El siguiente jugador toma dos cartas
        pickCard(players.getFirst());
        pickCard(players.getFirst());
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

    // Método principal para jugar una carta
    public Game playCard(Card card, String playerName) {
        Player current = players.getFirst();
        // Verifica turno y posesión de la carta
        if (!current.getName().equals(playerName) || !current.getHand().contains(card)) {
            throw new IllegalStateException(
                    "No es tu turno o no tienes esa carta: " + playerName + " / " + card
            );
        }

        if (card.canStackOn(pileCard)) {
            // La carta es válida: actualiza montón y dispara acción
            pileCard = card;
            current.removeCard(card);
            return card.cardAction(this);
        } else {
            // Carta inválida: pierde el turno
            advanceTurn();
            return this;
        }
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
