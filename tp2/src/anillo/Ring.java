package anillo;

import java.util.Stack;

public class Ring {
    private static String CurrentLinkInEmptyRing = "Error. The link cannot be accessed because the ring is empty.";
    private static String NextLinkInEmptyRing = "Error. The next link cannot be accessed because the ring is empty.";
    private static String RemoveInEmptyRing = "Error. Cannot remove a link in an empty ring.";
    private Link current;
    private Stack<Link> behaviors = new Stack<Link>();

    public abstract class Link {
        public abstract Object getCargo();
        public abstract Link getNext();
        public abstract Link add(Object cargo);
        public abstract Link remove();
        public abstract Link action(RegularLink regularLink);
    }

    public class NullLink extends Link{
        public Object getCargo() {throw new RuntimeException(CurrentLinkInEmptyRing);}
        public Link getNext() {throw new RuntimeException(NextLinkInEmptyRing);}
        public Link add(Object cargo) {
            RegularLink newLink = new RegularLink(cargo);
            newLink.next = newLink;
            newLink.prev = newLink;
            behaviors.push(newLink);
            return newLink;
        }
        public Link remove() {throw new RuntimeException(RemoveInEmptyRing);}
        public Link action(RegularLink regularLink) {return this;}
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
            behaviors.push(newLink);
            return newLink;
        }
        public Link remove(){
            behaviors.pop();
            return behaviors.peek().action(this);
        }

        public Link action(RegularLink regularLink) {
            Link nextlink = regularLink.next;
            ((RegularLink)regularLink.prev).next = regularLink.next;
            ((RegularLink)regularLink.next).prev = regularLink.prev;
            return nextlink;
        }
    }
    public Ring() {
        this.current = new NullLink();
        behaviors.push(current);
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
// tenemos que:
// ver si los getters son necesarios
// ver si los mensajes son necesarios
// ver de separar en dos archivos las cosas
// ver en caso cuando va public, private y lo de static y eso.