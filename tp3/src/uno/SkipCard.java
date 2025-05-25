package uno;

class SkipCard extends Card {
    public static String ERROR_GET_NUMBER = "SkipCard does not have a number";
    private final String color;

    public SkipCard(String color) { this.color = color; }

    public String getColor() { return color; }

    public String getNumber() { throw new Error (ERROR_GET_NUMBER); }

    public boolean canStackOn(Card card) { return card.likeColor(getColor()) || card.likeSymbol(getSymbol()); }

    public boolean likeColor(String color) { return getColor().equals(color); }

    public boolean likeNumber(String number) { return false; }

    public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

    public Game cardAction(Game game) { return game.skipCardAction(); }

    public boolean equals(Object obj) { return this == obj || (obj instanceof SkipCard other && getColor().equals(other.getColor())); }
}
