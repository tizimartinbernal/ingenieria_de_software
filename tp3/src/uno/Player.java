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

    public void addCard(Card card) { cards.add(card); }

    public void removeCard(Card card) { cards.remove(card); }

    public boolean hasCard(Card card) { return cards.contains(card); }

    public boolean mustSayUno() { return cards.size() == 1; }

    public boolean hasWon() { return cards.isEmpty(); }
}
