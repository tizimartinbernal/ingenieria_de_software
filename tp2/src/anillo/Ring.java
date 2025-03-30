package anillo;


public class Ring {
    private static class Node {
        Object cargo;
        Node next;

        Node(Object cargo) {
            this.cargo = cargo;
            this.next = this;  // Por defecto, se apunta a sí mismo (circular)
        }
    }

    private Node current;
    private int size;

    public Ring() {
        this.current = null;
        this.size = 0;
    }

    public Ring next() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
        current = current.next;
        return this;
    }

    public Object current() {
        return null;
    }

    public Ring add( Object cargo ) {
        return null;
    }

    public Ring remove() {
        return null;
    }
}
