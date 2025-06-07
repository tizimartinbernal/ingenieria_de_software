package org.udesa.unobackend.service;

import org.springframework.stereotype.Component;
import org.udesa.unobackend.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class Dealer {

    public List<Card> fullDeck() {
        List<Card> deck = new ArrayList<>();

        for (String color : List.of("Red", "Blue", "Green", "Yellow")) {
            deck.addAll(cardsOfColor(color));
        }

        deck.addAll(wildCards());

        Collections.shuffle(deck);
        return deck;
    }

    private List<Card> cardsOfColor(String color) {
        List<Card> cards = new ArrayList<>();

        cards.add(new NumberCard(color, 0));

        for (int i = 1; i <= 9; i++) {
            cards.add(new NumberCard(color, i));
            cards.add(new NumberCard(color, i));
        }

        for (int i = 0; i < 2; i++) {
            cards.add(new Draw2Card(color));
            cards.add(new ReverseCard(color));
            cards.add(new SkipCard(color));
        }

        return cards;
    }

    private List<Card> wildCards() {
        List<Card> cards = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            cards.add(new WildCard());
        }

        return cards;
    }
}
