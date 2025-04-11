package anillo;

public class Ring {
    public static String CurrentLinkInEmptyRing = "Error. The link cannot be accessed because the ring is empty.";
    public static String NextLinkInEmptyRing = "Error. The next link cannot be accessed because the ring is empty.";
    public static String RemoveAnEmptyRing = "Error. Cannot remove a link in an empty ring.";
    private Link current;

    public abstract class Link {
        public abstract Object getCargo();
        public abstract Link getNext();
        public abstract Link add(Object cargo);
        public abstract Link remove();
    }

    public class NullLink extends Link{
        public Object getCargo() {throw new RuntimeException(CurrentLinkInEmptyRing);}
        public Link getNext() {throw new RuntimeException(NextLinkInEmptyRing);}
        public Link add(Object cargo) {
            RegularLink newLink = new RegularLink(cargo);
            newLink.next = newLink;
            newLink.prev = newLink;
            return newLink;
        }
        public Link remove() {throw new RuntimeException(RemoveAnEmptyRing);}
    }

    public class RegularLink extends Link{
        public Object cargo;
        public Link next;
        public Link prev;

        RegularLink(Object cargo) {this.cargo = cargo;}
        public Object getCargo(){return cargo;}
        public Link getNext(){return next;}
        public Link add(Object cargo) {
            RegularLink newLink = new RegularLink(cargo);
            newLink.next = this;
            newLink.prev = this.prev;
            ((RegularLink)this.prev).next = newLink;
            this.prev = newLink;
            return newLink;
        }
        public Link remove(){
            Link nextLink = this.next;
            boolean isLastLink = this == this.next;

            if (isLastLink) {
                return new NullLink();
            }
            else {
                ((RegularLink)this.prev).next = this.next;
                ((RegularLink)this.next).prev = this.prev;
                return nextLink;
            }
        }
    }
    public Ring() {
        this.current = new NullLink();
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
        return this;
    }

    public Ring remove() {
        current = current.remove();
        return this;
    }
}