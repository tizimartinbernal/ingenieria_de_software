package uno;

class ReverseCard extends Card {
    private final String color;

    public ReverseCard(String color) { this.color = color; }

    public String getColor() { return color; }

    public String getNumber() {throw new Error("ReverseCard does not have a number.");}

    public boolean canStackOn(Card card) {return card.likeColor(getColor()) || card.likeSymbol(getSymbol());}

    public boolean likeColor(String color) { return getColor().equals(color); }

    public boolean likeNumber(String number) { return false; }

    public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

    public Game cardAction(Game game) { return game.reverseCardAction(); }

    public boolean equals(Object obj) { return this == obj || (obj != null && getClass() == obj.getClass() && getColor().equals(((ReverseCard) obj).getColor())); }
}
