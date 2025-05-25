package uno;

class NumberedCard extends Card {
    private final String number;
    private final String color;

    public NumberedCard(String number, String color) {
        this.number = number;
        this.color = color;
    }
    public String getNumber() { return number; }

    public String getColor() { return color; }

    public boolean canStackOn(Card card) { return card.likeColor(getColor()) || card.likeNumber(getNumber()); }

    public boolean likeColor(String color) { return getColor().equals(color); }

    public boolean likeNumber(String number) { return getNumber().equals(number); }

    public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

    public Game cardAction(Game game) { return game.numberedCardAction(); }

    public boolean equals(Object obj) { return this == obj || (obj instanceof NumberedCard other && getColor().equals(other.getColor()) && getNumber().equals(other.getNumber())); }
}
