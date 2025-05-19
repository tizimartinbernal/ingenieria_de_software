package uno;

public abstract class ColorAssignment {
    public abstract boolean matches(String color);
    public abstract boolean canStackOn(Card card);
}

class NullColorAssignment extends ColorAssignment { //Revisar por las dudas
    public boolean matches(String color) { return true; }

    public boolean canStackOn(Card card) { return true; }

    public String toString() {
        return "<no color>";
    }
}

class RealColorAssignment extends ColorAssignment {
    private final String color;

    public RealColorAssignment(String color) {
        this.color = color;
    }

    public boolean matches(String color) {
        return this.color.equals(color);
    }

    public boolean canStackOn(Card card) {
        return card.likeColor(this.color);
    }

    public String toString() {
        return color;
    }
}