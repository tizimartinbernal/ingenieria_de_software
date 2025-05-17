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


    // Falta Implementar
    public Game numberedCardAction() { return this; }

    public Game drawTwoCardAction() { return this; }

    public Game skipCardAction() { return this; }

    public Game reverseCardAction() { return this; }

    public Game wildCardAction() { return this; }

}
