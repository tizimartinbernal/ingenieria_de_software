package uno;

class RealColorAssignment extends ColorAssignment {
    private final String color;

    public RealColorAssignment(String color) {
        this.color = color;
    }

    public boolean matches(String color) {
        return this.color.equals(color);
    }

    public String toString() {return color;}
}