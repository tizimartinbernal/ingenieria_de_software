package uno;

class NullColorAssignment extends ColorAssignment {
    public boolean matches(String color) { return true; }

    public String getColor()  { return "No Color Assignment"; }
}