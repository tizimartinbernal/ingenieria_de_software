package anillo;

public abstract class RemoveType {
    public abstract Link remove(RegularLink link);
}

class RemoveFinalLink extends RemoveType {
    private final String currentError;
    private final String nextError;
    private final String removeError;

    public RemoveFinalLink(String currentError, String nextError, String removeError) {
        this.currentError = currentError;
        this.nextError = nextError;
        this.removeError = removeError;
    }

    public Link remove(RegularLink link) {
        return new NullLink(currentError, nextError, removeError);
    }
}

class RemoveRegularLink extends RemoveType {
    public Link remove(RegularLink link) {
        Link nextlink = link.next;
        ((RegularLink) link.prev).next = link.next;
        ((RegularLink) link.next).prev = link.prev;
        return nextlink;
    }
}

