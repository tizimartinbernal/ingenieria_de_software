package uno;

class SkipCard extends Card {
    private final String color;

    public SkipCard(String color) { this.color = color; }

    public String getColor() { return color; }

    public String getSymbol() { return this.getClass().getSimpleName(); }

    public boolean canStackOn(Card card) {
        return card.likeColor(color) || card.likeSymbol(getSymbol());
    }

    public boolean likeColor(String color) { return this.color.equals(color); }

    public boolean likeNumber(String number) { return false; }

    public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

    public Game cardAction(Game game) { return game.skipCardAction(); }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SkipCard other = (SkipCard) obj;
        return color.equals(other.color);
    }
}
