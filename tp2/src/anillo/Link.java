package anillo;

import java.util.Stack;

public abstract class Link {
    public abstract Object getCargo();

    public abstract Link getNext();

    public abstract void setNext(Link link);

    public abstract Link getPrev();

    public abstract void setPrev(Link link);

    public abstract Link add(Object cargo);

    public abstract Link remove(Stack<RemoveStrategy> behaviors);
}

class NullLink extends Link {
    public static String ERROR_CURRENT = "Error. The link cannot be accessed because the ring is empty.";
    public static String ERROR_NEXT = "Error. The next link cannot be accessed because the ring is empty.";
    public static String ERROR_PREV = "Error. The prev link cannot be accessed because the ring is empty.";
    public static String ERROR_REMOVE = "Error. Cannot remove a link in an empty ring.";

    public Object getCargo() {
        throw new RuntimeException(ERROR_CURRENT);
    }

    public Link getNext() {
        throw new RuntimeException(ERROR_NEXT);
    }

    public void setNext(Link link) {
        throw new RuntimeException(ERROR_NEXT);
    }

    public Link getPrev() {
        throw new RuntimeException(ERROR_PREV);
    }

    public void setPrev(Link link) {
        throw new RuntimeException(ERROR_PREV);
    }

    public Link add(Object cargo) {
        RegularLink newLink = new RegularLink(cargo);
        newLink.setNext(newLink);
        newLink.setPrev(newLink);
        return newLink;
    }

    public Link remove(Stack<RemoveStrategy> behaviors) {
        throw new RuntimeException(ERROR_REMOVE);
    }
}

class RegularLink extends Link {
    private Object cargo;
    private Link next;
    private Link prev;

    public RegularLink(Object cargo) {
        this.cargo = cargo;
    }

    public Object getCargo() {
        return cargo;
    }

    public Link getNext() {
        return next;
    }

    public void setNext(Link next) {
        this.next = next;
    }

    public Link getPrev() {
        return prev;
    }

    public void setPrev(Link prev) {
        this.prev = prev;
    }

    public Link add(Object cargo) {
        RegularLink newLink = new RegularLink(cargo);
        newLink.setNext(this);
        newLink.setPrev(this.prev);
        this.prev.setNext(newLink);
        this.setPrev(newLink);
        return newLink;
    }

    public Link remove(Stack<RemoveStrategy> behaviors) {
        return behaviors.peek().remove(this);
    }
}

