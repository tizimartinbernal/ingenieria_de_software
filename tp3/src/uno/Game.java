package uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;

public class Game {
    private final List <Card> cardDeck = new Stack<>();
    private List <Player> players = new ArrayList<>();
    private Card pileCard;

    public Game(List<Card> cards, int numberToDeal, String... players) {
        this.cardDeck.addAll(cards);
        this.pileCard = this.cardDeck.removeFirst(); // saca la primera carta de la lista
        this.players = Arrays.stream(players).map(name -> {
            List<Card> hand = new ArrayList<>(this.cardDeck.subList(0, numberToDeal));
            this.cardDeck.subList(0, numberToDeal).clear(); // elimina esas cartas de la lista original
            return new Player(name, hand); }).toList();
    }

    public Card getPileCard(){ return pileCard; } // acá tendría que hacer el chequeo de si hay cartas. Preguntarle a emilio, por el tema ese de que al agregar eso no construimos funcionalidad y agregamos ifs

    private void advanceTurn(){players.add(players.removeFirst());}

    public void pickCard(Player player) {player.addCard(cardDeck.removeFirst());}

    public Game numberedCardAction() {
        advanceTurn();
        return this;
    }

    public Game drawTwoCardAction() {
        advanceTurn();
        pickCard(players.getFirst());
        pickCard(players.getFirst());
        return this;
    }

    public Game skipCardAction() {
        advanceTurn();
        advanceTurn();
        return this;
    }

    public Game reverseCardAction() {
        players.reversed();
        return this;
    }

    public Game wildCardAction() {
        advanceTurn();
        return this;
    }

    public void playCard(Card card, Player playerName) {
        Player currentPlayer = players.stream()
                .limit(1) // me quedo sólo con el primero de la lista
                .filter(p -> p.getName().equals(playerName))
                .filter(p -> p.getHand().contains(card)) // compruebo que tenga la carta en la mano
                .findFirst() // si no encuentro a nadie, lanzo excepción
                .orElseThrow(() -> new IllegalStateException(
                        "No es tu turno o no tienes esa carta: " + playerName + " / " + card // Podemos cambiarlo para que le sume una carta del mazo al jugador.
                ));

        if (card.canStackOn(pileCard)) {
            pileCard = card;
            playerName.removeCard(card);
            card.cardAction(this);
            // advanceTurn();
        } else {
            advanceTurn();
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
