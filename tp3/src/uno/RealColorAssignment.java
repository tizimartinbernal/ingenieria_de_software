package uno;

class RealColorAssignment extends ColorAssignment {
    private final String color;

    public RealColorAssignment(String color) {
        this.color = color;
    }

    public boolean matches(String color) { return getColor().equals(color); }

    public String getColor() { return color; }
}