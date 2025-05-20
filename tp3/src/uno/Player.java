package uno;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final String name;
    private List<Card> cards = new ArrayList<>();

    public Player(String name, List<Card> cards) {
        this.name = name;
        this.cards.addAll(cards);
    }

    public String getName() { return name; }

    public List<Card> getHand() { return cards; }

    public void addCard(Card card) { cards.add(card); }

    public void removeCard(Card card) { cards.remove(card); }
}
