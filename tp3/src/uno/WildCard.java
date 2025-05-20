package uno;

class WildCard extends Card {
    private ColorAssignment assignedColor;

    public WildCard() {
        this.assignedColor = new NullColorAssignment();
    }

    public WildCard assignColor(String color) {
        this.assignedColor = new RealColorAssignment(color);
        return this;
    }

    public String getSymbol() { return this.getClass().getSimpleName(); }

    public boolean canStackOn(Card card) {
        return assignedColor.canStackOn(card);
    }

    public boolean likeColor(String color) {
        return assignedColor.matches(color);
    }

    public boolean likeNumber(String number) {
        return false;
    }

    public boolean likeSymbol(String symbol) {
        return getSymbol().equals(symbol);
    }

    public Game cardAction(Game game) {
        return game.wildCardAction();
    }

    public boolean equals(Object obj) {
        return this == obj || (obj != null && getClass() == obj.getClass());
    }
}
