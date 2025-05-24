package uno;

class NullColorAssignment extends ColorAssignment {
    public boolean matches(String color) { return true; }
    public String toString() {
        return "No Color Assignment";
    }
}