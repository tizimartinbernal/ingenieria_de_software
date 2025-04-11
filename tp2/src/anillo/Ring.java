package anillo;

public class Ring {
    public static final String CurrentLinkInEmptyRing = "Error. The link cannot be accessed because the ring is empty.";
    public static final String NextLinkInEmptyRing = "Error. The next link cannot be accessed because the ring is empty.";
    // Agregué esto para los errores porque vi que Emilio lo hizo así cuando nos mandó el video de ejemplo de parcial. 

    private Link current;

    // Interfaz base para eslabón
    private interface Link {
        Object getCargo();
        Link getNext();
        Link add(Object cargo);
        Link remove();
    }

    // Eslabón nulo para representar un anillo vacío
    private static class NullLink implements Link {
        @Override
        public Object getCargo() {
            throw new RuntimeException(CurrentLinkInEmptyRing);
        }

        @Override
        public Link getNext() {
            throw new RuntimeException(NextLinkInEmptyRing);
        }

        @Override
        public Link add(Object cargo) {
            RegularLink newLink = new RegularLink(cargo);
            newLink.next = newLink;
            newLink.prev = newLink;
            return newLink;
        }

        @Override
        public Link remove() {
            return this;
        }
    }

    // Eslabón regular para anillos con elementos
    private static class RegularLink implements Link {
        private final Object cargo;
        private Link next;
        private Link prev;

        RegularLink(Object cargo) {
            this.cargo = cargo;
        }

        @Override
        public Object getCargo() {
            return cargo;
        }

        @Override
        public Link getNext() {
            return next;
        }

        @Override
        public Link add(Object newCargo) {
            RegularLink newLink = new RegularLink(newCargo);
            newLink.next = this;
            newLink.prev = this.prev;
            ((RegularLink)this.prev).next = newLink;
            this.prev = newLink;
            return newLink;
        }

        @Override
        public Link remove() {
            Link nextLink = this.next;

            // Verificar si este es el último eslabón en el anillo
            boolean isLastLink = this == this.next;

            if (isLastLink) {
                return new NullLink();
            } else {
                ((RegularLink)this.prev).next = this.next;
                ((RegularLink)this.next).prev = this.prev;
                return nextLink;
            }
        }
    }

    // Métodos del anillo
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