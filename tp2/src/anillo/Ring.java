package anillo;

import java.util.Stack;

public class Ring {
    private Link current;
    private Stack<RemoveStrategy> behaviors = new Stack<>();

    public Ring() {
        this.current = new NullLink();
        behaviors.push(new FinalLinkRemovalStrategy());
    }

    public Ring next() {
        current = current.getNext();
        return this;
    }

    public Object current() {
        return current.getCargo();
    }

    public Ring add(Object cargo) {
        current = current.add(cargo);
        behaviors.push(new RegularLinkRemovalStrategy());
        return this;
    }

    public Ring remove() {
        behaviors.pop();
        current = current.remove(behaviors);
        return this;
    }
}
