package uno;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Stack;

public class Game {
    private List <Card> cards = new Stack<>();
    private List <Player> players = new ArrayList<>();
    private Card currentCard;

    public Game(List<Card> cards, int numberToDeal, String... players) {
        this.cards.addAll(cards);
        this.currentCard = this.cards.removeFirst(); // saca la primera carta de la lista y la asigna a currentCard
        this.players = Arrays.stream(players)
                .map(name -> {
                    List<Card> hand = new ArrayList<>(this.cards.subList(0, numberToDeal));
                    this.cards.subList(0, numberToDeal).clear(); // elimina esas cartas de la lista original
                    return new Player(name, hand);
                })
                .toList();
    }

    public Card pit(){return currentCard;} // acá tendría que hacer el chequeo de si hay cartas. Preguntarle a emilio, por el tema ese de que al agregar eso no construimos funcionalidad y agregamos ifs

}
