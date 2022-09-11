package collections.nodes;

public class DoublyLinkedNode<E> extends LinkedNode<E>{

    protected LinkedNode<E> prev;

    public DoublyLinkedNode(E value, LinkedNode<E> next, LinkedNode<E> prev) {
        super(value, next);
        this.prev = prev;
    }

    public DoublyLinkedNode(E value, LinkedNode<E> next) {
        this(value, next, null);
    }

    public DoublyLinkedNode(E value) {
        this(value, null, null);
    }

    public DoublyLinkedNode() {
        this(null, null, null);
    }

    public LinkedNode<E> getPrev() {
        return prev;
    }

    public void setPrev(LinkedNode<E> prev) {
        this.prev = prev;
    }
}
