package uno;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<Card> cards = new ArrayList<>();

    public Player(String name, List<Card> cards) {
        this.name = name;
        this.cards.addAll(cards);
    }

    public String getName() {return name;} //No se si es necesario

    public List<Card> getCards() {return cards;} //No se si es necesario

    public void addCard(Card card) {cards.add(card);}

    public void removeCard(Card card) {
        cards.remove(card);
    }
}
