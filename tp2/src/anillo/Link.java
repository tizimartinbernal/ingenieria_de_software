package anillo;

import java.util.Stack;

public abstract class Link {
    public abstract Object getCargo();
    public abstract Link getNext();
    public abstract Link add(Object cargo);
    public abstract Link remove(Stack<RemoveType> behaviors);
}

class NullLink extends Link {
    private final String currentError;
    private final String nextError;
    private final String removeError;

    public NullLink(String currentError, String nextError, String removeError) {
        this.currentError = currentError;
        this.nextError = nextError;
        this.removeError = removeError;
    }

    public Object getCargo() {
        throw new RuntimeException(currentError);
    }

    public Link getNext() {
        throw new RuntimeException(nextError);
    }

    public Link add(Object cargo) {
        RegularLink newLink = new RegularLink(cargo);
        newLink.next = newLink;
        newLink.prev = newLink;
        return newLink;
    }

    public Link remove(Stack<RemoveType> behaviors) {
        throw new RuntimeException(removeError);
    }
}

class RegularLink extends Link {
    public Object cargo;
    public Link next;
    public Link prev;

    public RegularLink(Object cargo) {
        this.cargo = cargo;
    }

    public Object getCargo() {
        return cargo;
    }

    public Link getNext() {
        return next;
    }

    public Link add(Object cargo) {
        RegularLink newLink = new RegularLink(cargo);
        newLink.next = this;
        newLink.prev = this.prev;
        ((RegularLink) this.prev).next = newLink;
        this.prev = newLink;
        return newLink;
    }

    public Link remove(Stack<RemoveType> behaviors) {
        return behaviors.peek().remove(this);
    }
}
