package anillo;

public abstract class RemoveStrategy {
    public abstract Link remove(Link link);
}

class FinalLinkRemovalStrategy extends RemoveStrategy {
    public Link remove(Link link) {
        return new NullLink();
    }
}

class RegularLinkRemovalStrategy extends RemoveStrategy {
    public Link remove(Link link) {
        Link next = link.getNext();
        link.getPrev().setNext(next);
        next.setPrev(link.getPrev());
        return next;
    }
}
