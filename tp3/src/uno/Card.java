package uno;

public abstract class Card {
   private boolean unoState = false;

   public boolean getUnoState() { return unoState; }

   public Card uno() {
      this.unoState = true;
      return this;
   }

   public abstract boolean canStackOn(Card card);

   public abstract boolean likeColor(String color);

   public abstract boolean likeNumber(String number);

   public abstract boolean likeSymbol(String symbol);

   public abstract Game cardAction(Game game);

   public abstract boolean equals(Object obj);
}