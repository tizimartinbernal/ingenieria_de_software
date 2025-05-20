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

    public String getSymbol() { return this.getClass().getSimpleName(); }

    public boolean canStackOn(Card card) { return card.likeColor(color) || card.likeNumber(number); }

    public boolean likeColor(String color) { return this.color.equals(color); }

    public boolean likeNumber(String number) { return this.number.equals(number); }

    public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

    public Game cardAction(Game game) { return game.numberedCardAction(); }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NumberedCard other = (NumberedCard) obj;
        return color.equals(other.color) && number.equals(other.number);
    }
}
