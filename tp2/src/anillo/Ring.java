package anillo;

import java.util.Stack;

public class Ring {
    private static final String CurrentLinkInEmptyRing = "Error. The link cannot be accessed because the ring is empty.";
    private static final String NextLinkInEmptyRing = "Error. The next link cannot be accessed because the ring is empty.";
    private static final String RemoveInEmptyRing = "Error. Cannot remove a link in an empty ring.";

    private Link current;
    private Stack<RemoveType> behaviors = new Stack<>();

    public Ring() {
        this.current = new NullLink(CurrentLinkInEmptyRing, NextLinkInEmptyRing, RemoveInEmptyRing);
        behaviors.push(new RemoveFinalLink(CurrentLinkInEmptyRing, NextLinkInEmptyRing, RemoveInEmptyRing));
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
        behaviors.push(new RemoveRegularLink());
        return this;
    }

    public Ring remove() {
        behaviors.pop();
        current = current.remove(behaviors);
        return this;
    }
}
