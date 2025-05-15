package uno;

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
}
