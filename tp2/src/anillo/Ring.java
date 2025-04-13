package anillo;

import java.util.Stack;
import java.util.function.Function;

public class Ring {
    private Link current;
    private Stack<Function<RegularLink, Link>> behaviors = new Stack<>();

    public Ring() {
        this.current = new NullLink();
        behaviors.push(link -> new NullLink());
    }

    public Ring next() {
        current = current.getNext();
        return this;
    }

    public Object current() {
        return current.getCargo();
    }

    public Ring add(Object cargo) {
        behaviors.push(link -> {
            RegularLink next = link.getNext();
            link.getPrev().setNext(next);
            next.setPrev(link.getPrev());
            return next;
        });
        current = current.add(cargo);
        return this;
    }

    public Ring remove() {
        behaviors.pop();
        current = current.remove(behaviors);
        return this;
    }
}