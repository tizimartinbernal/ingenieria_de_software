package uno;

class WildCard extends Card {
    public static String ErrorGetNumber = "WildCard does not have a number";
    private ColorAssignment assignedColor;

    public WildCard() {
        this.assignedColor = new NullColorAssignment();
    }

    public WildCard assignColor(String color) {
        this.assignedColor = new RealColorAssignment(color);
        return this;
    }

    public String getColor() { throw new Error(ErrorGetNumber); }

    public String getNumber() { return assignedColor.getColor(); }

    public boolean canStackOn(Card card) { return true; }

    public boolean likeColor(String color) { return assignedColor.matches(color); }

    public boolean likeNumber(String number) { return false; }

    public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

    public Game cardAction(Game game) { return game.wildCardAction(); }

    public boolean equals(Object obj) { return this == obj || (obj != null && getClass() == obj.getClass()); }
}
