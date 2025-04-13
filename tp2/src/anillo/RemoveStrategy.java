package anillo;

public abstract class RemoveStrategy {
    public abstract Link removeLink(Link link);
}

class FinalLinkRemovalStrategy extends RemoveStrategy {
    public Link removeLink(Link link) {
        return new NullLink();
    }
}

class RegularLinkRemovalStrategy extends RemoveStrategy {
    public Link removeLink(Link link) {
        Link next = link.getNext();
        link.getPrev().setNext(next);
        next.setPrev(link.getPrev());
        return next;
    }
}
