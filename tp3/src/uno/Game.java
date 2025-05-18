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

<<<<<<< HEAD
    public Game normalCardDraw(String playerName, Card card) {
        // 1–2) filtro y excepciono sin usar ifs
        Player current = players.stream()
                // me quedo sólo con el primero de la lista
                .limit(1)
                .filter(p -> p.getName().equals(playerName))
                // compruebo que tenga la carta en la mano
                .filter(p -> p.getHand().contains(card))
                // si no encuentro a nadie, lanzo excepción
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(
                        "No es tu turno o no tienes esa carta: " + playerName + " / " + card
                ));

        // 3a) saco la carta de la mano
        current.getHand().remove(card);
        // 3b) actualizo la pila
        this.pileCard = card;
        // 3c) roto la lista de jugadores para pasar el turno
        Collections.rotate(players, -1);

        // 4) devuelvo el mismo juego, pero con el turno ya movido
        return this;
    }
=======
    private void advanceTurn(){players.add(players.removeFirst());}
>>>>>>> 3074806 (Completé un poco de player y agregué funcionalidad a game)

    public Game numberedCardAction() {
        advanceTurn();
        return this;
    }

    public Game drawTwoCardAction() {
        advanceTurn();
        players.getFirst().addCard(cardDeck.removeFirst());
        players.getFirst().addCard(cardDeck.removeFirst());
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

    public void playCard(Card card, Player player) {
        if (card.canStackOn(pileCard)) {
            pileCard = card;
            player.removeCard(card);
            card.cardAction(this);
        } else {
            advanceTurn();
        }
    }
}

// DUDAS:
// alguien puede jugar cartas que no tiene? Osea tenemos que hacer el chequeo de que tiene la carta?
// O acaso si no tiene la carta que dice hacemos que se coma una carta del mazo?

