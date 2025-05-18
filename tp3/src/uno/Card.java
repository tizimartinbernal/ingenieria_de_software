package uno;

public abstract class Card {
   public abstract boolean canStackOn(Card card);

   public abstract boolean likeColor(String color);

   public abstract boolean likeNumber(String number);

   public abstract boolean likeSymbol(String symbol);

   public abstract Game cardAction(Game game);

   public abstract boolean equals(Object obj);
}


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


class DrawTwoCard extends Card {
   private final String color;
   public DrawTwoCard(String color) { this.color = color; }
   public String getColor() { return color; }
   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) { return card.likeColor(color) || card.likeSymbol(getSymbol()); }

   public boolean likeColor(String color) { return this.color.equals(color); }

   public boolean likeNumber(String number) { return false; }

   public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.drawTwoCardAction(); }

   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      DrawTwoCard other = (DrawTwoCard) obj;
      return color.equals(other.color);
   }
}


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


class ReverseCard extends Card {
   private final String color;

   public ReverseCard(String color) { this.color = color; }

   public String getColor() { return color; }

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) {
      return card.likeColor(color) || card.likeSymbol(getSymbol());
   }

   public boolean likeColor(String color) { return this.color.equals(color); }

   public boolean likeNumber(String number) { return false; }

   public boolean likeSymbol(String symbol) { return getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.reverseCardAction(); }

   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      ReverseCard other = (ReverseCard) obj;
      return color.equals(other.color);
   }
}


class WildCard extends Card {
   private ColorAssignment assignedColor;

   public WildCard() {
      this.assignedColor = new NullColorAssignment();
   }

   public void assignColor(String color) {
      this.assignedColor = new RealColorAssignment(color);
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