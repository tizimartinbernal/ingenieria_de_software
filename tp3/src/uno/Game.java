package uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;

public class Game {
    private List <Card> cardDeck = new Stack<>();
    private List <Player> players = new ArrayList<>();
    private Card pileCard;

    public Game(List<Card> cards, int numberToDeal, String... players) {
        this.cardDeck.addAll(cards);
        this.pileCard = this.cardDeck.removeFirst(); // saca la primera carta de la lista y la asigna a currentCard
        this.players = Arrays.stream(players).map(name -> {
                                              List<Card> hand = new ArrayList<>(this.cardDeck.subList(0, numberToDeal));
                                              this.cardDeck.subList(0, numberToDeal).clear(); // elimina esas cartas de la lista original
                                              return new Player(name, hand); }).toList();
    }

    public Card getPileCard(){ return pileCard; } // acá tendría que hacer el chequeo de si hay cartas. Preguntarle a emilio, por el tema ese de que al agregar eso no construimos funcionalidad y agregamos ifs

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

    // Falta Implementar
    public Game numberedCardAction() { return this; }

    public Game drawTwoCardAction() { return this; }

    public Game skipCardAction() { return this; }

    public Game reverseCardAction() { return this; }

    public Game wildCardAction() { return this; }

}
