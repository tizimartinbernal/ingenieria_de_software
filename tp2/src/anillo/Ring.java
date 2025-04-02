package anillo;

public class Ring {
    private static class Node {
        Object cargo;
        Node next;
        Node prev;

        Node(Object cargo) {
            this.cargo = cargo;
        }
    }

    private Node current;

    public Ring() {
        this.current = null;
    }

    public Ring next() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
        current = current.next;
        return this;
    }

    public Object current() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
        return current.cargo;
    }

    public Ring add(Object cargo) {
        Node newNode = new Node(cargo);
        if (current == null) {
            current = newNode;
            current.next = current;
            current.prev = current;
        } else {
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
            current.prev = newNode;
            current = newNode;
        }
        return this;
    }

    public Ring remove() {
        if (current == null) {
            return this;
        }
        if (current.next == current) {
            current = null;
        } else {
            Node nextNode = current.next;
            current.prev.next = current.next;
            current.next.prev = current.prev;
            current = nextNode;
        }
        return this;
    }
}