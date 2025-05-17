package uno;

public abstract class Card {
   public abstract boolean canStackOn(Card card);

   public abstract boolean likeColor(String color);

   public abstract boolean likeNumber(String number);

   public abstract boolean likeSymbol(String symbol);

   public abstract Game cardAction(Game game);
}

abstract class ColoredCard extends Card {
   private String color;

   public ColoredCard(String color) { this.color = color; }

   public String getColor() { return color; }

   public boolean likeColor(String color) { return this.getColor().equals(color); }
}

abstract class ColorlessCard extends Card {

   public boolean likeColor(String color) { return true; }

   public boolean canStackOn(Card card) { return true; }

}

class ColorlessWildCard extends ColorlessCard {

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean likeNumber(String number) { return true; }

   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); } //Devuelvo True solo ?

   public Game cardAction(Game game) { return game.wildCardAction(); } // Simplemete return this

}

class NumberedCard extends ColoredCard {
   private String number;

   public NumberedCard(String number, String color) {
      super(color);
      this.number = number;
   }

   public String getNumber() { return number; }

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeNumber(this.getNumber()); }

   public boolean likeNumber(String number) { return this.getNumber().equals(number); }

   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.numberedCardAction(); }
}

class DrawTwoCard extends ColoredCard {
   public DrawTwoCard(String color) { super(color); }

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }

   public boolean likeNumber(String number) { return false; }

   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.drawTwoCardAction(); }
}

class SkipCard extends ColoredCard {
   public SkipCard(String color) { super(color); }

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }

   public boolean likeNumber(String number) { return false; }

   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.skipCardAction(); }
}

class ReverseCard extends ColoredCard {
   public ReverseCard(String color) { super(color); }

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }

   public boolean likeNumber(String number) { return false; }

   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.reverseCardAction(); }
}

class WildCard extends ColoredCard { ;

   public WildCard(String color) { super(color); }

   public String getSymbol() { return this.getClass().getSimpleName(); }

   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }

   public boolean likeNumber(String number) { return true; }

   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }

   public Game cardAction(Game game) { return game.wildCardAction(); }
}

//
//
//class NumberedCard extends Card {
//   private String number;
//   private String color;
//
//   public NumberedCard(String number, String color) {
//      this.number = number;
//      this.color = color;
//   }
//
//   public String getNumber() { return number; }
//
//   public String getColor() { return color; }
//
//   public String getSymbol() { return this.getClass().getSimpleName(); }
//
//   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeNumber(this.getNumber()); }
//
//   public boolean likeColor(String color) { return this.getColor().equals(color); }
//
//   public boolean likeNumber(String number) { return this.getNumber().equals(number); }
//
//   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }
//
//   public Game cardAction(Game game) { return game.numberedCardAction(); }
//
//}
//
//class DrawTwoCard extends Card {
//   private String color;
//
//   public DrawTwoCard(String color) {
//          this.color = color;
//   }
//
//   public String getColor() {return color;}
//
//   public String getSymbol() {return this.getClass().getSimpleName();}
//
//   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }
//
//   public boolean likeColor(String color) { return this.getColor().equals(color); }
//
//   public boolean likeNumber(String number) { return false; }
//
//   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }
//
//    public Game cardAction(Game game) { return game.drawTwoCardAction(); }
//}
//
//class SkipCard extends Card {
//   private String color;
//
//   public SkipCard(String color) {
//          this.color = color;
//   }
//
//   public String getColor() {return color;}
//
//   public String getSymbol() { return this.getClass().getSimpleName(); } // Lo podría definir en Clase Card
//
//   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }
//
//   public boolean likeColor(String color) { return this.getColor().equals(color); }
//
//   public boolean likeNumber(String number) { return false; }
//
//   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }
//
//    public Game cardAction(Game game) { return game.skipCardAction(); }
//}
//
//class ReverseCard extends Card {
//   private String color;
//
//   public ReverseCard(String color) {
//          this.color = color;
//   }
//
//   public String getColor() {return color;}
//
//   public String getSymbol() { return this.getClass().getSimpleName(); } // Lo podría definir en Clase Card
//
//   public boolean canStackOn(Card card) { return card.likeColor(this.getColor()) || card.likeSymbol(this.getSymbol()); }
//
//   public boolean likeColor(String color) { return this.getColor().equals(color); }
//
//   public boolean likeNumber(String number) { return false; }
//
//   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }
//
//    public Game cardAction(Game game) { return game.reverseCardAction(); }
//
//}
//
//class WildCard extends Card {
//   private String symbol;
//
//   public WildCard(String symbol) {
//          this.symbol = symbol;
//   }
//
//   public String getSymbol() {return symbol;}
//
//   public boolean canStackOn(Card card) { return card.likeSymbol(this.getSymbol()); }
//
//   public boolean likeColor(String color) { return false; }
//
//   public boolean likeNumber(String number) { return false; }
//
//   public boolean likeSymbol(String symbol) { return this.getSymbol().equals(symbol); }
//
//   public Game cardAction(Game game) { return game.wildCardAction(); }
//}
//







/*
public class Card {
    private final String color;
    private final String number;

    public Card(String color, String number) {
        this.color = color;
        this.number = number;
    }

    public String getColor() {return color;}
    public String getNumber() {return number;}

    @Override
    public boolean equals(Object obj) {return this == obj || (obj != null && getClass() == obj.getClass() && color.equals(((Card) obj).getColor()) && number.equals(((Card) obj).number));}

    @Override
    public int hashCode() {return java.util.Objects.hash(color, number);}
}*/
