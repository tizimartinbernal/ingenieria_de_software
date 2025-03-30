package anillo;

public class Ring {
    private static class Node {
        Object cargo;
        Node next;

        Node(Object cargo) {
            this.cargo = cargo;
            this.next = this;  // Por defecto, se apunta a sí mismo (circular). Lo dejamos o lo sacamos y directamente lo inicializamos en add?
        }
    }

    private Node current;
    private int size;

    public Ring() {
        this.current = null;
        this.size = 0;
    }

    public Ring next() {
        if (current == null) {
            throw new RuntimeException("Ring is empty");
        }
        current = current.next;
        return this;
    }
    

    public Object current() {
        return current.cargo;
    }

    public Ring add( Object cargo ) {
        if (size == 0) {
            this.current = new Node(cargo);
            this.size++;
            return this.next();
        }
        Node viejo_next = current.next;
        Node nuevo_nodo = new Node( cargo );
        current.next = nuevo_nodo;
        nuevo_nodo.next = viejo_next;
        size++;
        return this.next();

    }

    public Ring remove() {
        if (size == 0) {
            throw new RuntimeException("Ring is empty");
        } else if (size == 1) {
            this.current = null;
            this.size = 0;
            return this;
        }
        Node viejo_next = current.next;
        for (int i = 0 ; i < size - 1 ; i++) {
            current = current.next;
        }
        current.next = viejo_next;
        size--;
        return this.next();
    }
}
// cuando hacemos add tenemos que avanzar el current?
// está bien el test 8?
// Está bien nuestra implementación con current solamente o deberíamos tener head y last. Porque si no tenemos head y last o si no tenemos la lista doblemente enlazada el remove nos queda en O(n)
// está bien nuestra idea del add y del remove? Porque nos anda casi todos pero el 8 y el 12 no.