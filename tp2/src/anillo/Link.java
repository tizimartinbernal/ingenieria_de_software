package anillo;

import java.util.Stack;
import java.util.function.Function;

public abstract class Link {
    public abstract Object getCargo();

    public abstract RegularLink getNext();

    public abstract void setNext(RegularLink link);

    public abstract RegularLink getPrev();

    public abstract void setPrev(RegularLink link);

    public abstract RegularLink add(Object cargo);

    public abstract Link remove(Stack<Function<RegularLink, Link>> behaviors);
}

class NullLink extends Link {
    public static String ERROR_CURRENT = "Error. The link cannot be accessed because the ring is empty.";
    public static String ERROR_NEXT = "Error. The next link cannot be accessed because the ring is empty.";
    public static String ERROR_PREV = "Error. The prev link cannot be accessed because the ring is empty.";
    public static String ERROR_REMOVE = "Error. The link cannot be removed because the ring is empty.";

    public Object getCargo() { throw new RuntimeException(ERROR_CURRENT); }

    public RegularLink getNext() { throw new RuntimeException(ERROR_NEXT); }

    public void setNext(RegularLink link) { throw new RuntimeException(ERROR_NEXT); }

    public RegularLink getPrev() { throw new RuntimeException(ERROR_PREV); }

    public void setPrev(RegularLink link) { throw new RuntimeException(ERROR_PREV); }

    public RegularLink add(Object cargo) {
        RegularLink newLink = new RegularLink(cargo);
        newLink.setNext(newLink);
        newLink.setPrev(newLink);
        return newLink;
    }

    public Link remove(Stack<Function<RegularLink, Link>> behaviors) {
        throw new RuntimeException(ERROR_REMOVE);
    }
}

class RegularLink extends Link {
    private Object cargo;
    private RegularLink next;
    private RegularLink prev;

    public RegularLink(Object cargo) {
        this.cargo = cargo;
    }

    public Object getCargo() {
        return cargo;
    }

    public RegularLink getNext() {
        return next;
    }

    public void setNext(RegularLink next) {
        this.next = next;
    }

    public RegularLink getPrev() {
        return prev;
    }

    public void setPrev(RegularLink prev) {
        this.prev = prev;
    }

    public RegularLink add(Object cargo) {
        RegularLink newLink = new RegularLink(cargo);
        newLink.setNext(this);
        newLink.setPrev(this.prev);
        this.prev.setNext(newLink);
        this.setPrev(newLink);
        return newLink;
    }

    public Link remove(Stack<Function<RegularLink, Link>> behaviors) {
        return behaviors.peek().apply(this);
    }
}