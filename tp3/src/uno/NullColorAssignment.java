package uno;

class NullColorAssignment extends ColorAssignment {
    public boolean matches(String color) { return true; }

    public boolean canStackOn(Card card) { return true; }

    public String toString() {
        return "No Color Assignment";
    }
}